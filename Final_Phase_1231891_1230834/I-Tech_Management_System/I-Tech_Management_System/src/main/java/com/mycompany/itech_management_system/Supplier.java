package com.mycompany.itech_management_system;

// supplier info from the Supplier table
public class Supplier {

    int id;
    String contactName;
    String companyName;
    String address;
    public Supplier(int id, String contactName, String companyName, String address) {
        this.id = id;
        this.contactName = contactName;
        this.companyName = companyName;
        this.address = address;
    }
    public int getId() {
        return id;
    }
    public String getContactName() {
        return contactName;
    }
    public String getCompanyName() {
        return companyName;
    }
    public String getAddress() {
        return address;
    }
}