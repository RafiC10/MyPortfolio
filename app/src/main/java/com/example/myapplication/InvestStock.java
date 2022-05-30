package com.example.myapplication;


public class InvestStock extends LookingStock
{//עצם מסוג מנייה להשקעה אשר יורשת ממנייה לצפייה ומופיסה גם מחיר קנייה,עמלה,כמות מניות,שווי מנייה כולל

    private double buyingPrice;//מחיר קניייה
    private double comission;//עמלה
    private double amount;//כמות מניות שנקנו
        private double TotalWorthOfStock;//שווי כולל של המניות (מחיר נוכחי כפול כמות מניות)

    public InvestStock(String name, double buyingPrice, double amount, double comission, double pricenow, double TotalWorthOfStock, String key) {
        //בנאי לעצם
        super(name, pricenow,key);
        this.buyingPrice = buyingPrice;
        this.comission = comission;
        this.amount = amount;
        this.TotalWorthOfStock = TotalWorthOfStock;

    }

    public InvestStock() {
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }
    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public double getComission() {
        return comission;
    }
    public void setComission(double comission) {
        this.comission = comission;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTotalWorthOfStock() {
        return TotalWorthOfStock;
    }
    public void setTotalWorthOfStock(double totalWorthOfStock) { this.TotalWorthOfStock = totalWorthOfStock;

    }
}
