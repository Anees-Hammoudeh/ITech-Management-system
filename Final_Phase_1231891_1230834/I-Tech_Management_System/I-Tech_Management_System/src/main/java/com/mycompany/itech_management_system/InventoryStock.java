package com.mycompany.itech_management_system;

//product stock info
public class InventoryStock {

    int productId;
    String productName;
    String categoryName;
    String brandName;
    int stock; //total quantity from Storage_Details

    public InventoryStock(int productId, String productName, String categoryName, String brandName, int stock) {
        this.productId = productId;
        this.productName = productName;
        this.categoryName = categoryName;
        this.brandName = brandName;
        this.stock = stock;
    }

    public int getProductId() {
        return productId;
    }
    public String getProductName() {
        return productName; 
    }
    public String getCategoryName() {
        return categoryName; 
    }
    public String getBrandName() { 
        return brandName;
    }
    public int getStock() {
        return stock;
    }
}