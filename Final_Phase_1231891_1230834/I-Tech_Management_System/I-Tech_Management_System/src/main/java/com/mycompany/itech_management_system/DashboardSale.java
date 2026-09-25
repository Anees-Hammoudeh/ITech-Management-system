package com.mycompany.itech_management_system;

// one row for the recent sales table on the dashboard
public class DashboardSale {
    int id;
    String customer;
    String employee;
    String date;
    double total;

    public DashboardSale(int id, String customer, String employee, String date, double total) {
        this.id = id;
        this.customer = customer;
        this.employee = employee;
        this.date = date;
        this.total = total;
    }

    public int getId() { return id; }
    public String getCustomer() { return customer; }
    public String getEmployee() { return employee; }
    public String getDate() { return date; }
    public double getTotal() { return total; }

    // formatted version shown in the table column
    public String getFormattedTotal() {
        return "$" + String.format("%.2f", total);
    }
}