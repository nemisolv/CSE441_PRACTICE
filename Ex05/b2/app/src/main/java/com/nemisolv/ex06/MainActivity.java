package com.nemisolv.ex06;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnContinue,btnExit, btnCal;
    EditText editTextA, editTextB, editTextC;
    TextView txtRes;

    @SuppressLint("WrongViewCast")
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

        btnContinue = findViewById(R.id.btnContinue);
        btnExit = findViewById(R.id.btnExit);
        btnCal = findViewById(R.id.btnCal);
        btnContinue.setOnClickListener(this);
        btnCal.setOnClickListener(this);
        btnExit.setOnClickListener(this);

        editTextA = findViewById(R.id.editTextA);
        editTextB = findViewById(R.id.editTextB);
        editTextC = findViewById(R.id.editTextC);
        txtRes = findViewById(R.id.txtRes);



    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnContinue) {
            editTextA.setText("");
            editTextB.setText("");
            editTextC.setText("");
            txtRes.setText("");
            // focus on editTextA
            editTextA.requestFocus();
        }else if(view.getId() == R.id.btnExit) {
            finish();
        }else if(view.getId() == R.id.btnCal) {
            float a = Float.parseFloat(editTextA.getText().toString());
            float b = Float.parseFloat(editTextB.getText().toString());
            float c = Float.parseFloat(editTextC.getText().toString());
            float delta = b*b - 4*a*c;
            if(delta <0) {
                txtRes.setText("Phương trình vô nghiệm");
            }else if(delta == 0) {
                txtRes.setText("Phương trình có nghiệm kép x1 = x2 = " + (-b / (2 * a)));
            }else {
                float x1 = (float) ((-b + Math.sqrt(delta)) / (2 * a));
                float x2 = (float) ((-b - Math.sqrt(delta)) / (2 * a));
                txtRes.setText("Phương trình có 2 nghiệm phân biệt x1 = " + x1 + " và x2 = " + x2);
            }
        }

    }
}