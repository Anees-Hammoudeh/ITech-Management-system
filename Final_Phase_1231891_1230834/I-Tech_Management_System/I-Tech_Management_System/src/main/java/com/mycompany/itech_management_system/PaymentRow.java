package com.mycompany.itech_management_system;

// holds one payment row for the payments table
public class PaymentRow {
    int paymentId;
    int saleId;
    double amount;
    String method;
    String status; // calculated from total paid and remaining amount
    String date;

    public PaymentRow(int paymentId, int saleId, double amount, String method, String status, String date) {
        this.paymentId = paymentId;
        this.saleId = saleId;
        this.amount = amount;
        this.method = method;
        this.status = status;
        this.date = date;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public int getSaleId() {
        return saleId;
    }

    public double getAmount() {
        return amount;
    }

    public String getMethod() {
        return method;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }
}
