package ADMIN;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter; // Import this
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class EmployeeManager {
    private EmployeeVar[] employees;
    private int count;
    private static final String FILE_PATH = "EmployeeList/employees.csv";  // Relative path to the CSV file
    RegisterEmployeeMenu rgs = new RegisterEmployeeMenu();

    public EmployeeManager(int size) {
        employees = new EmployeeVar[size];
        count = 0;
        loadEmployeesFromFile();  // Load employees from the file when the object is created
    }

    // Load employees from the CSV file
    private void loadEmployeesFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("\t\t\tNo employee data file found, starting fresh.");
            return;  // Skip loading if the file does not exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    employees[count++] = new EmployeeVar(data[0], data[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("\t\t\tError while loading employee data: " + e.getMessage());
        }
    }

    // Register a new employee
    public boolean register(String username, String password, String confirmPassword) {
        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            System.out.println("\t\t\tPasswords do not match.");
            rgs.displayRegisterMenu();
            return false;
        }

        if (count < employees.length) {
            employees[count++] = new EmployeeVar(username, password);
            saveEmployeesToFile();  // Save employees to file after adding a new one
            return true;
        }

        System.out.println("\t\t\tEmployee limit reached.");
        return false;
    }

    // Save the employees to the CSV file
    private void saveEmployeesToFile() {
        File folder = new File("EmployeeList");
        if (!folder.exists()) {
            folder.mkdirs();  // Create the folder if it doesn't exist
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            // Write the header line

            for (int i = 0; i < count; i++) {
                writer.write(employees[i].getUsername() + "," + employees[i].getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("\t\t\tError while saving employee data: " + e.getMessage());
        }
        
    }

}
