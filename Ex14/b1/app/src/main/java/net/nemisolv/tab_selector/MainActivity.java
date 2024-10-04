package net.nemisolv.tab_selector;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText inputA, inputB;
    Button btnAdd;
    RecyclerView recyclerView;
    List<String> list;
    HistoryAdapter adapter; // Use the custom adapter for RecyclerView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControl(); // Initialize controls
        addEvent(); // Set up event listeners
    }

    private void addEvent() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }

            private void add() {
                try {
                    int a = Integer.parseInt(inputA.getText().toString());
                    int b = Integer.parseInt(inputB.getText().toString());
                    String result = a + " + " + b + " = " + (a + b);
                    list.add(result);
                    adapter.notifyDataSetChanged();
                    inputA.setText("");
                    inputB.setText("");
                } catch (NumberFormatException e) {
                }
            }
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void addControl() {
        TabHost tab = findViewById(R.id.tabhost);
        tab.setup();

        // Setup Tab 1
        TabHost.TabSpec tab1 = tab.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("Tab 1",
                ContextCompat.getDrawable(this, R.drawable.ic_add));        tab.addTab(tab1);

        // Setup Tab 2
        TabHost.TabSpec tab2 = tab.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("Tab 2", getResources().getDrawable(R.drawable.ic_history));
        tab.addTab(tab2);

        // Initialize views
        inputA = findViewById(R.id.input_a);
        inputB = findViewById(R.id.input_b);
        btnAdd = findViewById(R.id.btn_add);
        recyclerView = findViewById(R.id.recycler_view_history);

        // Initialize RecyclerView
        list = new ArrayList<>();
        adapter = new HistoryAdapter(list); // Use the custom adapter
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set LayoutManager
    }
}
