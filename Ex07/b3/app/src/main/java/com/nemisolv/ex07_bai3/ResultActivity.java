package com.nemisolv.ex07_bai3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {
    private Button btnSum, btnSub;
    private EditText txtA, txtB;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setupViews();
        btnSum.setOnClickListener(v -> {
            int a = Integer.parseInt(txtA.getText().toString());
            int b = Integer.parseInt(txtB.getText().toString());
            intent.putExtra("res", a + b);
            setResult(1,intent);
            finish();

        });
        btnSub.setOnClickListener(v -> {
            int a = Integer.parseInt(txtA.getText().toString());
            int b = Integer.parseInt(txtB.getText().toString());
            intent.putExtra("res", a - b);
            setResult(2,intent);
            finish();
        });
    }

    private void setupViews() {
        txtA = findViewById(R.id.txt_a);
        txtB = findViewById(R.id.txt_b);
        btnSum = findViewById(R.id.btn_sum);
        btnSub = findViewById(R.id.btn_sub);

        intent = getIntent();
        if(intent.hasExtra("a") && intent.hasExtra("b")){
            txtA.setText(String.valueOf(intent.getIntExtra("a", 0)));
            txtB.setText(String.valueOf(intent.getIntExtra("b", 0)));
        }


    }
}