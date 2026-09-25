package com.mycompany.itech_management_system;

//stock movement history : product moved from one section to another
public class StockMovement {

    int movementId;
    String productName;
    String movementType;
    int quantity;
    String sourceSection;      //can be "-" if no source(like a new purchase coming in)
    String destinationSection; //can be "-" if no destination(like a sale going out)
    String movementDate;

    public StockMovement(int movementId, String productName, String movementType, int quantity,String sourceSection, String destinationSection, String movementDate) {
        this.movementId = movementId;
        this.productName = productName;
        this.movementType = movementType;
        this.quantity = quantity;
        this.sourceSection = sourceSection;
        this.destinationSection = destinationSection;
        this.movementDate = movementDate;
    }

    public int getMovementId() { 
        return movementId; 
    }
    public String getProductName() {
        return productName; 
    }
    public String getMovementType() {
        return movementType;
    }
    public int getQuantity() { 
        return quantity;
    }
    public String getSourceSection() {
        return sourceSection; 
    }
    public String getDestinationSection() { 
        return destinationSection;
    }
    public String getMovementDate() {
        return movementDate;
    }
}