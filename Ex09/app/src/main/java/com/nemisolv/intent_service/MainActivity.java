package com.nemisolv.intent_service;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImageButton btnPlay,btnStop;
    Boolean flag = true;

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
        btnPlay = findViewById(R.id.img_btn_play);
        btnStop = findViewById(R.id.img_btn_stop);
        btnPlay.setOnClickListener(v -> {
            Intent intent = new Intent(this, MyService.class);
            startService(intent);
            if(!flag) {
                btnPlay.setImageResource(R.drawable.ic_pause);
                flag = true;
            }else {
                btnPlay.setImageResource(R.drawable.ic_play);
                flag = false;
            }
        });
        btnStop.setOnClickListener(v -> {
            Intent intent = new Intent(this, MyService.class);
            stopService(intent);
            btnPlay.setImageResource(R.drawable.ic_play);
            flag = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirm Exit");
            builder.setMessage("Are you sure you want to exit?");
            builder.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.dismiss();
            });
            builder.setPositiveButton("Exit", (dialog, which) -> {
                finish();
            });
            builder.show();

//            finish();
        });
    }
}