package com.nemisolv.phonelist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Phone> phoneList;
    private ListView lvPhones;
    MyArrayAdapter myArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial();

    }

    private void initial() {
        lvPhones = findViewById(R.id.listView);
        phoneList = List.of(
                new Phone("Iphone 15 Pro Max",R.drawable.image1),
                new Phone("Iphone 12 Pro Max",R.drawable.image2),
                new Phone("Samsung s22 ultra",R.drawable.image3),
                new Phone("HTC 1", R.drawable.image4),
                new Phone("Xiaomi mi 8",R.drawable.image5)
                );
        myArrayAdapter = new MyArrayAdapter(this,R.layout.list_view_item,phoneList);
        lvPhones.setAdapter(myArrayAdapter);

        lvPhones.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this,ResultActivity.class);
            Phone current = myArrayAdapter.getItem(position);
            intent.putExtra("name",current.getName());
            startActivity(intent);
        });



    }
}