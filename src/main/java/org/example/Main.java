package org.example;

import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        JDBC db = new JDBC();

        while (true) {
            System.out.println(".........................................................");
            System.out.println("1.  LOGIN");
            System.out.println("2.  Initialize database");
            System.out.println("3.  Fetch Data of ArticlesTable from API");
            System.out.println("4.  INSERT INTO JDBC");
            System.out.println("5.  REMOVE TABLES FROM DATABASE");
            System.out.println("6.  FETCH ArticlesTable FROM DB");
            System.out.println("7.  SEARCH FROM DATABASE");
            System.out.println("8.  DISPLAY THE MOST POPULAR ARTICLES");
            System.out.println("0.  Exit program");
            System.out.println("=========================================================");

            int choice;
            try {
                System.out.print("Enter option number: ");
                choice = sc.nextInt();
            } catch (Exception e) {
                System.err.println("Please enter an integer.");
                sc.next(); // discard non-integer input
                continue; // go back to the beginning of the loop
            }

            // continue with switch statement
            switch (choice) {
                case 1 -> db.loginToDatabase();
                case 2 -> db.initializeDatabase();
                case 3 -> APIConsumer.getAPI();
                case 4 -> db.INSERT_INTO_ArticlesTable();
                case 5 -> db.removeArticlesTableFromDatabase();
                case 6 -> db.fetchArticlesTableFromDatabase();
                case 7 -> db.searchFromDatabase();
                case 8 -> APIConsumer.getAPIMostPopularArticles();
                case 0 -> {
                    System.out.println("press <y> to confirm exit");
                    String exitString = sc.next();
                    if (exitString.equalsIgnoreCase("y")) {
                        System.exit(0);
                    }
                }
                default -> System.err.println("Invalid option number");
            }
        }
    }
}