package net.nemisolv.internetworking;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String API_URL_XML = "https://dadosabertos.camara.leg.br/api/v2/grupos?ordem=ASC&ordenarPor=id";
    Button btnParser;
    ArrayAdapter<String> adapter;
    ListView lv;
    List<String> list;

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
        btnParser = findViewById(R.id.button);
        lv = findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);
        btnParser.setOnClickListener(v -> {
            MyAsyncTask task = new MyAsyncTask();
            task.execute();
        });
    }

    class MyAsyncTask extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            ArrayList<String> list = new ArrayList<>();
            try {
                XMLParser myparser = new XMLParser();
                String json = myparser.getXmlFromUrl(MainActivity.API_URL_XML); // Use JSON parser
                JSONObject jsonObject = new JSONObject(json);
                JSONArray dadosArray = jsonObject.getJSONArray("dados");

                for (int i = 0; i < dadosArray.length(); i++) {
                    JSONObject grupo = dadosArray.getJSONObject(i);
                    String id = grupo.getString("id");
                    String nome = grupo.getString("nome");
                    list.add("ID: " + id + " - Nome: " + nome);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            adapter.clear();
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
            adapter.clear();
            adapter.addAll(result);
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}