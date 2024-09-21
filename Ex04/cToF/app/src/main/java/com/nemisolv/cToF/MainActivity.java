package com.nemisolv.cToF;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btnC, btnF, btnClear;
    EditText txt_c, txt_f;

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

        btnC = findViewById(R.id.btnC);
        btnF = findViewById(R.id.btnF);
        btnClear = findViewById(R.id.btnClear);
        txt_c = findViewById(R.id.txt_c);
        txt_f = findViewById(R.id.txt_f);

        btnC.setOnClickListener(v -> {
            if(txt_f.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
                return;
            }
            float c = Float.parseFloat(txt_f.getText().toString());
            float f = (c * 9 / 5) + 32;
            txt_c.setText(String.valueOf(f));
        });

        btnF.setOnClickListener(v -> {
            if(txt_c.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
                return;
            }
            float f = Float.parseFloat(txt_c.getText().toString());
            float c = (f - 32) * 5 / 9;
            txt_c.setText(String.valueOf(c));
        });

        btnClear.setOnClickListener(v -> {
            txt_c.setText("");
            txt_f.setText("");
        });


    }
}