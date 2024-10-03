package com.nemisolv.b3;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Country> countryList;
    private RecyclerView recyclerView;

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
        initial();
        CountryAdapter adapter = new CountryAdapter(countryList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        Drawable drawable = getDrawable(R.drawable.divider);
        if (drawable != null) {
            dividerItemDecoration.setDrawable(drawable);
        }
        recyclerView.addItemDecoration(dividerItemDecoration);



    }

    private void initial() {

        recyclerView = findViewById(R.id.recycler_countries);
        countryList = List.of(
                new Country("Việt Nam","Hà Nội",R.drawable.vietnam),
                new Country("United States","Washington",R.drawable.usa),
                new Country("United Kingdom","London",R.drawable.uk),
                new Country("France","Paris",R.drawable.france),
                new Country("Germany","Berlin",R.drawable.germany),
                new Country("Wales","Cardiff",R.drawable.wales),
                new Country("Scotland","Edinburgh",R.drawable.image1),
                new Country("Ireland","Dublin",R.drawable.image2),
                new Country("Australia","Canberra",R.drawable.image3),
                new Country("New Zealand","Wellington",R.drawable.image4),
                new Country("Canada","Ottawa",R.drawable.image5)
        );
    }
}