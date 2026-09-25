package com.mycompany.itech_management_system;

//employee sales detailsfrom Employee, Sale, Customer tables
public class EmployeeSales {

    int employeeId;
    String employeeName;
    int saleId;
    String customerName;
    int productsSold;   //count of products in this sale from subquery
    double saleTotal;   //total paid from Payment table subquery

    public EmployeeSales(int employeeId, String employeeName, int saleId,
            String customerName, int productsSold, double saleTotal) {
        this.employeeId = employeeId;
         this.employeeName = employeeName;
        this.saleId = saleId;
         this.customerName = customerName;
         this.productsSold = productsSold;
        this.saleTotal = saleTotal;
    }

    public int getEmployeeId() {
        return employeeId; 
    }
    public String getEmployeeName() { 
        return employeeName;
    }
    public int getSaleId() {
        return saleId;
    }
    public String getCustomerName() {
        return customerName; 
    }
    public int getProductsSold() {
        return productsSold; 
    }
    public double getSaleTotal() { 
        return saleTotal; 
    }
}