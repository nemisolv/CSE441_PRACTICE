package net.nemisolv.readnews;

import android.graphics.Bitmap;

public class News {
    private Bitmap image;
    private String title, description, url;

    public News() {
    }

    public News(Bitmap image, String title, String description, String url) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
