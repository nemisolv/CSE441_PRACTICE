package com.nemisolv.ex08_b1;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SMSActivity extends AppCompatActivity {
    private ImageButton imageButtonSendSMS;
    private EditText editSms;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_smsactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupViews();
        imageButtonSendSMS.setOnClickListener(v -> {
            String message  = editSms.getText().toString();
            Intent intent = new Intent(Intent.ACTION_SENDTO , Uri.parse("smsto:" + message));
            startActivity(intent);
        });
        btnBack.setOnClickListener(v -> {
            finish();
        });


    }

    private void setupViews() {
        imageButtonSendSMS = findViewById(R.id.image_btn_send_sms);
        editSms = findViewById(R.id.edit_sms);
        btnBack = findViewById(R.id.btn_back);
    }
}