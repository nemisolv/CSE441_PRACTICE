package com.nemisolv.b3;

public class Country {
    private String name;
    private String capital;
    private int flagId;
    public Country(String name, String capital, int flagId) {
        this.name = name;
        this.capital = capital;
        this.flagId = flagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public int getFlagId() {
        return flagId;
    }

    public void setFlagId(int flagId) {
        this.flagId = flagId;
    }
}
