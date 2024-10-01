package com.nemisolv.phonelist;

import androidx.annotation.NonNull;

public class Phone {
    private String name;
    private int imgId;

    public Phone(String name, int imgId) {
        this.name = name;
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    @NonNull
    @Override
    public String toString() {
        return name + " : " + imgId;
    }
}
