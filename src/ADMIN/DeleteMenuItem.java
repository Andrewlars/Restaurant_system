package ADMIN;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ORDER_SYSTEM.MainOrderSystem;

public class DeleteMenuItem {

    private static final String FOLDER_PATH = "MenuItems"; // Folder name where the CSV file is stored
    private static final String FILE_NAME = FOLDER_PATH + "/menu.csv"; // CSV file path

    private Scanner scanner;

    // Constructor
    public DeleteMenuItem() {
        this.scanner = new Scanner(System.in);
    }

    // Display the category menu to the user
    public void displayCategoryMenu() {
        AdminMenu ads = new AdminMenu();
        try {
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
            System.out.print("\t\t\tEnter: ");

            if (scanner.hasNextInt()) {
                int categoryChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
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
                    default:
                        System.out.println("\t\t\tInvalid selection. Please choose a valid category.");
                        displayCategoryMenu();
                        return;
                }

                // Proceed to delete item from the selected category
                deleteItemFromCategory(category);
            } else {
                scanner.nextLine(); // Clear invalid input
                System.out.println("\t\t\tInvalid input. Please enter a valid number.");
                displayCategoryMenu();
            }
        } catch (Exception e) {
            System.out.println("\t\t\tAn unexpected error occurred: " + e.getMessage());
            displayCategoryMenu();
        }
    }

    // Method to delete an item from the selected category
    private void deleteItemFromCategory(String category) {
        try {
            List<String[]> menuItems = readMenuFromCSV();
            List<String[]> updatedMenuItems = new ArrayList<>();
            List<String[]> categoryItems = new ArrayList<>();

            System.out.println("\t\t\t============================================================================");
            System.out.printf("\t\t\t| %-30s | %-10s | %-26s |\n", "Item", "Price", "Category");
            System.out.println("\t\t\t============================================================================");

            // Filter items by category and display them
            boolean categoryFound = false;
            for (String[] item : menuItems) {
                if (item[2].equalsIgnoreCase(category)) {
                    categoryFound = true;
                    categoryItems.add(item);
                    System.out.printf("\t\t\t| %-30s | %-10s | %-26s |\n", item[0], item[1], item[2]);
                } else {
                    updatedMenuItems.add(item);
                }
            }

            if (!categoryFound) {
                System.out.println("\n\t\t\tNo items found for the selected category.");
                displayCategoryMenu();
                return;
            }

            System.out.println("\t\t\t============================================================================");

            while (true) {
                System.out.print("\t\t\tEnter the item name to delete (or 0 to go back): ");
                String itemToDelete = scanner.nextLine();

                if (itemToDelete.equals("0")) {
                    displayCategoryMenu();
                    return;
                }

                boolean itemExists = false;
                for (String[] item : categoryItems) {
                    if (item[0].equalsIgnoreCase(itemToDelete)) {
                        itemExists = true;
                        break;
                    }
                }

                if (itemExists) {
                    System.out.print("\t\t\tAre you sure you want to delete the item: " + itemToDelete + "? (Y/N): ");
                    String confirmation = scanner.nextLine();

                    if (confirmation.equalsIgnoreCase("Y")) {
                        // Remove the item
                        categoryItems.removeIf(item -> item[0].equalsIgnoreCase(itemToDelete));
                        updatedMenuItems.addAll(categoryItems);
                        writeToCSV(updatedMenuItems);
                        System.out.println("\t\t\tItem deleted successfully.");
                        displayCategoryMenu();
                        return;
                    } else if (confirmation.equalsIgnoreCase("N")) {
                        System.out.println("\t\t\tItem deletion canceled.");
                        displayCategoryMenu();
                        return;
                    } else {
                        System.out.println("\t\t\tInvalid confirmation input. Please try again.");
                    }
                } else {
                    System.out.println("\t\t\tItem not found. Please enter a valid item name.");
                }
            }
        } catch (Exception e) {
            System.out.println("\t\t\tAn error occurred: " + e.getMessage());
            displayCategoryMenu();
        }
    }

    // Method to read the menu from the CSV file
    private List<String[]> readMenuFromCSV() {
        List<String[]> menuItems = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("\t\t\tMenu file does not exist.");
            return menuItems;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.startsWith("Item, Price, Category")) {
                    continue; // Skip the header
                }
                String[] itemDetails = line.split(", ");
                menuItems.add(itemDetails);
            }
        } catch (IOException e) {
            System.out.println("\t\t\tAn error occurred while reading the menu: " + e.getMessage());
        }

        return menuItems;
    }

    // Method to write the updated menu back to the CSV file
    private void writeToCSV(List<String[]> updatedMenuItems) {
        try {
            File folder = new File(FOLDER_PATH);
            if (!folder.exists()) {
                folder.mkdir(); // Create the folder if it doesn't exist
            }

            try (FileWriter writer = new FileWriter(FILE_NAME)) {
                writer.append("Item, Price, Category\n");
                for (String[] item : updatedMenuItems) {
                    writer.append(item[0] + ", " + item[1] + ", " + item[2] + "\n");
                }
                System.out.println("\t\t\tMenu updated successfully.");
            }
        } catch (IOException e) {
            System.out.println("\t\t\tAn error occurred while updating the menu: " + e.getMessage());
        }
    }

    public static void clearScreen() {
        for (int i = 0; i < 5; i++) {
            System.out.println();
        }
    }

    public static void clearScreenBottom() {
        for (int i = 0; i < 4; i++) {
            System.out.println();
        }
    }
}