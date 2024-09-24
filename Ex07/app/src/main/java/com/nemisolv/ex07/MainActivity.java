package com.nemisolv.ex07;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button btnSubmit;
    private EditText editTextFullName,editTextId, editTextInfo;
    private CheckBox chkReadNews, chkReadBook, chkReadCoding;
    private RadioGroup radioGroup;

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

        setupView();



        btnSubmit.setOnClickListener(v -> {
            String fullName = editTextFullName.getText().toString();
            String cmnd = editTextId.getText().toString();

            // validate
            if(fullName.isEmpty() || fullName.trim().length() <3) {
                editTextFullName.setError("Fullname must be at least 3 characters");
                return;
            }
            if(cmnd.isEmpty() || cmnd.trim().length() !=9){
                editTextId.setError("ID must be 9 characters");
                return;
            }
            String degree ="";
            int id = radioGroup.getCheckedRadioButtonId();
            if(id == -1) {
                Toast.makeText(this, "Please choose degree", Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton rad = findViewById(id);
            degree = rad.getText().toString();

            String hobbies = "";
            if(!chkReadCoding.isChecked() && !chkReadBook.isChecked() && !chkReadNews.isChecked()) {
                Toast.makeText(this, "Please choose at least a hobby", Toast.LENGTH_SHORT).show();
                return;
            }

            if(chkReadNews.isChecked()) {
                hobbies+= chkReadNews.getText()+"\n";
            }
            if(chkReadBook.isChecked()) {
                hobbies+= chkReadBook.getText() + "\n";
            }
            if(chkReadCoding.isChecked()) {
                hobbies+=chkReadCoding.getText()+"\n";
            }
            String extraInfo = editTextInfo.getText().toString();
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("Information")
                    .setMessage("Fullname: "+fullName+"\n"+
                            "ID: "+cmnd+"\n"+
                            "Degree: "+degree+"\n"+
                            "Hobbies: "+hobbies+"\n"+
                            "Extra Info: "+extraInfo)
                    .setPositiveButton("Close", (dialog, which) -> dialog.cancel())
                    .create();
            alertDialog.show();






        });
    }



    private void setupView() {
        btnSubmit = findViewById(R.id.btn_submit);
        editTextFullName = findViewById(R.id.editTextFullname);
        editTextId = findViewById(R.id.editTextId);
        editTextInfo = findViewById(R.id.editTextInfo);
        chkReadNews = findViewById(R.id.chkReadNews);
        chkReadBook = findViewById(R.id.chkReadBook);
        chkReadCoding = findViewById(R.id.chkReadCoding);
        radioGroup = findViewById(R.id.radioGroup);

        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Exit")
                        .setMessage("Do you want to exit?")
                        .setPositiveButton("Yes", (dialog, which) -> finish())
                        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                        .create();
                alertDialog.show();

            }

        });


    }
}