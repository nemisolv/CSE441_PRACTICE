package com.nemisolv.b3;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CountryDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);

        ImageView flagImageView = findViewById(R.id.flagImageView);
        TextView countryNameTextView = findViewById(R.id.countryNameTextView);
        TextView capitalTextView = findViewById(R.id.capitalTextView);

        Intent intent = getIntent();
        String countryName = intent.getStringExtra("countryName");
        String capital = intent.getStringExtra("capital");
        int flagResource = intent.getIntExtra("flag", 0);

        countryNameTextView.setText(countryName);
        capitalTextView.setText("Capital: " + capital);
        flagImageView.setImageResource(flagResource);
    }
}
