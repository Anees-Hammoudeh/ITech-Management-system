package com.mycompany.itech_management_system;

// one row for the pending payments table on the dashboard
public class PendingPayment {
    int saleId;
    double amount;
    String method;
    String date;

    public PendingPayment(int saleId, double amount, String method, String date) {
        this.saleId = saleId;
        this.amount = amount;
        this.method = method;
        this.date = date;
    }

    public int getSaleId() { return saleId; }
    public double getAmount() { return amount; }
    public String getMethod() { return method; }
    public String getDate() { return date; }

    public String getFormattedAmount() {
        return "$" + String.format("%.2f", amount);
    }
}