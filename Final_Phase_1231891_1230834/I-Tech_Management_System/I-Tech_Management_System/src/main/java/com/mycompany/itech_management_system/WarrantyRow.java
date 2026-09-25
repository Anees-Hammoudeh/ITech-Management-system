package com.mycompany.itech_management_system;

// holds one warranty row linked to a specific sale detail
public class WarrantyRow {
    int saleId;
    String product;
    String warrantyNumber;
    String warrantyType;
    String warrantyPeriod;
    String startDate;

    public WarrantyRow(int saleId, String product, String warrantyNumber, String warrantyType, String warrantyPeriod, String startDate) {
        this.saleId = saleId;
        this.product = product;
        this.warrantyNumber = warrantyNumber;
        this.warrantyType = warrantyType;
        this.warrantyPeriod = warrantyPeriod;
        this.startDate = startDate;
    }

    public int getSaleId() {
        return saleId;
    }

    public String getProduct() {
        return product;
    }

    public String getWarrantyNumber() {
        return warrantyNumber;
    }

    public String getWarrantyType() {
        return warrantyType;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public String getStartDate() {
        return startDate;
    }
}
