package com.mycompany.itech_management_system;

// holds row for the sales table
public class SalePage {
    int saleId;
    String customer;   // first & last name of customer
    String employee;   // first + last name of employee
    String date;
    double total;      // sum of all line totals for this sale

    public SalePage(int saleId, String customer, String employee, String date, double total) {
        this.saleId = saleId;
        this.customer = customer;
        this.employee = employee;
        this.date = date;
        this.total = total;
    }

    public int getSaleId() {
        return saleId;
    }

    public String getCustomer() {
        return customer;
    }

    public String getEmployee() {
        return employee;
    }

    public String getDate() {
        return date;
    }

    public double getTotal() {
        return total;
    }
}
