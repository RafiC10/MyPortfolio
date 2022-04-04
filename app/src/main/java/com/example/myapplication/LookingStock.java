package com.example.myapplication;

public class LookingStock {

    protected String name;
    protected double priceNow;
    private String key;


    public LookingStock() {
    }

    public LookingStock(String name, double priceNow, String key) {
        this.name = name;
        this.priceNow = priceNow;
        this.key=key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPriceNow() {
        return priceNow;
    }

    public void setPriceNow(double priceNow) {
        this.priceNow = priceNow;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
