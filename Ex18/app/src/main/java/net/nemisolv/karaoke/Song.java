package net.nemisolv.karaoke;

import java.io.Serializable;

public class Song implements Serializable {
    private String id;
    private String name;
    private int favorite;
    private String author;
    private String lyric;

    public Song(String id, String title, int favorite, String author, String lyric) {
        this.id = id;
        this.name = title;
        this.favorite = favorite;
        this.author = author;
        this.lyric = lyric;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int isFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }
}
