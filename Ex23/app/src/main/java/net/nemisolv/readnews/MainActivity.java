package net.nemisolv.readnews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static ListView lv1;
    List<News> mylist;
    MyAdapter myadapter;
    String URL= "https://vnexpress.net/rss/tin-moi-nhat.rss";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv1= findViewById(R.id.lvNews);
        mylist = new ArrayList<>();
        myadapter = new
                MyAdapter(MainActivity.this,mylist);
        lv1.setAdapter(myadapter);
        LoadExampleTask task = new LoadExampleTask();
        task.execute();
    }
    class LoadExampleTask extends AsyncTask<Void, Void, ArrayList<News>> {
        @Override
        protected ArrayList<News> doInBackground(Void... voids) {
            List<News> mylist = new ArrayList<>();
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                XMLParser myparser = new XMLParser();
                String xml = myparser.getXmlFromUrl(URL); // getting XML from URL
                parser.setInput(new StringReader(xml));
                int eventType = parser.getEventType();
                String title = "", link = "", description = "";

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tagName = parser.getName();
                    News currentItem = null;
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            tagName = parser.getName();
                            if (tagName.equals("item")) {
                                currentItem = new News();
                            } else if (currentItem != null) {
                                if (tagName.equals("title")) {
                                    currentItem.setTitle(parser.nextText());
                                } else if (tagName.equals("description")) {
                                    currentItem.setDescription(parser.nextText());
                                } else if (tagName.equals("link")) {
                                    currentItem.setUrl(parser.nextText());
                                } else if (tagName.equals("enclosure")) {
                                    String imgUrl = parser.getAttributeValue(null, "url");
                                    currentItem.setImage(loadImage(imgUrl)); // Load as Bitmap
                                }
                            }
                            break;

                        case XmlPullParser.END_TAG:
                            if (parser.getName().equals("item") && currentItem != null) {
                                mylist.add(currentItem);
                            }
                            break;
                    }
                    eventType = parser.next();
                }
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
            return (ArrayList<News>) mylist;
        }

        private String extractImageUrl(String description) {
            try {
                return description.substring(description.indexOf("src=\"") + 5, description.indexOf("\"", description.indexOf("src=\"") + 5));
            } catch (Exception e) {
                return ""; // Fallback or default image URL
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            myadapter.clear();
        }

        @Override
        protected void onPostExecute(ArrayList<News> result) {
            super.onPostExecute(result);
            myadapter.addAll(result);
        }
        private Bitmap loadImage(String imgUrl) {
            try {
                URL url = new URL(imgUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                Log.e("MainActivity", "Error loading image: " + imgUrl, e);
                return null; // Return null if image loading fails
            }
        }
    }
}