package com.mycompany.itech_management_system;

// supplier phone data from Supplier and Supplier_Phone tables
public class SupplierPhone {

    int supplierId;
    String supplierName; //contact name from Supplier table
    String phone;
    public SupplierPhone(int supplierId, String supplierName, String phone) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.phone = phone;
    }
    public int getSupplierId() {
      return supplierId;
    }
    public String getSupplierName() {
       return supplierName;
    }
    public String getPhone() {
        return phone;
    }
}