package com.mycompany.itech_management_system;
// holds customer sales data from multiple tables
public class CustomerSales {
    int customerId;
   String customerName;
   int saleId;
   String productName;
    int quantity;
   double lineTotal;  //unit price * quantity - discount
    double paidTotal;  //total paid from Payment table
    public CustomerSales(int customerId, String customerName, int saleId,
            String productName, int quantity, double lineTotal, double paidTotal) {
        this.customerId = customerId;
         this.customerName = customerName;
          this.saleId = saleId;
         this.productName = productName;
         this.quantity = quantity;
         this.lineTotal = lineTotal;
         this.paidTotal = paidTotal;
    }
    public int getCustomerId() {
        return customerId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public int getSaleId() {
        return saleId;
    }
    public String getProductName() {
        return productName;
    }
    public int getQuantity() {
        return quantity;
    }

    public double getLineTotal() {
        return lineTotal;
    }
    public double getPaidTotal() {
        return paidTotal;
    }
}