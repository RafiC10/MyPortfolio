package com.example.myapplication;


/**
 * The type Invest stock.
 */
public class InvestStock extends LookingStock
{//עצם מסוג מנייה להשקעה אשר יורשת ממנייה לצפייה ומופיסה גם מחיר קנייה,עמלה,כמות מניות,שווי מנייה כולל

    private double buyingPrice;//מחיר קניייה
    private double comission;//עמלה
    private double amount;//כמות מניות שנקנו
        private double TotalWorthOfStock;//שווי כולל של המניות (מחיר נוכחי כפול כמות מניות)

    /**
     * Instantiates a new Invest stock.
     *
     * @param name              the name
     * @param buyingPrice       the buying price
     * @param amount            the amount
     * @param comission         the comission
     * @param pricenow          the pricenow
     * @param TotalWorthOfStock the total worth of stock
     * @param key               the key
     */
    public InvestStock(String name, double buyingPrice, double amount, double comission, double pricenow, double TotalWorthOfStock, String key) {
        //בנאי לעצם
        super(name, pricenow,key);
        this.buyingPrice = buyingPrice;
        this.comission = comission;
        this.amount = amount;
        this.TotalWorthOfStock = TotalWorthOfStock;

    }

    /**
     * Instantiates a new Invest stock.
     */
    public InvestStock() {
    }

    /**
     * Gets buying price.
     *
     * @return the buying price
     */
    public double getBuyingPrice() {
        return buyingPrice;
    }

    /**
     * Sets buying price.
     *
     * @param buyingPrice the buying price
     */
    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    /**
     * Gets comission.
     *
     * @return the comission
     */
    public double getComission() {
        return comission;
    }

    /**
     * Sets comission.
     *
     * @param comission the comission
     */
    public void setComission(double comission) {
        this.comission = comission;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets total worth of stock.
     *
     * @return the total worth of stock
     */
    public double getTotalWorthOfStock() {
        return TotalWorthOfStock;
    }

    /**
     * Sets total worth of stock.
     *
     * @param totalWorthOfStock the total worth of stock
     */
    public void setTotalWorthOfStock(double totalWorthOfStock) { this.TotalWorthOfStock = totalWorthOfStock;

    }
}
