package EMPLOYEE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Login {
    private static final String FILE_PATH = "EmployeeList/employees.csv";  // Path to employee data file

    // Method to handle employee login directly, accepting employeeID and password
    public boolean handleLogin(String employeeID, String password) {
        // Verify credentials by calling the verifyCredentials method
        if (verifyCredentials(employeeID, password)) {
            return true;  // Return true if login is successful
        } else {
            System.out.println("\t\t\tInvalid employeeID or password.");
            return false;  // Return false if login fails
        }
    }

    // Method to verify employee credentials from the file
    private boolean verifyCredentials(String employeeID, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                
                // Check if the CSV has the correct number of columns
                if (data.length >= 3) {
                    //System.out.println("\t\t\tChecking ID: " + data[0] + ", Password: " + data[2]); // Debugging output
                    
                    System.out.println("\t\t\tChecking ID...");
                    //timer
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        System.out.println("Timer was interrupted!");
                    }
                    //timer
                    // Compare employeeID and password
                    if (data[0].equals(employeeID) && data[2].equals(password)) {
                    	System.out.println("\t\t\tEmployee login successfully...");
                        return true;  // employeeID and password match
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("\t\t\tError while verifying credentials: " + e.getMessage());
        }
        return false;  // No match found
    }
}
