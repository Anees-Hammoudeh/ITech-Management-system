package com.mycompany.itech_management_system;
//holds  customer info from the Customer table
public class Customer {

   int id;
    String firstName;
   String lastName;
   public Customer(int id, String firstName, String lastName){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public int getId(){
        return id;
    }
    public String getFirstName(){
        return firstName;
    }
   public String getLastName() {
        return lastName;
    }
}