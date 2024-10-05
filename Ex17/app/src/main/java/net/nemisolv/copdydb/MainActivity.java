package net.nemisolv.copdydb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase db;
    String DB_NAME = "student.db";
    ListView listView;
    List<String> myList;
    ArrayAdapter<String> adapter;

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
        processCopy();
        db = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        listView = findViewById(R.id.listView);
        myList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myList);
        listView.setAdapter(adapter);
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS `class` (id TEXT, name TEXT, size INTEGER)");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor cursor = db.query("class", null, null, null, null, null, null);
        cursor.moveToFirst();
        String data = "";
        while (!cursor.isAfterLast()) {
            data = cursor.getString(0) + " - " + cursor.getString(1) + " - " + cursor.getString(2);
            myList.add(data);
            cursor.moveToNext();
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    private void processCopy() {
        File dbFile = getDatabasePath(DB_NAME);
        if (!dbFile.exists()) {
            try {
                copyDatabase();
                Toast.makeText(this, "Copy database success", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Copy database failed", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DB_NAME;
    }

    private void copyDatabase() {
        try {
            InputStream myInput = getAssets().open(DB_NAME);
            String outFileName = getDatabasePath();
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists()) {
                f.mkdir();
            }
            OutputStream myOutput = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                myOutput = Files.newOutputStream(Paths.get(outFileName));
            }
            int size = myInput.available();
            byte[] buffer = new byte[size];
            myInput.read(buffer);
            assert myOutput != null;
            myOutput.write(buffer);
            myOutput.flush();
            myOutput.close();
            myInput.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}