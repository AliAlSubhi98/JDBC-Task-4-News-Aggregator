package org.example;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
public class JDBC {
    static String databaseName;
    static String userName;
    static String password;
    static boolean Login = false;

    public boolean loginToDatabase() {
        Scanner sc = new Scanner(System.in);
        System.out.println("==================LOGIN TO THE DATABASE==================");
        System.out.print("Enter database name: ");
        JDBC.databaseName = sc.next();
        System.out.print("Enter user name: (sa) ");
        JDBC.userName = sc.next();
        System.out.print("Enter password: (root)");
        JDBC.password = sc.next();
        System.out.println("=========================================================");

        // Test the connection
        String url = "jdbc:sqlserver://" + "localhost:1433;" + "encrypt=true;" + "trustServerCertificate=true";
        url += ";databaseName=" + databaseName;

        try {
            // Load the SQL Server JDBC driver
            Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            DriverManager.registerDriver(driver);

            // Connect to the SQL Server instance
            Connection con = DriverManager.getConnection(url, userName, password);

            // If the connection is successful, set login to true and close the connection
            JDBC.Login = true;
            con.close();
            System.out.println("Successfully logged in to the database.");
        } catch (Exception ex) {
            System.err.println("Error logging in to the database: " + ex);
            JDBC.Login = false;
        }

        return JDBC.Login;
    }


}