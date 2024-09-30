package com.nemisolv.note;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private Button btnAddWork;
    private EditText workName,workHour, workMinute;
    private TextView txtDateToday;
    private ListView lvWork;
    private List<String> listWork;
    private ArrayAdapter<String> adapterWork;

    // shared preferences
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFS_NAME = "note";
    private static final String WORK_LIST_KEY = "work_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);

        listWork = new ArrayList<>();
        adapterWork = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listWork);
        lvWork.setAdapter(adapterWork);
        loadData(); // Load data from SharedPreferences

        logic();
    }

    private void loadData() {
        Set<String> workSet = sharedPreferences.getStringSet(WORK_LIST_KEY, new HashSet<>());
        listWork.clear();
        listWork.addAll(workSet);
        adapterWork.notifyDataSetChanged();

    }

    private void logic() {
        btnAddWork.setOnClickListener(v -> {
            String work = workName.getText().toString();
            String hour = workHour.getText().toString();
            String minute = workMinute.getText().toString();
            if (work.isEmpty() || hour.isEmpty() || minute.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Info Missing");
                builder.setMessage("Please fill all the information");
                builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                builder.show();
                return;
            }
            String time = hour + "h " + minute + "m";
            listWork.add(work + " - " + time);
            adapterWork.notifyDataSetChanged();
            workName.setText("");
            workHour.setText("");
            workMinute.setText("");

            // save data
            saveData();

        });

        lvWork.setOnItemClickListener((parent, view, position, id) -> {
            listWork.remove(position);
            adapterWork.notifyDataSetChanged();
            saveData();
        });



    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> workSet = new HashSet<>(listWork);
        editor.putStringSet(WORK_LIST_KEY, workSet);
        editor.apply();
        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    private void setupViews() {

        btnAddWork = findViewById(R.id.btn_add_work);
        workName = findViewById(R.id.et_work);
        workHour = findViewById(R.id.et_hour);
        workMinute = findViewById(R.id.et_minute);
        txtDateToday = findViewById(R.id.txt_date_today);
        lvWork = findViewById(R.id.list_view_work);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = Calendar.getInstance().getTime();
        txtDateToday.setText(dateFormat.format(date));


    }
}