package com.mycompany.itech_management_system;

// one row for the top products table on the dashboard
public class TopProduct {
    String product;
    int soldQty;
    double revenue;

    public TopProduct(String product, int soldQty, double revenue) {
        this.product = product;
        this.soldQty = soldQty;
        this.revenue = revenue;
    }

    public String getProduct() { return product; }
    public int getSoldQty() { return soldQty; }
    public double getRevenue() { return revenue; }

    // formatted version shown in the table column
    public String getFormattedRevenue() {
        return "$" + String.format("%.2f", revenue);
    }
}