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

    public void initializeDatabase() {
        System.out.println("Initialize Database");
        Scanner scanner = new Scanner(System.in);

        String url = "jdbc:sqlserver://" + "localhost:1433;" +
                "encrypt=true;" +
                "trustServerCertificate=true";
        Connection con = null;

        try {
            Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            DriverManager.registerDriver(driver);

            con = DriverManager.getConnection(url, userName, password);
            Statement st = con.createStatement();

            // Check if the database exists
            String sql1 = "SELECT * FROM sys.databases WHERE name='" + databaseName + "'";
            ResultSet rs = st.executeQuery(sql1);

            if (rs.next()) {
                // Update url with the existing database name
                url += ";databaseName=" + databaseName;
                con = DriverManager.getConnection(url, userName, password);
                Statement st2 = con.createStatement();

                // Check if the universities table exists
                String sql2 = "SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'ArticlesTable'";
                rs = st2.executeQuery(sql2);

                if (!rs.next()) {
                    // Create table if it doesn't exist
                    String sql3 = """
                            CREATE TABLE ArticlesTable (
                                ArticleID INT PRIMARY KEY IDENTITY,
                                Title VARCHAR(250) ,
                                Author VARCHAR(250) ,
                                PublicationDate VARCHAR(250) ,
                                Category VARCHAR(250) ,
                                Content VARCHAR(max)
                            );""";
                    st2.executeUpdate(sql3);
                    System.out.println("ArticlesTable Table created successfully!");
                } else {
                    System.out.println("ArticlesTable Table already exists!");
                }
            } else {
                System.out.println("Database does not exist.");
            }

            con.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public void INSERT_INTO_ArticlesTable() {

        System.out.println("TRYING TO INSERT INTO ArticlesTable");

        String url = "jdbc:sqlserver://" + "localhost:1433;" + "encrypt=true;" + "trustServerCertificate=true";
        Connection con = null;

        try {
            Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            DriverManager.registerDriver(driver);

            con = DriverManager.getConnection(url, userName, password);
            Statement st = con.createStatement();

            // Update url with the database name
            url += ";databaseName=" + databaseName;
            con = DriverManager.getConnection(url, userName, password);

            String sql3 = "INSERT INTO ArticlesTable(Title, Author, PublicationDate, Category, Content) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql3);


            for (Article article : APIConsumer.articleArray) {
                ps.setString(1, article.headline.main);
                ps.setString(2, article.byline.original);
                ps.setString(3, article.pub_date);
                ps.setString(4, article.section_name);
                ps.setString(5, article.lead_paragraph);
                ps.executeUpdate();
            }
            System.out.println("Data inserted into ArticlesTable table!");
            con.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public void removeArticlesTableFromDatabase() {

        System.out.println("TRYING TO REMOVE ArticlesTable TABLE ");

        String url = "jdbc:sqlserver://" + "localhost:1433;" + "encrypt=true;" + "trustServerCertificate=true";
        Connection con = null;

        try {
            Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            DriverManager.registerDriver(driver);

            // Update url with the database name
            url += ";databaseName=" + databaseName;
            con = DriverManager.getConnection(url, userName, password);
            Statement st2 = con.createStatement();

            // Create table if it doesn't exist
            String sql2 = "drop table ArticlesTable;";
            st2.executeUpdate(sql2);

            System.out.println("ArticlesTable TABLE REMOVED SUCCESSFULLY");
            con.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public void fetchArticlesTableFromDatabase() {

        System.out.println("TRYING TO FETCH ArticlesTable TABLE ");
        System.out.println();

        String url = "jdbc:sqlserver://" + "localhost:1433;" + "encrypt=true;" + "trustServerCertificate=true";
        Connection con = null;

        try {
            Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            DriverManager.registerDriver(driver);

            // Update url with the database name
            url += ";databaseName=" + databaseName;
            con = DriverManager.getConnection(url, userName, password);
            Statement st = con.createStatement();

            String sql = """
                    SELECT * FROM ArticlesTable
                    ORDER BY PublicationDate ASC;""";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int ArticleID = rs.getInt("ArticleID");
                String Title = rs.getString("Title");
                String Author = rs.getString("Author");
                String PublicationDate = rs.getString("PublicationDate");
                String Category = rs.getString("Category");
                String Content = rs.getString("Content");

                System.out.println("ArticleID " + ArticleID);
                System.out.println("Title " + Title);
                System.out.println("Author " + Author);
                System.out.println("PublicationDate " + PublicationDate);
                System.out.println("Category " + Category);
                System.out.println("Content " + Content);
                System.out.println();

            }
            System.out.println();
            System.out.println("ArticlesTable TABLE FETCHED SUCCESSFULLY");
            con.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public void searchFromDatabase() {
        System.out.println("SEARCH FOR UNIVERSITIES IN DATABASE");

        String url = "jdbc:sqlserver://" + "localhost:1433;" + "encrypt=true;" + "trustServerCertificate=true";
        Connection con = null;

        try {
            Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            DriverManager.registerDriver(driver);

            // Update url with the database name
            url += ";databaseName=" + databaseName;
            con = DriverManager.getConnection(url, userName, password);
            Statement st = con.createStatement();

            Scanner scanner = new Scanner(System.in);
            System.out.println("Search by Title (t), Author (a), or Category (c)?");
            String searchType = scanner.next().toLowerCase();
            System.out.println("Enter search term:");
            String searchTerm = scanner.next();

            String sql = "";
            switch (searchType) {
                case "t":
                    sql = "SELECT DISTINCT Title, Author, PublicationDate, Category, Content FROM ArticlesTable WHERE Title LIKE '%"
                            + searchTerm + "%';";
                    break;
                case "a":
                    sql = "SELECT DISTINCT Title, Author, PublicationDate, Category, Content FROM ArticlesTable WHERE Author LIKE '%"
                            + searchTerm + "%';";
                    break;
                case "c":
                    sql = "SELECT DISTINCT Title, Author, PublicationDate, Category, Content FROM ArticlesTable WHERE Category LIKE '%"
                            + searchTerm + "%';";
                    break;
                default:
                    System.err.println("Invalid search type.");
                    break;
            }

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String Title = rs.getString("Title");
                String Author = rs.getString("Author");
                String PublicationDate = rs.getString("PublicationDate");
                String Category = rs.getString("Category");
                String Content = rs.getString("Content");

                System.out.println("Title " + Title);
                System.out.println("Author " + Author);
                System.out.println("PublicationDate " + PublicationDate);
                System.out.println("Category " + Category);
                System.out.println("Content " + Content);
                System.out.println();
            }

            con.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}