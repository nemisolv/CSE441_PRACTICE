package net.nemisolv.sharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button btnSum, btnClear;
    private EditText inputA, inputB;
    private TextView txtHistory;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static final String PREFS_NAME = "calcHistory";
    private static final String KEY_HISTORY = "history";

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
        // Initialize Shared Preferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Load saved history
        loadHistory();

        btnSum.setOnClickListener(v -> {
            String a = inputA.getText().toString();
            String b = inputB.getText().toString();
            if (a.isEmpty() || b.isEmpty()) {
                Toast.makeText(this, "Please enter both numbers", Toast.LENGTH_SHORT).show();
                return;
            }
           try {
               int numA = Integer.parseInt(a);
               int numB = Integer.parseInt(b);
               int sum = numA + numB;
               String history = sharedPreferences.getString(KEY_HISTORY, "");
               String newEntry = numA + " + " + numB + " = " + sum;
               history = newEntry + "\n" + history;

               // Save updated history
               editor.putString(KEY_HISTORY, history);
               editor.apply();

               // Update TextView
               txtHistory.setText(history);
           }catch(NumberFormatException ex) {
                Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
                ex.printStackTrace();
           }
        });

        btnClear.setOnClickListener(v -> {
            txtHistory.setText("");
            editor.putString(KEY_HISTORY, "");
            editor.apply();
        });

    }

    private void setupViews() {
        btnSum = findViewById(R.id.btnSum);
        btnClear = findViewById(R.id.btnClear);
        inputA = findViewById(R.id.etNumberA);
        inputB = findViewById(R.id.etNumberB);
        txtHistory = findViewById(R.id.tvHistory);
    }

    private void loadHistory() {
        // Load history from Shared Preferences
        String history = sharedPreferences.getString(KEY_HISTORY, "");
        txtHistory.setText(history);
    }
}