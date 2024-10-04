package net.nemisolv.studentmanagement;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lvClass;
    private EditText txtClassId, txtClassName, txtClassSize;
    private Button btnAdd, btnUpdate, btnDelete,btnQuery;
    List<String> list ;
    private ArrayAdapter<String> adapter;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setupViews();
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lvClass.setAdapter(adapter);
        db = openOrCreateDatabase("student.db", MODE_PRIVATE, null);
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS `class` (id TEXT, name TEXT, size INTEGER)");
        } catch (Exception e) {
            e.printStackTrace();
        }
        btnAdd.setOnClickListener(v -> {
            String id = txtClassId.getText().toString();
            String name = txtClassName.getText().toString();
            String size = txtClassSize.getText().toString();
            if(id.isEmpty() || name.isEmpty() || size.isEmpty())  {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            db.execSQL("INSERT INTO `class` (id, name, size) VALUES (?, ?, ?)",
                    new Object[]{id, name, Integer.parseInt(size)});
            clearFields();
        });

        btnUpdate.setOnClickListener(v -> {
            String id = txtClassId.getText().toString();
            String name = txtClassName.getText().toString();
            String size = txtClassSize.getText().toString();
            if(id.isEmpty()) {
                Toast.makeText(this, "Please enter class id", Toast.LENGTH_SHORT).show();
                return;
            }

            db.execSQL("UPDATE `class` SET name = ?, size = ? WHERE id = ?",
                    new Object[]{name, Integer.parseInt(size), id});
            clearFields();
        });

        btnDelete.setOnClickListener(v -> {
            String id = txtClassId.getText().toString();
            if(id.isEmpty())  {
                Toast.makeText(this, "Please enter class id", Toast.LENGTH_SHORT).show();
                return;
            }

            db.execSQL("DELETE FROM `class` WHERE id = ?", new Object[]{id});
            clearFields();
        });

        btnQuery.setOnClickListener(v -> {
            list.clear();
            list.add("ID - Name - Size");
            adapter.notifyDataSetChanged();
            try {
                android.database.Cursor cursor = db.rawQuery("SELECT * FROM `class`", null);
                while (cursor.moveToNext()) {
                    String id = cursor.getString(0);
                    String name = cursor.getString(1);
                    int size = cursor.getInt(2);
                    list.add(id + " - " + name + " - " + size);
                }
                adapter.notifyDataSetChanged();
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    private void clearFields() {
        txtClassId.setText("");
        txtClassName.setText("");
        txtClassSize.setText("");
    }


    private void setupViews() {
        lvClass = findViewById(R.id.lv_class);
        txtClassId = findViewById(R.id.txt_class_id);
        txtClassName = findViewById(R.id.txt_class_name);
        txtClassSize = findViewById(R.id.txt_class_size);
        btnAdd = findViewById(R.id.btn_insert);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);
        btnQuery = findViewById(R.id.btn_query);
    }
}