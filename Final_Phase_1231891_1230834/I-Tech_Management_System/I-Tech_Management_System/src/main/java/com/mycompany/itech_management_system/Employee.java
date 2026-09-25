package com.mycompany.itech_management_system;

//employee info
// supervisorName is from the recursive relationship
public class Employee {

    int id;
    String firstName;
    String lastName;
    String position;
    double salary;
    int supervisorId;
    String supervisorName; //comes from joining Employee table with itself

    public Employee(int id, String firstName, String lastName, String position,double salary, int supervisorId, String supervisorName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.salary = salary;
        this.supervisorId = supervisorId;
        this.supervisorName = supervisorName;
    }

    public int getId() {
        return id; 
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() { 
        return lastName; 
    }
    public String getPosition() {
        return position; 
    }
    public double getSalary() { 
        return salary; 
    }
    public int getSupervisorId() {
        return supervisorId;
    }
    public String getSupervisorName() { 
        return supervisorName; 
    }
}