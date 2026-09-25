package com.mycompany.itech_management_system;

//torage section info
public class StorageCapacity {

    int sectionId;
    String sectionName;
    String sectionType;
    int capacity;
    double usedQuantity;
    double capacityPercent; //used / capacity * 100

    public StorageCapacity(int sectionId, String sectionName, String sectionType,int capacity, double usedQuantity, double capacityPercent) {
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.sectionType = sectionType;
        this.capacity = capacity;
        this.usedQuantity = usedQuantity;
        this.capacityPercent = capacityPercent;
    }

    public int getSectionId() {
        return sectionId; 

  }
    public String getSectionName() { 
        return sectionName;
    }
    public String getSectionType() { 
        return sectionType; 
    }
    public int getCapacity() { 
        return capacity;
    }
    public double getUsedQuantity() { 
        return usedQuantity;
    }
    public double getCapacityPercent() {
        return capacityPercent;
    }
}