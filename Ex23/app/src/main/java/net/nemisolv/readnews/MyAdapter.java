package net.nemisolv.readnews;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MyAdapter extends ArrayAdapter<News> {
    private Activity context;
    private List<News> newsList;
    public MyAdapter(Activity context, List<News> newsList) {
        super(context, R.layout.list_item, newsList);
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        News news = newsList.get(position);
        ImageView ivImage = view.findViewById(R.id.img_thumb);
        ivImage.setImageBitmap(news.getImage());
        ((TextView)view.findViewById(R.id.txt_title)).setText(news.getTitle());
        ((TextView)view.findViewById(R.id.txt_description)).setText(news.getDescription());

        convertView.setOnClickListener(v -> {
            // Open the news in a browser
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getUrl()));
            context.startActivity(intent);
        });
        return view;
    }
}
