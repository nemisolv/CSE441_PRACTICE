package net.nemisolv.read_json;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> adapter;
    List<String> list;

    @SuppressLint("MissingInflatedId")
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
        listView = findViewById(R.id.listView);
        list = readJSON();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

    }

    private List<String> readJSON() {
        try {
            InputStream is = getAssets().open("computer.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            JSONObject obj = new JSONObject(json);
            List<String> list = new ArrayList<>();
            list.add(obj.getString("category_id"));
            list.add(obj.getString("category_name"));
            for (int i = 0; i < obj.getJSONArray("products").length(); i++) {
                JSONObject computer = obj.getJSONArray("products").getJSONObject(i);
                list.add(computer.getString("id"));
                list.add(computer.getString("name"));
                list.add(computer.getString("price"));
            }
            return list;
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
}