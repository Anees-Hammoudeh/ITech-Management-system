package com.mycompany.itech_management_system;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnection {

    private static String username = "root";
    private static String password = "12345555";
    private static String url = "jdbc:mysql://localhost:3306/itech_db";
    public static Connection getConnection() {

        Connection mycon = null;

        try {

            mycon = DriverManager.getConnection(url,username,password);
            System.out.println("Connection Open");
        } catch (Exception e) {
            System.out.println("Connection Error");
        }

        return mycon;
    }
}