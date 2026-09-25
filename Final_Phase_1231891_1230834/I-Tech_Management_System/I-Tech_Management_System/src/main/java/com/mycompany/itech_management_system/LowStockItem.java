package com.mycompany.itech_management_system;

// holds one row for the low stock table on the dashboard
public class LowStockItem {

    String product;
    String category;
    int quantity;
    String section;

    public LowStockItem(String product, String category, int quantity, String section) {
        this.product = product;
        this.category = category;
        this.quantity = quantity;
        this.section = section;
    }

    public String getProduct() {
        return product;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSection() {
        return section;
    }
}
