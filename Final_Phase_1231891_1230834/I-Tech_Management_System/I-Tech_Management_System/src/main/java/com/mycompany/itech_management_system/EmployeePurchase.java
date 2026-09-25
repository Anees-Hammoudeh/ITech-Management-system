package com.mycompany.itech_management_system;

//employee purchase details from Employee, Purchase, Supplier tables
public class EmployeePurchase {

    int employeeId;
    String employeeName;
    int purchaseId;
    String supplierName;
    int productsPurchased;  //count of products in this purchase from subquery
    double purchaseTotal;   //total value from Purchase_Details subquery

    public EmployeePurchase(int employeeId, String employeeName, int purchaseId,String supplierName, int productsPurchased, double purchaseTotal) {
        this.employeeId = employeeId;
         this.employeeName = employeeName;
         this.purchaseId = purchaseId;
        this.supplierName = supplierName;
         this.productsPurchased = productsPurchased;
        this.purchaseTotal = purchaseTotal;
    }

    public int getEmployeeId() {
        return employeeId;
    }
    public String getEmployeeName() { 
        return employeeName;
    }
    public int getPurchaseId() { 
        return purchaseId;
    }
    public String getSupplierName() {
        return supplierName; }
    public int getProductsPurchased() {
        return productsPurchased;
    }
    public double getPurchaseTotal() {
        return purchaseTotal; 
    }
}