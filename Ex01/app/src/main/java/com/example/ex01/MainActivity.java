package com.example.ex01;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText editTextA;
    private EditText editTextB;
    private EditText editTextRes;
    private Button btnRes;

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
        editTextA = findViewById(R.id.edit_text_A);
        editTextB = findViewById(R.id.edit_text_B);
        editTextRes = findViewById(R.id.edit_text_res);
        btnRes = findViewById(R.id.btn_res);
        btnRes.setOnClickListener(v -> {
            int a = Integer.parseInt(editTextA.getText().toString());
            int b = Integer.parseInt(editTextB.getText().toString());
            editTextRes.setText(String.valueOf(a + b));
        });
    }
}