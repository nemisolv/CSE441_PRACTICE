package net.nemisolv.stock_exchange;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView lvStockExchange;
    TextView txtDateToday;
    ArrayList<ExchangeRate> exchangeRates = new ArrayList<>();
    ExchangeRateAdapter exchangeRateAdapter;
    private static final String URL_EXCHANGE_RATE = "https://dongabank.com.vn/exchange/export";

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
        lvStockExchange = findViewById(R.id.lv_stock_exchange);
        txtDateToday = findViewById(R.id.txt_date_today);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        txtDateToday.setText(sdf.format(System.currentTimeMillis()));
        exchangeRateAdapter = new ExchangeRateAdapter(this, exchangeRates);
        lvStockExchange.setAdapter(exchangeRateAdapter);
        new ExchangeRateTask().execute();
    }

    class ExchangeRateTask extends AsyncTask<Void, Void, ArrayList<ExchangeRate>> {

        @Override
        protected ArrayList<ExchangeRate> doInBackground(Void... voids) {
            ArrayList<ExchangeRate> exchangeRates = new ArrayList<>();
            try {
                URL url = new URL(URL_EXCHANGE_RATE);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setRequestProperty("Accept", "*/*");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Compatible)");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                    BufferedReader reader = new BufferedReader(isr);
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    String json = response.toString();
                    json = json.replace("(", "").replace(")", "");
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray jsonArray = jsonObject.getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        String currencyType = item.getString("type");
                        String buyCash = item.getString("muatienmat");
                        String sellCash = item.getString("bantienmat");
                        String buySecurities = item.getString("muack");
                        String sellSecurities = item.getString("banck");
                        String imgUrl = item.getString("imageurl");
                        Bitmap thumbnail = null;
                        if (!imgUrl.isEmpty()) {
                            URL urlImg = new URL(imgUrl);
                            HttpURLConnection connectionImg = (HttpURLConnection) urlImg.openConnection();
                            connectionImg.setRequestMethod("GET");
                            connectionImg.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                            connectionImg.setRequestProperty("Accept", "*/*");
                            connectionImg.setRequestProperty("User-Agent", "Mozilla/5.0 (Compatible)");
                            InputStream isImg = connectionImg.getInputStream();
                            thumbnail = BitmapFactory.decodeStream(isImg);
                        }
                        ExchangeRate exchangeRate = new ExchangeRate(currencyType, imgUrl, thumbnail, buyCash, sellCash, buySecurities, sellSecurities);
                        exchangeRates.add(exchangeRate);
                    }
                } else {
                    throw new IOException("HTTP error code: " + responseCode);
                }
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (ProtocolException e) {
                throw new RuntimeException(e);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return exchangeRates;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            exchangeRateAdapter.clear();
        }

        @Override
        protected void onPostExecute(ArrayList<ExchangeRate> result) {
            super.onPostExecute(result);
            exchangeRateAdapter.clear();
            exchangeRateAdapter.addAll(result);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


}