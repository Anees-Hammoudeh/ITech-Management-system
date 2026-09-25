package com.mycompany.itech_management_system;

//holds data for each product row
public class Product {
    private int id;
    private String name;
    private String model;
    private double price;
    private String categoryName;
    private String brandName;
    
    public Product(int id, String name, String model, double price, String categoryName, String brandName) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.price = price;
        this.categoryName = categoryName;
        this.brandName = brandName;
    }

    //getters
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getModel() {
        return model;
    }
    public double getPrice() {
        return price;
    }
    //these came from the join with category and brand tables
    public String getCategoryName() {
        return categoryName;
    }

    public String getBrandName() {
        return brandName;
    }

}