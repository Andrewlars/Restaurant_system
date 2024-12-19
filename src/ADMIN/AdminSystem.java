package ADMIN;

import ORDER_SYSTEM.MainOrderSystem;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AdminSystem {
    private static final String ADMIN_RECORD_FILE = "AdminRecord/AdminRecord.csv";
    private static final String ADMIN_KEY_FILE = "AdminRecord/adminkey.csv";

    private String adminUsername;
    private String adminPassword;
    private String securityAnswer;
    private String hardcodedKey;
    private AdminMenu adminMenu = new AdminMenu(); // Initialize here instead of in the constructor

    public AdminSystem() {
        loadAdminCredentials();
        loadHardcodedKey();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in); // Create a new Scanner instance for user input
        int choice;
        boolean running = true;

        while (running) {
//            MainOrderSystem.clearScreen();
            System.out.println("\t\t\t\t\t\tADMIN LOGIN");
            System.out.println("\t\t\t===================================================================");
            System.out.println("\t\t\t|                        [1] Login                                |");
            System.out.println("\t\t\t|                        [2] Forgot Password                      |");
            System.out.println("\t\t\t|                        [3] Exit                                 |");
            System.out.println("\t\t\t===================================================================\n\n");
            clearScreenBottom();
            System.out.print("\t\t\tEnter: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear the newline left by nextInt()
            } else {
                scanner.nextLine(); // Clear invalid input
                clearScreen();
                System.out.println("\t\t\tInvalid choice. Please try again.\n");
                continue;
            }

            switch (choice) {
                case 1:
                    if (login(scanner)) { // Pass the scanner to the login method
                        adminMenu.displayMenu(); // Show the admin menu after successful login
                        break;
                    } else {
                        if (forgotPassword(scanner)) {
                            break;
                        } else {
                            return;
                        }
                    }
                case 2:
                    if (forgotPassword(scanner)) {
                        break;
                    } else {
                        return;
                    }
                case 3:
                	//scanner.nextLine();
                	//System.out.println("Press Enter to continue...");
		            MainOrderSystem.getRoleChoice(scanner);
                    return ;
                default:
                	clearScreen();
                    System.out.println("\t\t\tInvalid choice. Please try again.\n");
            }
        }
    }

    private void loadAdminCredentials() {
        try (BufferedReader br = new BufferedReader(new FileReader(ADMIN_RECORD_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                adminUsername = parts[0];
                adminPassword = parts[1];
                securityAnswer = parts[2];
            }
        } catch (IOException e) {
            System.err.println("\t\t\tError reading admin credentials: " + e.getMessage());
            System.exit(1);
        }
    }

    private void loadHardcodedKey() {
        try (BufferedReader br = new BufferedReader(new FileReader(ADMIN_KEY_FILE))) {
            hardcodedKey = br.readLine();
        } catch (IOException e) {
            System.err.println("\t\t\tError reading admin key: " + e.getMessage());
            System.exit(1);
        }
    }

    public boolean login(Scanner scanner) {
        int attemptsLeft = 3;
        while (attemptsLeft > 0) {
            System.out.print("\t\t\tEnter username: ");
            String loginUsername = scanner.nextLine();
            System.out.print("\t\t\tEnter password: ");
            String loginPassword = scanner.nextLine();

            if (loginUsername.equals(adminUsername) && loginPassword.equals(adminPassword)) {
                return true;
            } else {
                attemptsLeft--;
                clearScreenSmall();
                System.out.println("\t\t\tInvalid username or password. Attempts remaining: " + attemptsLeft);
            }
        }
        clearScreenSmall();
        System.out.println("\t\t\tToo many failed attempts.");
        return false;
    }

    public boolean forgotPassword(Scanner scanner) {
        System.out.print("\t\t\tSecurity Question - What is the CEO's favorite food? ");
        String answer = scanner.nextLine();
        System.out.print("\t\t\tEnter the security key: ");
        String key = scanner.nextLine();

        if (answer.equalsIgnoreCase(securityAnswer) && key.equals(hardcodedKey)) {
            System.out.print("\t\t\tEnter new password: ");
            String newPassword = scanner.nextLine();

            adminPassword = newPassword; // Update the password in memory

            // Write the updated password back to the CSV file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ADMIN_RECORD_FILE))) {
                writer.write(adminUsername + "," + adminPassword + "," + securityAnswer);
                System.out.println("\t\t\tPassword updated successfully.");
            } catch (IOException e) {
                System.err.println("\t\t\tError updating password: " + e.getMessage());
                return false; // Return false if saving fails
            }

            return true; // Return true if password reset is successful
        } else {
        	clearScreen();
            System.out.println("\t\t\tIncorrect security answers.\n\n");
            start();
            return false; // Return false if password reset fails
        }
    }
    
    public static void clearScreen() {
        for (int i = 0; i < 5; i++) {  // Print 50 newlines
            System.out.println();
        }   
    }
    
    public static void clearScreenBottom() {
        for (int i = 0; i < 4; i++) {  // Print 50 newlines
            System.out.println();
        }   
    }
    
    public static void clearScreenSmall() {
        for (int i = 0; i < 5; i++) {  // Print 50 newlines
            System.out.println();
        }   
    }
}
