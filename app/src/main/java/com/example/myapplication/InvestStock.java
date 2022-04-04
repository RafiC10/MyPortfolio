package com.example.myapplication;

//לעשות הורשה על ידי הוספת מנניה שהיא רק לצפייה ובניית 2 תיקים אחד לרשימות צפייה ואחד לקניית מניות ממש
public class InvestStock extends LookingStock{

    private double buyingPrice;
    private double comission;
    private double amount;
    private double TotalWorthOfStock;

    public InvestStock(String name, double buyingPrice, double amount, double comission, double pricenow, double TotalWorthOfStock, String key) {
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
