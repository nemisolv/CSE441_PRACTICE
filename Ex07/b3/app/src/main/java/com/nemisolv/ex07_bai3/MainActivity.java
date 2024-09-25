package com.nemisolv.ex07_bai3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText txtA, txtB,txtRes;
    private Button btnRequireRes;
    private ActivityResultLauncher<Intent> resultLauncher;

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
        setupViews();
        registerActivityResultLauncher();
        btnRequireRes.setOnClickListener(v -> {
            int a = Integer.parseInt(txtA.getText().toString());
            int b = Integer.parseInt(txtB.getText().toString());
            Bundle bundle = new Bundle();
            bundle.putInt("a", a);
            bundle.putInt("b", b);
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtras(bundle);
//            startActivityForResult(intent, 1);
            resultLauncher.launch(intent);

        });

    }

    private void registerActivityResultLauncher() {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == 1 && result.getData() != null) {
                        int res = result.getData().getIntExtra("res", 0);
                        txtRes.setText("Tổng 2 số là: " + res);
                    } else if (result.getResultCode() == 2 && result.getData() != null) {
                        int res = result.getData().getIntExtra("res", 0);
                        txtRes.setText("Hiệu 2 số là: " + res);
                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            int res = data.getIntExtra("res", 0);
            txtRes.setText("Tổng 2 số là: " + res);
        }
        if(requestCode == 1 && resultCode == 2){
            int res = data.getIntExtra("res", 0);
            txtRes.setText("Hiệu 2 số là: " + res);
        }
    }

    private void setupViews() {
        txtA = findViewById(R.id.txt_a);
        txtB = findViewById(R.id.txt_b);
        txtRes = findViewById(R.id.txt_res);
        btnRequireRes = findViewById(R.id.btn_require_res);

    }
}