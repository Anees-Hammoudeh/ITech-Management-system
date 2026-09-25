package com.mycompany.itech_management_system;

public class PurchasePage {
    private int purchaseId;
    private String supplier;
    private String employee;
    private String purchaseDate;
    private String deliveryDate;
    private String status;
    private double total;

    public PurchasePage(int purchaseId, String supplier, String employee, String purchaseDate, String deliveryDate, String status, double total) {
        this.purchaseId = purchaseId;
        this.supplier = supplier;
        this.employee = employee;
        this.purchaseDate = purchaseDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.total = total;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getEmployee() {
        return employee;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public double getTotal() {
        return total;
    }
}
