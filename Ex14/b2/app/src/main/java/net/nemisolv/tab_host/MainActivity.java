package net.nemisolv.tab_host;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv1, lv2, lv3;
    TabHost tab;
    ArrayList<Song> list1, list2, list3;
    CustomListAdapter adapter1, adapter2, adapter3;

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
        addControl();
        addEvent();


    }

    private void addEvent() {
        tab.setOnTabChangedListener(tabId -> {
            switch (tabId) {
                case "t1":
                    if (list1.isEmpty()) {
                        list1.add(new Song("52300", "Em là ai Tôi là ai", true));
                        list1.add(new Song("52600", "Chén Đắng", true));
                        list1.add(new Song("52567", "Buồn của Anh", true));
                        adapter1.notifyDataSetChanged();
                    }
                    break;
                case "t2":
                    list2.clear();
                    list2.add(new Song("57236", "Gởi em ở cuối sông hồng", false));
                    list2.add(new Song("51548", "Say tình", false));
                    adapter2.notifyDataSetChanged();
                    break;
                case "t3":
                    list3.clear();
                    list3.add(new Song("57689", "Hát với dòng sông", true));
                    list3.add(new Song("58716", "Say tình _ Remix", false));
                    adapter3.notifyDataSetChanged();
                    break;
            }
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void addControl() {
        tab = findViewById(R.id.tabhost);
        tab.setup();

        // Tab 1
        TabHost.TabSpec tab1 = tab.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("", getResources().getDrawable(R.drawable.ic_search));
        tab.addTab(tab1);

        // Tab 2
        TabHost.TabSpec tab2 = tab.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("", getResources().getDrawable(R.drawable.ic_dashboard));
        tab.addTab(tab2);

        // Tab 3
        TabHost.TabSpec tab3 = tab.newTabSpec("t3");
        tab3.setContent(R.id.tab3);
        tab3.setIndicator("", getResources().getDrawable(R.drawable.ic_heart));
        tab.addTab(tab3);

        // ListView and Adapter Setup
        lv1 = findViewById(R.id.listViewTab1);
        lv2 = findViewById(R.id.listViewTab2);
        lv3 = findViewById(R.id.listViewTab3);

        // Initialize song lists for each tab
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        list1.add(new Song("52300", "Em là ai Tôi là ai", true));
        list1.add(new Song("52600", "Chén Đắng", true));
        list1.add(new Song("52567", "Buồn của Anh", true));

        // Initialize the adapters and set them to the ListViews
        adapter1 = new CustomListAdapter(this, list1);
        adapter2 = new CustomListAdapter(this, list2);
        adapter3 = new CustomListAdapter(this, list3);

        // Set adapters for each ListView
        lv1.setAdapter(adapter1);
        lv2.setAdapter(adapter2);
        lv3.setAdapter(adapter3);
    }

}