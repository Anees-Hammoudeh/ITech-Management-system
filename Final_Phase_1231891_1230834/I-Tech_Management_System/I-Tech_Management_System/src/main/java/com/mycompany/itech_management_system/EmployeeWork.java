package com.mycompany.itech_management_system;

//employee work summary from Employee, Sale, Purchase tables
public class EmployeeWork {

    int employeeId;
    String employeeName;
    String position;
    String managerName; //from recursive join on Employee table
    int salesCount;     //number of sales this employee recorded
    int purchasesCount; //number of purchases this employee recorded
    int totalRecords;   //salesCount + purchasesCount

    public EmployeeWork(int employeeId, String employeeName, String position,String managerName, int salesCount, int purchasesCount, int totalRecords) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.position = position;
        this.managerName = managerName;
        this.salesCount = salesCount;
        this.purchasesCount = purchasesCount;
        this.totalRecords = totalRecords;
    }

    public int getEmployeeId() {
        return employeeId; 
    }
    public String getEmployeeName() {
        return employeeName; 
    }
    public String getPosition() { 
        return position; 
    }
    public String getManagerName() { 
        return managerName;
    }
    public int getSalesCount() {
        return salesCount; 
    }
    public int getPurchasesCount() { 
        return purchasesCount; 
    }
    public int getTotalRecords() { 
        return totalRecords;
    }
}