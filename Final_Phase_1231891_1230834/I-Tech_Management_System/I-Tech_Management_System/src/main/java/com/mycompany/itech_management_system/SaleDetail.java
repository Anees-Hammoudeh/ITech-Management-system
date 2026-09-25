package com.mycompany.itech_management_system;

// holds one row for the sale details table
public class SaleDetail {
    int saleId;
    String product;// product name from Product table
    int quantity;
    double unitPrice;
    double discount;
    double lineTotal;  // (unit_price*quantity)-discount

    public SaleDetail(int saleId, String product, int quantity, double unitPrice, double discount, double lineTotal) {
        this.saleId = saleId;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.lineTotal = lineTotal;
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

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public double getLineTotal() {
        return lineTotal;
    }
}
