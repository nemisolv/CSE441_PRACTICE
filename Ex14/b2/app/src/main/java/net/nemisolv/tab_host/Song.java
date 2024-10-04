package net.nemisolv.tab_host;

public class Song {
   private String title;
    private boolean isFavorite;
    private String id;

    public Song( String id,String title, boolean isFavorite) {
        this.title = title;
        this.id = id;
        this.isFavorite = isFavorite;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
