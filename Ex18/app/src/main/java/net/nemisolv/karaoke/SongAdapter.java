package net.nemisolv.karaoke;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SongAdapter extends ArrayAdapter<Song> {
    private final List<Song> songs; // Make the list final for immutability
    private final LayoutInflater inflater; // Use LayoutInflater instance to avoid repeated calls

    public SongAdapter(@NonNull Context context, int resource, List<Song> songs) {
        super(context, resource, songs); // Pass songs to super
        this.songs = songs;
        this.inflater = LayoutInflater.from(context); // Initialize inflater once
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        // Reuse convertView if possible
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtSongId = convertView.findViewById(R.id.txt_song_id);
            viewHolder.txtSongName = convertView.findViewById(R.id.txt_song_name);
            viewHolder.imgFavorite = convertView.findViewById(R.id.img_favorite);
            convertView.setTag(viewHolder); // Store the view holder in the convertView
        } else {
            viewHolder = (ViewHolder) convertView.getTag(); // Retrieve the view holder
        }

        Song song = songs.get(position);
        viewHolder.txtSongId.setText(song.getId());
        viewHolder.txtSongName.setText(song.getName());
        viewHolder.imgFavorite.setVisibility(song.isFavorite()==1 ? View.VISIBLE : View.GONE);

        // Favorite button click listener
        viewHolder.imgFavorite.setOnClickListener(v -> {
            song.setFavorite(song.isFavorite()==1 ? 0 : 1);
            viewHolder.imgFavorite.setVisibility(song.isFavorite()==1 ? View.VISIBLE : View.GONE);

            // Store the favorite status in the database
            ContentValues values = new ContentValues();
            values.put("favorite", song.isFavorite()==1 ? 1 : 0); // Store 1 for true and 0 for false
            MainActivity.db.update("songs", values, "id = ?", new String[]{song.getId()});
        });

        // Song name click listener
        viewHolder.txtSongName.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ResultActivity.class);
            intent.putExtra("song", song);
            getContext().startActivity(intent);
        });

        return convertView; // Return the reused or newly created view
    }

    // ViewHolder class to hold views for better performance
    static class ViewHolder {
        TextView txtSongId;
        TextView txtSongName;
        ImageView imgFavorite;
    }
}
