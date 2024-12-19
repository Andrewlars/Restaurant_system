package ADMIN;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import ORDER_SYSTEM.MainOrderSystem;

public class RegisterEmployeeMenu {
    public void displayRegisterMenu() {
        Scanner in = new Scanner(System.in);
        ViewEmployees viewEmployee = new ViewEmployees();
        RegisterEmployee register = new RegisterEmployee();
        EmployeeManager employeeManager = new EmployeeManager(50); // Create an instance of EmployeeManager
        AdminMenu ads = new AdminMenu();

        while (true) {
            try {
                MainOrderSystem.clearScreen();
                System.out.println("\t\t\t\t\t\tREGISTER EMPLOYEE");
                System.out.println("\t\t\t===================================================================");
                System.out.println("\t\t\t|                        [1] View Employees                       |");
                System.out.println("\t\t\t|                        [2] Register Employee                    |");
                System.out.println("\t\t\t|                        [3] Edit Employee                        |");
                System.out.println("\t\t\t|                        [4] Exit                                 |");
                System.out.println("\t\t\t===================================================================\n\n");
                clearScreenBottom();
                System.out.print("\t\t\tEnter: ");

                // Check if input is an integer
                if (in.hasNextInt()) {
                    int choice = in.nextInt();
                    in.nextLine(); // Consume newline character

                    switch (choice) {
                        case 1:
                            viewEmployee.viewEmployees();
                            System.out.print("\t\t\tPress ENTER to continue...");
                            in.nextLine();
                            break;
                        case 2:
                            register.RegisterEmployee();
                            break;
                        case 3:
                        	viewEmployee.viewEmployees();
                            editEmployee(employeeManager, in); // Call method to edit employee
                            break;
                        case 4:
                            System.out.println("\t\t\tExiting...");
                            return; // Exit the loop and return
                        default:
                            System.out.println("\t\t\tInvalid Choice. Please enter a number between 1 and 4.");
                    }
                } else {
                    // Handle non-integer input
                    in.nextLine(); // Consume invalid input
                    System.out.println("\t\t\tInvalid input. Please enter a valid number.");
                }
            } catch (Exception e) {
                System.err.println("\t\t\tAn error occurred: " + e.getMessage());
                in.nextLine(); // Clear the scanner buffer in case of an error
            }
        }
    }

