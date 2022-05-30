package com.example.myapplication;

/**
 * The type Looking stock.
 */
public class LookingStock
{//עצם מסוג מנייה לצפייה הכולל שם,מחיר נוכחי,Key
    /**
     * The Name.
     */
    protected String name;//שם מנייה
    /**
     * The Price now.
     */
    protected double priceNow;//מחיר מנייה נוכחי
    /**
     * The Key.
     */
    protected String key;//key in database


    /**
     * Instantiates a new Looking stock.
     */
    public LookingStock() {
    }

    /**
     * Instantiates a new Looking stock.
     *
     * @param name     the name
     * @param priceNow the price now
     * @param key      the key
     */
    public LookingStock(String name, double priceNow, String key) {
        //בנאי
        this.name = name;
        this.priceNow = priceNow;
        this.key=key;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets price now.
     *
     * @return the price now
     */
    public double getPriceNow() {
        return priceNow;
    }

    /**
     * Sets price now.
     *
     * @param priceNow the price now
     */
    public void setPriceNow(double priceNow) {
        this.priceNow = priceNow;
    }

    /**
     * Gets key.
     *
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets key.
     *
     * @param key the key
     */
    public void setKey(String key) {
        this.key = key;
    }
}
