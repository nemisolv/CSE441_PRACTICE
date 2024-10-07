package net.nemisolv.readnews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {
    public String getXmlFromUrl(String urlString) {
        String xml = null;
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(urlString);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            xml = EntityUtils.toString(httpEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
// return XML
        return xml;
    }

    public ArrayList<News> parseXML(String url) {
        List<News> mylist = new ArrayList<>();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            String xml = getXmlFromUrl(url); // getting XML from URL
            Log.d("XML", xml);
            parser.setInput(new StringReader(xml));
            int eventType = parser.getEventType();
            News currentItem = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
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
                        if (tagName.equals("item") && currentItem != null) {
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