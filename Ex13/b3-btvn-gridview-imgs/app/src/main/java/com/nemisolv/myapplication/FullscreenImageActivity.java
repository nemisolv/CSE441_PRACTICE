package com.nemisolv.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class FullscreenImageActivity extends AppCompatActivity {
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_image);

        ImageView imageView = findViewById(R.id.fullscreenImageView);
        btnBack = findViewById(R.id.btn_back);

        // Lấy ID của hình ảnh được truyền từ MainActivity
        int imageId = getIntent().getIntExtra("imageId", -1);
        if (imageId != -1) {
            imageView.setImageResource(imageId);
        }

        btnBack.setOnClickListener(v -> {
            finish();
        });
    }
}
