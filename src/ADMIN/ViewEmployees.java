package ADMIN;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import ORDER_SYSTEM.MainOrderSystem;

public class ViewEmployees {

    // Method to read and display the CSV file content
    public void viewEmployees() {
        Scanner scanner = new Scanner(System.in);
        String filePath = "EmployeeList/employees.csv"; // Path to the CSV file

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Display the table header with borders
            MainOrderSystem.clearScreen();
            System.out.println("\t\t\t======================================================================");
            System.out.println("\t\t\t| EmployeeId     | Name                   | Position                 |");
            System.out.println("\t\t\t======================================================================");

            // Read each line and display its content
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(","); // Split by comma

                // Display the employee details with borders
                if (columns.length == 4) {
                    // Assuming the columns are EmployeeId, Name, Password, and Role
                    System.out.printf("\t\t\t| %-14s | %-22s | %-24s | \n", 
                                      columns[0], columns[1], columns[3]);
                } else {
                    // Handle any line with invalid data (less than 4 columns)
                    System.out.printf("\t\t\t| %-14s | %-22s | %-24s | \n", 
                                      "Invalid Data", "Invalid Data", "Invalid Data");
                }
            }

            // Display the table footer with borders
            System.out.println("\t\t\t======================================================================\n\n");

        } catch (IOException e) {
            System.out.println("\t\t\tError while reading employee data: " + e.getMessage());
        }
        
        // Clear the bottom and prompt user to continue
        MainOrderSystem.clearScreenBottom();
        
        
    }
}
