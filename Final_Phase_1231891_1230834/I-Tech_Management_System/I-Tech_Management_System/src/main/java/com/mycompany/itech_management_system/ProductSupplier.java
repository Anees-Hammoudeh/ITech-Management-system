package com.mycompany.itech_management_system;

//its to show which supplier provides which product
public class ProductSupplier {

    String productName;
    String supplierName;
    double price;
    //constructor
    public ProductSupplier(String productName, String supplierName, double price){
        this.productName = productName;
        this.supplierName = supplierName;
        this.price = price;
    }
    //getters for the tableview columns to work
    public String getProductName() {
        return productName;
    }
    public String getSupplierName() {
        return supplierName;
    }
    //this price is supply price (what its baid for) not selling price
    public double getPrice() {
        return price;
    }
}