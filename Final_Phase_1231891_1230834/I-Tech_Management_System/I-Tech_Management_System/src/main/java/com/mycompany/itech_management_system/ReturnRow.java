package com.mycompany.itech_management_system;

// one row for the return table
public class ReturnRow {

    int returnId;
    int saleId;
    String product;
    int quantity;
    String reason;
    String status;
    String date;

    public ReturnRow(int returnId, int saleId, String product, int quantity, String reason, String status, String date) {
        this.returnId = returnId;
        this.saleId = saleId;
        this.product = product;
        this.quantity = quantity;
        this.reason = reason;
        this.status = status;
        this.date = date;
    }

    public int getReturnId() {
        return returnId;
    }

    public int getSaleId() {
        return saleId;
    }

    public String getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }
}
