package net.nemisolv.stock_exchange;

import android.graphics.Bitmap;

public class ExchangeRate {
    private String currencyType;
    private String imgUrl;
    private Bitmap thumbnail;
    private String buyCash;
    private String sellCash;
    private String buySecurities;
    private String sellSecurities;

    public ExchangeRate(String currencyType, String imgUrl, Bitmap thumbnail,
                        String buyCash, String sellCash,
                        String buySecurities, String sellSecurities) {
        this.currencyType = currencyType;
        this.imgUrl = imgUrl;
        this.thumbnail = thumbnail;
        this.buyCash = buyCash;
        this.sellCash = sellCash;
        this.buySecurities = buySecurities;
        this.sellSecurities = sellSecurities;
    }

    // Getters and Setters
    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    // Convert double to String
    public String getBuyCash() {
        return String.valueOf(buyCash);
    }

    public void setBuyCash(String buyCash) {
        this.buyCash = buyCash;
    }

    public String getSellCash() {
        return String.valueOf(sellCash);
    }

    public void setSellCash(String sellCash) {
        this.sellCash = sellCash;
    }

    public String getBuySecurities() {
        return String.valueOf(buySecurities);
    }

    public void setBuySecurities(String buySecurities) {
        this.buySecurities = buySecurities;
    }

    public String getSellSecurities() {
        return String.valueOf(sellSecurities);
    }

    public void setSellSecurities(String sellSecurities) {
        this.sellSecurities = sellSecurities;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "currencyType='" + currencyType + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", buyCash=" + getBuyCash() +
                ", sellCash=" + getSellCash() +
                ", buySecurities=" + getBuySecurities() +
                ", sellSecurities=" + getSellSecurities() +
                '}';
    }
}