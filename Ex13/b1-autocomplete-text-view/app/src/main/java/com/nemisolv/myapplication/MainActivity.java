package com.nemisolv.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView selection;
    private AutoCompleteTextView singleComplete;
    private MultiAutoCompleteTextView multiAutoCompleteTextView;

    String arr[] = {"Hà Nội", "Nam Định", "Kiên Giang", "Cà Mau", "TP. HỒ CHÍ MINH","Vũng Tàu","Ninh Bình", "Nha Trang"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr);
        singleComplete.setAdapter(adapter);

        multiAutoCompleteTextView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr));
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        singleComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                selection.setText(singleComplete.getText());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void setupViews() {
        selection = findViewById(R.id.txt_selection);
        singleComplete = findViewById(R.id.autoCompleteTextView);
        multiAutoCompleteTextView = findViewById(R.id.multiAutoCompleteTextView);
    }
}