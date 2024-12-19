package ADMIN;

import java.util.Scanner;
import ORDER_SYSTEM.MainOrderSystem;

class AdminMenu {
    private Scanner scanner = new Scanner(System.in);  // Initialize the scanner
    RegisterEmployeeMenu registerEmployee = new RegisterEmployeeMenu();
    AddMenuItem addMenuItem = new AddMenuItem();
    UpdateMenuItem updateMenuItem = new UpdateMenuItem();
    DeleteMenuItem deleteMenuItem = new DeleteMenuItem();

    public void displayMenu() {
        while (true) {  // Loop until a valid choice is provided
            try {
                MainOrderSystem.clearScreen();
                System.out.println("\t\t\t\t\t\tADMIN MENU");
                System.out.println("\t\t\t===================================================================");
                System.out.println("\t\t\t|                        [1] Add menu item                        |");
                System.out.println("\t\t\t|                        [2] Update menu item                     |");
                System.out.println("\t\t\t|                        [3] Delete menu item                     |");
                System.out.println("\t\t\t|                        [4] Register Employee                    |");
                System.out.println("\t\t\t|                        [5] Logout                               |");
                System.out.println("\t\t\t===================================================================\n\n");
                clearScreenBottom();
                System.out.print("\t\t\tEnter: ");

                // Validate if the input is an integer
                if (!scanner.hasNextInt()) {
                    System.out.println("\t\t\tInvalid input. Please enter a number between 1 and 5.");
                    scanner.nextLine();  // Clear the invalid input
                    continue;  // Restart the loop
                }

                int choice = scanner.nextInt();
                scanner.nextLine();  // Clear the newline character

                // Handle valid choices
                switch (choice) {
                    case 1:
                        addMenuItem.displayCategoryMenu();
                        return;
                    case 2:
                        updateMenuItem.displayCategoryMenu();
                        return;
                    case 3:
                        deleteMenuItem.displayCategoryMenu();
                        return;
                    case 4:
                        registerEmployee.displayRegisterMenu();
                        return;
                    case 5:
                        System.out.println("\t\t\tLogging out...");
                        return;
                    default:
                        System.out.println("\t\t\tInvalid choice. Please enter a number between 1 and 5.");
                }
            } catch (Exception e) {
                System.err.println("\t\t\tAn error occurred: " + e.getMessage());
                scanner.nextLine();  // Clear any leftover invalid input
            }
        }
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
