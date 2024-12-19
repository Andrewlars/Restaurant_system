package ADMIN;

import ORDER_SYSTEM.MainOrderSystem;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AddMenuItem {

    private static final String FOLDER_PATH = "MenuItems";  // Folder name where the CSV file will be stored
    private static final String FILE_NAME = FOLDER_PATH + "/menu.csv";  // CSV file path

    private Scanner scanner;

    // Constructor
    public AddMenuItem() {
        this.scanner = new Scanner(System.in);
    }

    // Display the category menu to the user
    public void displayCategoryMenu() {
        AdminMenu ads = new AdminMenu();
        MainOrderSystem.clearScreen();
        System.out.println("\t\t\t\t\t\tCATEGORIES");
        System.out.println("\t\t\t===================================================================");
        System.out.println("\t\t\t|                        [1] Chicken and Platters                 |");
        System.out.println("\t\t\t|                        [2] Breakfast                            |");
        System.out.println("\t\t\t|                        [3] Burgers                              |");
        System.out.println("\t\t\t|                        [4] Drinks and Desserts                  |");
        System.out.println("\t\t\t|                        [5] Coffee                               |");
        System.out.println("\t\t\t|                        [6] Fries                                |");
        System.out.println("\t\t\t|                        [7] Go back                              |");
        System.out.println("\t\t\t===================================================================\n\n");
        clearScreenBottom();
        System.out.println("\n");
        System.out.print("\t\t\tEnter: ");

        int categoryChoice = -1;
        while (true) {
            try {
                categoryChoice = scanner.nextInt();
                scanner.nextLine();  // Consume the newline character

                if (categoryChoice < 1 || categoryChoice > 7) {
                    System.out.println("\t\t\tInvalid selection. Please choose a valid category.");
                    displayCategoryMenu();
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("\t\t\tInvalid input. Please enter a number between 1 and 7.");
                scanner.nextLine();  // Clear the invalid input
            }
        }

        // Define category variables
        String category = "";

        // Handle category selection
        switch (categoryChoice) {
            case 1:
                category = "Chicken and Platters";
                break;
            case 2:
                category = "Breakfast";
                break;
            case 3:
                category = "Burger";
                break;
            case 4:
                category = "Drinks and Dessert";
                break;
            case 5:
                category = "Coffee";
                break;
            case 6:
                category = "Fries";
                break;
            case 7:
                ads.displayMenu();
                return;
                
        }

        displayCategoryItems(category);
        addItemsToCategory(category);
    }

    private void displayCategoryItems(String category) {
    	System.out.println("\t\t\t============================================================================");
        System.out.printf("\t\t\t| %-30s | %-10s | %-26s |\n", "Item Name", "Price", "Category");
        System.out.println("\t\t\t============================================================================");

        boolean itemsFound = false; // Track if items are found for the category
        try (Scanner fileScanner = new Scanner(new File(FILE_NAME))) {
            // Skip the header line
            if (fileScanner.hasNextLine()) {
                fileScanner.nextLine();
            }

            // Read and display items belonging to the selected category
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(", ");
                if (parts.length == 3 && parts[2].equalsIgnoreCase(category)) {
                    System.out.printf("\t\t\t| %-30s | %-10s | %-26s |\n", parts[0], parts[1], parts[2]);
                    itemsFound = true;
                }
            }

            if (!itemsFound) {
                System.out.println("\t\t\tNo items found for the " + category + " category.");
            }
        } catch (IOException e) {
            System.out.println("\t\t\tError reading the menu: " + e.getMessage());
        }
        System.out.println("\t\t\t============================================================================");
    }

    private void addItemsToCategory(String category) {
        String[] itemNames = new String[10];
        double[] itemPrices = new double[10];
        int itemCount = 0;

        // Allow user to input items up to 10
        System.out.println("\t\t\tEnter up to 10 items for the " + category + " category:");

        while (itemCount < 10) {
            // Ask for the item name
            System.out.print("\t\t\tEnter item name (or type '0' to stop): ");
            String itemName = scanner.nextLine();

            // Check if the user wants to stop adding items
            if (itemName.equalsIgnoreCase("0")) {
                break;
            }

            // Ask for the item price
            double itemPrice = -1;
            while (true) {
                System.out.print("\t\t\tEnter price for " + itemName + " (or type '0' to stop): ");
                try {
                    itemPrice = scanner.nextDouble();
                    scanner.nextLine();  // Consume the newline character
                    if (itemPrice < 0) {
                        System.out.println("\t\t\tInvalid price. Please enter a positive number.");
                    } else {
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("\t\t\tInvalid input. Please enter a valid number.");
                    scanner.nextLine();  // Clear the invalid input
                }
            }

            if (itemPrice == 0) {
                break;
            }

            // Store the item details
            itemNames[itemCount] = itemName;
            itemPrices[itemCount] = itemPrice;
            itemCount++;
        }

        // Write the items to the CSV file
        writeToCSV(itemNames, itemPrices, itemCount, category);

        // Reload the updated menu after adding items
        displayCategoryMenu();
    }

    private void writeToCSV(String[] itemNames, double[] itemPrices, int itemCount, String category) {
        // Ensure the folder exists
        File folder = new File(FOLDER_PATH);
        if (!folder.exists()) {
            folder.mkdir();  // Create the folder if it doesn't exist
        }

        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            // Check if the file is empty and write the header if needed
            boolean isFileEmpty = new File(FILE_NAME).length() == 0;
            if (isFileEmpty) {
                writer.append("Item, Price, Category\n");
            }

            // Write the menu items to the CSV file
            for (int i = 0; i < itemCount; i++) {
                writer.append(String.format("%s, %.2f, %s\n", itemNames[i], itemPrices[i], category));
            }
        } catch (IOException e) {
            System.out.println("\t\t\tAn error occurred while saving the menu: " + e.getMessage());
        }
    }

    private void readMenuFromCSV() {
    	System.out.println("\t\t\t============================================================================");
        System.out.printf("\t\t\t%-30s %-10s %-26s\n", "Item Name", "Price", "Category");
        System.out.println("\t\t\t============================================================================");

        boolean itemsFound = false; // Track if items exist in the menu
        try (Scanner fileScanner = new Scanner(new File(FILE_NAME))) {
            // Skip the header line if it exists
            if (fileScanner.hasNextLine()) {
                fileScanner.nextLine();
            }

            // Read and display all items
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(", ");
                if (parts.length == 3) {
                    System.out.printf("\t\t\t%-30s %-10s %-26s\n", parts[0], parts[1], parts[2]);
                    itemsFound = true;
                }
            }

            if (!itemsFound) {
                System.out.println("\t\t\tNo items found in the menu.");
            }
        } catch (IOException e) {
            System.out.println("\t\t\tError reading the menu: " + e.getMessage());
        }
        System.out.println("\t\t\t============================================================================");
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
}
