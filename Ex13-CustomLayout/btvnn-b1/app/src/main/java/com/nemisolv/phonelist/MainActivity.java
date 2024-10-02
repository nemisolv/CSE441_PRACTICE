package com.nemisolv.phonelist;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Phone> phones;
    private PhoneAdapter adapter;
    private RecyclerView recyclerView;

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
        phones = List.of(
                new Phone("Iphone 14 Pro Max", 1000, R.drawable.image2),
                new Phone("Samsung Galaxy S21", 800, R.drawable.image1),
                new Phone("Google Pixel 6", 700, R.drawable.image3),
                new Phone("OnePlus 9 Pro", 900, R.drawable.image4),
                new Phone("Xiaomi Mi 11", 600, R.drawable.image5),
                new Phone("Oppo Find X3 Pro", 750, R.drawable.vietnam),
                new Phone("Vivo X60 Pro", 650, R.drawable.wales),
                new Phone("Realme GT", 500, R.drawable.uk),
                new Phone("Sony Xperia 1 III", 850, R.drawable.usa),
                new Phone("Asus Zenfone 8", 650, R.drawable.france)

        );

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new PhoneAdapter(phones);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

    }
}