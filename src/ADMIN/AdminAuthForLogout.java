package ADMIN;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class AdminAuthForLogout {
    private static final String ADMIN_KEY_FILE = "AdminRecord/adminkey.csv";  // Path to the file that stores the admin key

    // Login method for authentication using the admin key from the file
    public boolean login(Scanner scanner) {
        int attemptsLeft = 3; // Maximum attempts allowed
        String hardcodedKey = loadAdminKeyFromFile();  // Load the key from the file

        System.out.println("\n\t\t\t--- Security Key Authentication ---");
        while (attemptsLeft > 0) {
            System.out.print("\t\t\tEnter security key: ");
            String inputKey = scanner.nextLine();

            if (inputKey.equals(hardcodedKey)) {
        
                System.out.println("\n\t\t\tAuthentication successful!");
                return true; // Login successful
            } else {
                attemptsLeft--;
                System.out.println("\n\t\t\tInvalid key. Attempts remaining: " + attemptsLeft);
            }
        }

        // If all attempts are exhausted
        System.out.println("\t\t\tToo many failed attempts. Access denied.");
        return false;
    }

    // Load the admin key from the file
    private String loadAdminKeyFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(ADMIN_KEY_FILE))) {
            return br.readLine().trim();  // Read the first line of the file (which is the key)
        } catch (IOException e) {
            System.err.println("\t\t\tError reading admin key from file: " + e.getMessage());
            return null;
        }
    }

    // Utility method to clear the screen
    public static void clearScreen() {
        for (int i = 0; i < 5; i++) {  // Print 50 newlines
            System.out.println();
        }
    }

    // Utility method to clear the bottom of the screen
    public static void clearScreenBottom() {
        for (int i = 0; i < 3; i++) {  // Print 30 newlines
            System.out.println();
        }
    }
}
