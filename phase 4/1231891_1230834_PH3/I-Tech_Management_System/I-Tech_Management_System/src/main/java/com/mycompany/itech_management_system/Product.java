package com.mycompany.itech_management_system;

public class Product {

    private int id;
    private String name;
    private String model;
    private double price;

    public Product(int id, String name, String model, double price) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.price = price;
    }

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
}