    // Method to handle editing or deleting employee credentials
    private void editEmployee(EmployeeManager employeeManager, Scanner in) {
        System.out.print("\t\t\tEnter the Employee ID to edit: ");
        String employeeId = in.nextLine();

        // Check if employee exists
        EmployeeVar employee = employeeManager.findEmployeeById(employeeId);
        if (employee == null) {
            System.out.println("\t\t\tEmployee not found with ID: " + employeeId);
            return;
        }

        System.out.println("\t\t\tEmployee Found: " + employee.getName());
        System.out.println("\t\t\tWhat would you like to do?");
        System.out.println("\t\t\t1. Edit Name");
        System.out.println("\t\t\t2. Edit Password");
        System.out.println("\t\t\t3. Edit Position");
        System.out.println("\t\t\t4. Delete Employee");
        System.out.print("\t\t\tEnter your choice: ");
        
        // Read choice as a string for easier invalid input handling
        String choice = in.nextLine().trim().toLowerCase();

        // Ask for the security key before proceeding with the edit or delete
        if (askSecurityQuestion(in)) {
            boolean isValidChoice = true;  // Track if the choice is valid

            switch (choice) {
                case "1":
                    // Edit Name
                    System.out.print("\t\t\tEnter new Name: ");
                    String newName = in.nextLine();

                    // Check if the new Name contains any numbers
                    if (newName.matches(".*\\d.*")) {
                        System.out.println("\t\t\tNames cannot contain numbers. Please enter a valid Name.");
                        isValidChoice = false;
                    } else {
                        employee.setName(newName);
                    }
                    break;

                case "2":
                    // Edit Password
                    System.out.print("\t\t\tEnter new Password: ");
                    String newPassword = in.nextLine();

                    System.out.print("\t\t\tConfirm new Password: ");
                    String confirmPassword = in.nextLine();

                    if (!newPassword.equals(confirmPassword)) {
                        System.out.println("\t\t\tPasswords do not match.");
                        isValidChoice = false;
                    } else {
                        employee.setPassword(newPassword);
                    }
                    break;

                case "3":
                    // Edit Position
                    System.out.println("\t\t\tSelect Position:");
                    System.out.println("\t\t\t1. Manager");
                    System.out.println("\t\t\t2. Counter");
                    System.out.println("\t\t\t3. Kitchen");
                    System.out.println("\t\t\t4. Service");
                    System.out.println("\t\t\t5. Housekeeping");
                    System.out.print("\t\t\tEnter the Position number: ");
                    int positionChoice = in.nextInt();
                    in.nextLine();  // Consume the newline character

                    String newPosition = "";
                    switch (positionChoice) {
                        case 1:
                            newPosition = "Manager";
                            break;
                        case 2:
                            newPosition = "Counter";
                            break;
                        case 3:
                            newPosition = "Kitchen";
                            break;
                        case 4:
                            newPosition = "Service";
                            break;
                        case 5:
                            newPosition = "Housekeeping";
                            break;
                        default:
                            System.out.println("\t\t\tInvalid Position. Keeping the current position.");
                            isValidChoice = false;  // Invalid position
                    }
                    if (isValidChoice) {
                        employee.setPosition(newPosition);
                    }
                    break;

                case "4":
                    // Delete Employee
                    System.out.println("\t\t\tAre you sure you want to delete this employee? This action is permanent. Press any key to cancel.");
                    System.out.print("\t\t\tEnter '1' to confirm: ");
                    String confirmDelete = in.nextLine().trim().toLowerCase();
                    if ("1".equals(confirmDelete)) {
                        employeeManager.deleteEmployee(employeeId);
                        System.out.println("\t\t\tEmployee deleted successfully.");
                        return;  // Exit after deletion
                    } else {
                        System.out.println("\t\t\tDelete operation canceled.");
                        return;  // Exit after canceling deletion
                    }

                default:
                    System.out.println("\t\t\tInvalid choice. Please enter a valid option.");
                    isValidChoice = false;
            }

            if (!isValidChoice) {
                // Ask user if they want to retry or exit
                System.out.println("\t\t\tInvalid input or action. Continue editing? or exit?");
                System.out.println("\t\t\t1. Continue");
                System.out.println("\t\t\t2. Exit");
                System.out.print("\t\t\tEnter your choice: ");
                String retryChoice = in.nextLine().trim().toLowerCase();
                if ("1".equals(retryChoice)) {
                    // Restart the edit process
                    editEmployee(employeeManager, in);
                } else {
                    System.out.println("\t\t\tExiting...");
                    return;  // Exit the method if the user selects 'Exit'
                }
            } else {
                // Save changes to the employee data
                boolean success = employeeManager.saveEmployeesToFile();
                if (success) {
                    System.out.println("\t\t\tEmployee updated successfully.");
                } else {
                    System.out.println("\t\t\tFailed to update employee.");
                }
            }
        } else {
            System.out.println("\t\t\tSecurity check failed. No changes made.");
        }
    }



    // Method to ask for the security key before editing or deleting
    private boolean askSecurityQuestion(Scanner in) {
        RegisterEmployee registerEmployee = new RegisterEmployee();
        String expectedKey = registerEmployee.readSecurityKeyFromFile();  // Use RegisterEmployee's method to read the key
        System.out.print("\t\t\tPlease enter the security key: ");
        String inputKey = in.nextLine().trim();

        return expectedKey.equals(inputKey);  // Check if the input key matches the expected key
    }

    public static void clearScreen() {
        for (int i = 0; i < 5; i++) {  // Print 50 newlines
            System.out.println();
        }
    }

    public static void clearScreenBottom() {
        for (int i = 0; i < 4; i++) {  // Print 40 newlines
            System.out.println();
        }
    }
}
