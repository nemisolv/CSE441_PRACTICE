package net.nemisolv.tab_host;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomListAdapter extends ArrayAdapter<Song> {

    public CustomListAdapter(Context context, List<Song> songs) {

        super(context, 0, songs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        Song song = getItem(position);

        TextView songId = convertView.findViewById(R.id.txt_id);
        TextView songName = convertView.findViewById(R.id.txt_song_name);
        ImageView favoriteIcon = convertView.findViewById(R.id.btn_is_favorite);

        songId.setText(song.getId());
        songName.setText(song.getTitle());

        // Set heart icon based on favorite status
        favoriteIcon.setImageResource(song.isFavorite() ? R.drawable.heart_fill : R.drawable.heart_outline);

        favoriteIcon.setOnClickListener(v -> {
            song.setFavorite(!song.isFavorite());
            favoriteIcon.setImageResource(song.isFavorite() ? R.drawable.heart_fill : R.drawable.heart_outline);
        });

        return convertView;
    }
}
