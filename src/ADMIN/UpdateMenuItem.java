package ADMIN;

import java.io.*;
import java.util.*;

import ORDER_SYSTEM.MainOrderSystem;

public class UpdateMenuItem {

    private static final String FOLDER_PATH = "MenuItems";  // Folder name where the CSV file is stored
    private static final String FILE_NAME = FOLDER_PATH + "/menu.csv";  // CSV file path
    Scanner scanner;

    // Constructor
    public UpdateMenuItem() {
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

        int categoryChoice = -1;
        while (categoryChoice < 1 || categoryChoice > 7) {
            System.out.print("\t\t\tEnter: ");
            if (scanner.hasNextInt()) {
                categoryChoice = scanner.nextInt();
                scanner.nextLine();  // Consume the newline character
            } else {
                System.out.println("\t\t\tInvalid input. Please enter a number between 1 and 7.");
                scanner.nextLine(); // Clear invalid input
            }
        }

        String category = "";
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

        updateItemsInCategory(category);
    }

    private void updateItemsInCategory(String category) {
        AdminMenu adminMenu = new AdminMenu();
        List<String[]> menuItems = readMenuFromCSV();

        List<String[]> categoryItems = new ArrayList<>();
        for (String[] item : menuItems) {
            if (item[2].equalsIgnoreCase(category)) {
                categoryItems.add(item);
            }
        }

        if (categoryItems.isEmpty()) {
            System.out.println("\nNo items found in the " + category + " category.\n");
            return;
        }

        System.out.println("\t\t\t===============================================================");
        System.out.printf("\t\t\t| %-5s | %-30s | %-18s |\n", "No.", "Item Name", "Price");
        System.out.println("\t\t\t===============================================================");
        for (int i = 0; i < categoryItems.size(); i++) {
            String[] item = categoryItems.get(i);
            System.out.printf("\t\t\t| %-5s | %-30s | %-18s |\n", (i + 1), item[0], item[1]);
        }
        System.out.println("\t\t\t===============================================================\n");
        System.out.println("\t\t\tEnter 0 to go back to the category menu.");

        int itemChoice = -1;
        while (itemChoice < 0 || itemChoice > categoryItems.size()) {
            System.out.print("\t\t\tEnter the number of the item to update: ");
            if (scanner.hasNextInt()) {
                itemChoice = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("\t\t\tInvalid input. Please enter a valid number.");
                scanner.nextLine();
            }

            if (itemChoice == 0) {
                displayCategoryMenu();
                return;
            }

            if (itemChoice < 1 || itemChoice > categoryItems.size()) {
                System.out.println("\t\t\tInvalid selection. Please choose a valid item.");
            }
        }

        String[] selectedItem = categoryItems.get(itemChoice - 1);
        System.out.println("\n\t\t\tYou selected: " + selectedItem[0] + " - Price: " + selectedItem[1]);

        System.out.print("\t\t\tDo you want to change the item name? (y/n): ");
        String changeName = scanner.nextLine();

        if (changeName.equalsIgnoreCase("y")) {
            System.out.print("\t\t\tEnter the new name for " + selectedItem[0] + ": ");
            selectedItem[0] = scanner.nextLine();
        }

        System.out.print("\t\t\tDo you want to change the price? (y/n): ");
        String changePrice = scanner.nextLine();

        if (changePrice.equalsIgnoreCase("y")) {
            boolean validPrice = false;
            while (!validPrice) {
                System.out.print("\t\t\tEnter the new price for " + selectedItem[0] + ": ");
                if (scanner.hasNextInt()) {
                    int newPrice = scanner.nextInt();
                    scanner.nextLine();
                    selectedItem[1] = String.valueOf(newPrice);
                    validPrice = true;
                } else {
                    System.out.println("\t\t\tInvalid input. Please enter a valid price.");
                    scanner.nextLine();
                }
            }
        }

        for (String[] menuItem : menuItems) {
            if (menuItem[0].equalsIgnoreCase(selectedItem[0]) && menuItem[2].equalsIgnoreCase(category)) {
                menuItem[1] = selectedItem[1];
            }
        }

        writeToCSV(menuItems);
        System.out.println("\n\t\t\tItem updated successfully!\n");
        updateItemsInCategory(category);
    }

    private List<String[]> readMenuFromCSV() {
        List<String[]> menuItems = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Item, Price, Category")) {
                    continue;
                }
                String[] parts = line.split(", ");
                if (parts.length == 3) {
                    menuItems.add(parts);
                }
            }
        } catch (IOException e) {
            System.out.println("\t\t\tAn error occurred while reading the menu: " + e.getMessage());
        }
        return menuItems;
    }

    private void writeToCSV(List<String[]> menuItems) {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            writer.append("Item, Price, Category\n");
            for (String[] item : menuItems) {
                writer.append(String.join(", ", item) + "\n");
            }
            System.out.println("\t\t\tMenu items updated in the CSV file.");
        } catch (IOException e) {
            System.out.println("\t\t\tAn error occurred while saving the updated menu: " + e.getMessage());
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