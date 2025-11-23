package com.Util;

import java.sql.Connection;

public class DBTest {
    public static void main(String[] args) {
        try {
            Connection conn = JdbcConnector.connect();
            if (conn != null) {
                System.out.println("Connected to the database successfully!");
            } else {
                System.out.println("Connection returned null");
            }
        } catch (Exception e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }
}

