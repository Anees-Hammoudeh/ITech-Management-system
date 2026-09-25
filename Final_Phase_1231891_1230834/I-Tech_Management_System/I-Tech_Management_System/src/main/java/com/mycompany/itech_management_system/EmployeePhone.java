package com.mycompany.itech_management_system;

//employee phone datafrom Employee and Employee_Phone tables
public class EmployeePhone {

    int employeeId;
    String employeeName; //first + last name 
    String phone;

    public EmployeePhone(int employeeId, String employeeName, String phone) {
        this.employeeId = employeeId;
         this.employeeName = employeeName;
        this.phone = phone;
    }

    public int getEmployeeId() { 
        return employeeId; 
    }
    public String getEmployeeName() {
        return employeeName; 
    }
    public String getPhone() { 
        return phone; 
    }
}