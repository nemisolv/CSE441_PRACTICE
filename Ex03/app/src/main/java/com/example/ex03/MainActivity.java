package com.example.ex03;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btnSum, btnSub, btnMul, btnDiv;
    EditText editA, editB, editRes;

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

        btnSum = findViewById(R.id.btn_sum);
        btnSub = findViewById(R.id.btn_sub);
        btnMul = findViewById(R.id.btn_mul);
        btnDiv = findViewById(R.id.btn_div);
        editA = findViewById(R.id.edit_text_a);
        editB = findViewById(R.id.edit_text_b);
        editRes = findViewById(R.id.edit_text_res);

        btnSum.setOnClickListener(v -> {
            if (editA.getText().toString().isEmpty() || editB.getText().toString().isEmpty()) {
                editRes.setText("Vui lòng nhập số");
                return;
            }
            int a = Integer.parseInt(editA.getText().toString());
            int b = Integer.parseInt(editB.getText().toString());
            editRes.setText(String.valueOf(a + b));
        });
        btnSub.setOnClickListener(v -> {
            if (editA.getText().toString().isEmpty() || editB.getText().toString().isEmpty()) {
                editRes.setText("Vui lòng nhập số");
                return;
            }

            int a = Integer.parseInt(editA.getText().toString());
            int b = Integer.parseInt(editB.getText().toString());
            editRes.setText(String.valueOf(a - b));
        });
        btnMul.setOnClickListener(v -> {
            if (editA.getText().toString().isEmpty() || editB.getText().toString().isEmpty()) {
                editRes.setText("Vui lòng nhập số");
                return;
            }

            int a = Integer.parseInt(editA.getText().toString());
            int b = Integer.parseInt(editB.getText().toString());
            editRes.setText(String.valueOf(a * b));
        });
        btnDiv.setOnClickListener(v -> {
            if (editA.getText().toString().isEmpty() || editB.getText().toString().isEmpty()) {
                editRes.setText("Vui lòng nhập số");
                return;
            }
            if (editB.getText().toString().equals("0")) {
                editRes.setText("Không thể chia cho 0");
                return;
            }

            int a = Integer.parseInt(editA.getText().toString());
            int b = Integer.parseInt(editB.getText().toString());
            editRes.setText(String.valueOf(a / b));
        });



    }
}