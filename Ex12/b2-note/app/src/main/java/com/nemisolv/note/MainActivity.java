package com.nemisolv.note;

import android.app.AlertDialog;
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
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btnAddWork;
    private EditText workName,workHour, workMinute;
    private TextView txtDateToday;
    private ListView lvWork;
    private List<String> listWork;
    private ArrayAdapter<String> adapterWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        listWork = new ArrayList<>();
        adapterWork = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listWork);
        lvWork.setAdapter(adapterWork);
        logic();
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
            
        });



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