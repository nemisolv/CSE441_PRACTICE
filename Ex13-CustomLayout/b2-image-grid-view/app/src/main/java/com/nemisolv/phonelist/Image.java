package com.nemisolv.phonelist;

import androidx.annotation.NonNull;

public class Image {
    private int imgId;
    private String name;

    public Image( String name,int imgId) {
        this.imgId = imgId;
        this.name = name;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return name + " : " + imgId;
    }
}
