package com.mycompany.itech_management_system;

public class PurchaseDetail {
    private int purchaseId;
    private int productId;
    private String productName;
    private String sectionName;
    private int quantity;
    private double unitPrice;
    private double lineTotal;

    public PurchaseDetail(int purchaseId, int productId, String productName, String sectionName, int quantity, double unitPrice, double lineTotal) {
        this.purchaseId = purchaseId;
        this.productId = productId;
        this.productName = productName;
        this.sectionName = sectionName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.lineTotal = lineTotal;
    }

    public int getPurchaseId() { return purchaseId; }
    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getSectionName() { return sectionName; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
    public double getLineTotal() { return lineTotal; }
}
