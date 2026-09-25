package com.mycompany.itech_management_system;
// holds customer phone data from Customer and Customer_Phone tables
public class CustomerPhone {
   int customerId;
    String customerName; // first + last name combined
  String phone;

   public CustomerPhone(int customerId, String customerName, String phone) {
        this.customerId = customerId;
        this.customerName = customerName;
          this.phone = phone;
    }
     public int getCustomerId() {
        return customerId;
    }
    public String getCustomerName() {
        return customerName;
    }
   public String getPhone() {
        return phone;
    }
}