package com.nemisolv.ex08_b1;

import android.annotation.SuppressLint;
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

public class CallActivity extends AppCompatActivity {
    private Button  btnBack;
    private ImageButton imgBtnCall;
    private EditText editPhoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_call);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setupViews();
        imgBtnCall.setOnClickListener(
                v-> {
                    String phoneNumber  = editPhoneNum.getText().toString();
                    Intent intent = new Intent(Intent.ACTION_CALL , Uri.parse("tel:" + phoneNumber));
                    if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != 0){
                        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
                    }
                    startActivity(intent);
                } );
        btnBack.setOnClickListener(v -> finish());
    }

    @SuppressLint("WrongViewCast")
    private void setupViews() {
        imgBtnCall = findViewById(R.id.image_btn_send_sms);
        btnBack = findViewById(R.id.btn_back);
        editPhoneNum = findViewById(R.id.edit_sms);


    }
}