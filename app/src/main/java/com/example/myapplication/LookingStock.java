package com.example.myapplication;

public class LookingStock
{//עצם מסוג מנייה לצפייה הכולל שם,מחיר נוכחי,Key
    protected String name;//שם מנייה
    protected double priceNow;//מחיר מנייה נוכחי
    protected String key;//key in database


    public LookingStock() {
    }

    public LookingStock(String name, double priceNow, String key) {
        //בנאי
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
