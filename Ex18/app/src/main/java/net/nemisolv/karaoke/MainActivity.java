package net.nemisolv.karaoke;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editTextSearch;
    private List<Song> songs;
    public static SQLiteDatabase db;
    private static final String DB_PATH_SUFFIX = "/databases/";
    private static final String DB_NAME = "karaoke.sqlite";
    private List<Song> allSongs, favoriteSongs, searchSongs;
    private ListView lvSearchSongs, lvAllSongs, lvFavoriteSongs;
    private SongAdapter allSongsAdapter, favoriteSongsAdapter, searchSongsAdapter;

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

        copyDatabaseFromAssets();
        db = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        createSongsTable();
        initializeUIComponents();
        setupTabHost();
        setupSearchAutocomplete();
    }

    private void initializeUIComponents() {
        editTextSearch = findViewById(R.id.edit_text_search);
        ImageButton btnBackspace = findViewById(R.id.btn_backspace);
        btnBackspace.setOnClickListener(v -> {
            editTextSearch.setText("");
            editTextSearch.requestFocus();
            resetField();
        });
    }



    private void setupTabHost() {
        TabHost tabHost = findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("Search"); // You must set an indicator (text or drawable)
        tabHost.addTab(tab1);

        // Tab 2: All Songs
        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("All Songs"); // Provide a label for the tab
        tabHost.addTab(tab2);

        // Tab 3: Favorites
        TabHost.TabSpec tab3 = tabHost.newTabSpec("t3");
        tab3.setContent(R.id.tab3);
        tab3.setIndicator("Favorites"); // Provide a label for the tab
        tabHost.addTab(tab3);

        lvSearchSongs = findViewById(R.id.lv_tab1);
        lvAllSongs = findViewById(R.id.lv_tab2);
        lvFavoriteSongs = findViewById(R.id.lv_tab3);

        allSongs = new ArrayList<>();
        searchSongs = new ArrayList<>();
        favoriteSongs = new ArrayList<>();

        allSongsAdapter = new SongAdapter(this, 0, allSongs);
        favoriteSongsAdapter = new SongAdapter(this, 0, favoriteSongs);
        searchSongsAdapter = new SongAdapter(this, 0, searchSongs);


        lvAllSongs.setAdapter(allSongsAdapter);
        lvFavoriteSongs.setAdapter(favoriteSongsAdapter);
        lvSearchSongs.setAdapter(searchSongsAdapter);

        tabHost.setOnTabChangedListener(tabId -> {
            if ("t2".equals(tabId)) loadAllSongs();
            if ("t3".equals(tabId)) loadFavoriteSongs();
        });
    }

    private void setupSearchAutocomplete() {
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchSongs();
            }

            private void searchSongs() {
                String searchText = editTextSearch.getText().toString();
                searchSongs.clear();
                if (!searchText.isEmpty()) {
                    Cursor cursor = db.rawQuery("SELECT * FROM songs WHERE name LIKE ? OR id LIKE ?",
                            new String[]{"%" + searchText + "%", "%" + searchText + "%"});
                   searchSongs.clear();
                    while (cursor.moveToNext()) {
                        String id = cursor.getString(0);
                        String name = cursor.getString(1);
                        String author = cursor.getString(2);
                        String lyric = cursor.getString(3);
                        int favorite = cursor.getInt(4);
                        searchSongs.add(new Song(id, name, favorite, author, lyric));
                    }
                    cursor.close();
                    searchSongsAdapter.notifyDataSetChanged();


                }else {
                    resetField();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void resetField() {
        allSongs.clear();
        allSongsAdapter.notifyDataSetChanged();
    }

    private void createSongsTable() {
        db.execSQL("CREATE TABLE IF NOT EXISTS songs (id TEXT PRIMARY KEY, name TEXT, author TEXT, lyric TEXT, favorite INTEGER)");
        db.execSQL("DELETE FROM songs");
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM songs", null);
        if (cursor.moveToFirst() && cursor.getInt(0) == 0) {
            db.execSQL("INSERT INTO songs (id, name, author, lyric, favorite) VALUES ('1', 'Hello', 'Adele', 'Hello, it''s me', 1)");
            db.execSQL("INSERT INTO songs (id, name, author, lyric, favorite) VALUES ('2', 'Rolling in the Deep', 'Adele', 'There''s a fire starting in my heart', 0)");
            db.execSQL("INSERT INTO songs (id, name, author, lyric, favorite) VALUES ('3', 'Someone Like You', 'Adele', 'I heard that you''re settled down', 1)");
            db.execSQL("INSERT INTO songs (id, name, author, lyric, favorite) VALUES ('4', 'Love Story', 'Taylor Swift', 'We were both young when I first saw you', 0)");

        }
        cursor.close();
    }

    private void loadAllSongs() {
        allSongs.clear();
        Cursor cursor = db.rawQuery("SELECT * FROM songs", null);
        while (cursor.moveToNext()) {
            allSongs.add(new Song(cursor.getString(0), cursor.getString(1), cursor.getInt(4) , cursor.getString(2), cursor.getString(3)));
        }
        cursor.close();
        allSongsAdapter.notifyDataSetChanged();
    }

    private void loadFavoriteSongs() {
        favoriteSongs.clear();
        Cursor cursor = db.rawQuery("SELECT * FROM songs WHERE favorite = 1", null);
        while (cursor.moveToNext()) {
            favoriteSongs.add(new Song(cursor.getString(0), cursor.getString(1), cursor.getInt(4) , cursor.getString(2), cursor.getString(3)));
        }
        cursor.close();
        favoriteSongsAdapter.notifyDataSetChanged();
    }

    private void copyDatabaseFromAssets() {
        File dbFile = getDatabasePath(DB_NAME);
        if (!dbFile.exists()) {
            try {
                copyDatabaseFile();
                Toast.makeText(this, "Database copied from assets", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(this, "Error copying database: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void copyDatabaseFile() throws IOException {
        InputStream inputStream = getAssets().open(DB_NAME);
        String outFileName = getApplicationDataPath();
        File dbFolder = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!dbFolder.exists()) dbFolder.mkdir();
        OutputStream outputStream = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    private String getApplicationDataPath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DB_NAME;
    }


}
