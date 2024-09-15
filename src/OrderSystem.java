import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class OrderSystem {
    static Scanner scanner = new Scanner(System.in);
    static int orderCount = 0;
    static Order[] orders = new Order[100]; // array to store orders
    static int dineInOrTakeOut;

    public static void main(String[] args) {
    	displayDineInOrTakeOut(); // Ask the user for dine-in or take-out before the main menu starts

        int mainChoice;

        do {
            System.out.println("\nWelcome to the Restaurant!");
            System.out.println("1. Breakfast Menu");
            System.out.println("2. Chicken And Platters");
            System.out.println("3. Burger Menu");
            System.out.println("4. Drinks & Desserts Menu");
            System.out.println("5. Coffee Menu");
            System.out.println("6. Fries Menu");
            System.out.println("7. My Order");
            System.out.println("0. Go Back");
            System.out.print("Please select an option: ");
            mainChoice = scanner.nextInt();

            switch (mainChoice) {
                case 1:
                    displayBreakfastMenu();
                    break;
                case 2:
                    displayChickenAndPlatters();
                    break;
                case 3:
                    displayBurgerMenu();
                    break;
                case 4:
                    displayDrinksAndDessertsMenu();
                    break;
                case 5:
                    displayCoffeeMenu();
                    break;
                case 6:
                    displayFriesMenu();
                    break;
                case 7:
                    handleMyOrder();
                    break;
                case 0:
                    displayDineInOrTakeOut(); // Go back to the dine-in or take-out function
                    break;
                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        } while (true); // Infinite loop; exit handled in `displayDineInOrTakeOut`
    }
    
    public static void displayDineInOrTakeOut() {
        int choice;
        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Dine In");
            System.out.println("2. Take Out");
            System.out.println("3. Exit");

            System.out.print("Please select an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    dineInOrTakeOut = 1; // Dine In
                    return;
                case 2:
                    dineInOrTakeOut = 2; // Take Out
                    return;
                case 3:
                    System.out.println("Exiting system.");
                    System.exit(0); // Exit the system
                    break;
                default:
                    System.out.println("Invalid option. Please select again.");
            }
        }
    }



    public static void displayBreakfastMenu() {
        int choice, quantity, saveOrder;
        while (true) {
            System.out.println("\nBreakfast Menu:");
            for (int i = 0; i < MenuData.NUM_BREAKFAST_ITEMS; i++) {
                System.out.printf("%d. %s - %d PHP\n", i + 1, MenuData.breakfastItemNames[i], MenuData.breakfastItemPrices[i]);
            }
            System.out.print("Please select an option (1-10), input 0 to go back: ");
            choice = scanner.nextInt();

            if (choice == 0) return; // Go back to the main menu

            if (choice >= 1 && choice <= MenuData.NUM_BREAKFAST_ITEMS) {
                System.out.printf("You selected %s.\n", MenuData.breakfastItemNames[choice - 1]);

                System.out.print("Enter the quantity for this item: ");
                quantity = scanner.nextInt();

                System.out.print("Do you want to save this order? (1 for Yes, 0 for No): ");
                saveOrder = scanner.nextInt();

                if (saveOrder == 1) {
                    int price = MenuData.breakfastItemPrices[choice - 1] * quantity;
                    orders[orderCount++] = new Order("Breakfast", choice - 1, quantity, price);

                    displayOrderSummary();
                    waitForEnter();
                } else {
                    System.out.println("Order Not Saved.");
                }
            } else {
                System.out.println("Invalid Burger menu selection.");
            }
        }
    }
    
    public static void displayChickenAndPlatters() {
        int choice, quantity, saveOrder;
        while (true) {
            System.out.println("\nBreakfast Menu:");
            for (int i = 0; i < MenuData.NUM_CHICKENANDPLATTERS_ITEMS; i++) {
                System.out.printf("%d. %s - %d PHP\n", i + 1, MenuData.chickenAndPlattersItemNames[i], MenuData.chickenAndPlattersItemPrices[i]);
            }
            System.out.print("Please select an option (1-10), input 0 to go back: ");
            choice = scanner.nextInt();

            if (choice == 0) return; // Go back to the main menu

            if (choice >= 1 && choice <= MenuData.NUM_CHICKENANDPLATTERS_ITEMS) {
                System.out.printf("You selected %s.\n", MenuData.chickenAndPlattersItemNames[choice - 1]);

                System.out.print("Enter the quantity for this item: ");
                quantity = scanner.nextInt();

                System.out.print("Do you want to save this order? (1 for Yes, 0 for No): ");
                saveOrder = scanner.nextInt();

                if (saveOrder == 1) {
                    int price = MenuData.chickenAndPlattersItemPrices[choice - 1] * quantity;
                    orders[orderCount++] = new Order("ChickenAndPlatters", choice - 1, quantity, price);

                    displayOrderSummary();
                    waitForEnter();
                } else {
                    System.out.println("Order Not Saved.");
                }
            } else {
                System.out.println("Invalid Burger menu selection.");
            }
        }
    }
    public static void displayBurgerMenu() {
        int choice, quantity, saveOrder;
        while (true) {
            System.out.println("\nBurger Menu:");
            for (int i = 0; i < MenuData.NUM_BURGER_ITEMS; i++) {
                System.out.printf("%d. %s - %d PHP\n", i + 1, MenuData.burgerItemNames[i], MenuData.burgerItemPrices[i]);
            }
            System.out.print("Please select an option (1-10), input 0 to go back: ");
            choice = scanner.nextInt();

            if (choice == 0) return; // Go back to the main menu

            if (choice >= 1 && choice <= MenuData.NUM_BURGER_ITEMS) {
                System.out.printf("You selected %s.\n", MenuData.burgerItemNames[choice - 1]);

                System.out.print("Enter the quantity for this item: ");
                quantity = scanner.nextInt();

                System.out.print("Do you want to save this order? (1 for Yes, 0 for No): ");
                saveOrder = scanner.nextInt();

                if (saveOrder == 1) {
                    int price = MenuData.burgerItemPrices[choice - 1] * quantity;
                    orders[orderCount++] = new Order("Burger", choice - 1, quantity, price);

                    displayOrderSummary();
                    waitForEnter();
                } else {
                    System.out.println("Order Not Saved.");
                }
            } else {
                System.out.println("Invalid Burger menu selection.");
            }
        }
    }

    public static void displayDrinksAndDessertsMenu() {
        int choice, quantity, saveOrder;
        while (true) {
            System.out.println("\nDrinks and Dessert Menu:");
            for (int i = 0; i < MenuData.NUM_DRINKS_AND_DESSERTS_ITEMS; i++) {
                System.out.printf("%d. %s - %d PHP\n", i + 1, MenuData.drinksAndDessertsItemNames[i], MenuData.drinksAndDessertsItemPrices[i]);
            }
            System.out.print("Please select an option (1-10), input 0 to go back: ");
            choice = scanner.nextInt();

            if (choice == 0) return; // Go back to the main menu

            if (choice >= 1 && choice <= MenuData.NUM_DRINKS_AND_DESSERTS_ITEMS) {
                System.out.printf("You selected %s.\n", MenuData.drinksAndDessertsItemNames[choice - 1]);

                System.out.print("Enter the quantity for this item: ");
                quantity = scanner.nextInt();

                System.out.print("Do you want to save this order? (1 for Yes, 0 for No): ");
                saveOrder = scanner.nextInt();

                if (saveOrder == 1) {
                    int price = MenuData.drinksAndDessertsItemPrices[choice - 1] * quantity;
                    orders[orderCount++] = new Order("DrinksAndDesserts", choice - 1, quantity, price);

                    displayOrderSummary();
                    waitForEnter();
                } else {
                    System.out.println("Order Not Saved.");
                }
            } else {
                System.out.println("Invalid Drinks and Dessert menu selection.");
            }
        }
    }

    public static void displayCoffeeMenu() {
        int choice, quantity, saveOrder;
        while (true) {
            System.out.println("\nCoffee Menu:");
            for (int i = 0; i < MenuData.NUM_COFFEE_ITEMS; i++) {
                System.out.printf("%d. %s - %d PHP\n", i + 1, MenuData.coffeeItemNames[i], MenuData.coffeeItemPrices[i]);
            }
            System.out.print("Please select an option (1-9), input 0 to go back: ");
            choice = scanner.nextInt();

            if (choice == 0) return;

            if (choice >= 1 && choice <= MenuData.NUM_COFFEE_ITEMS) {
                System.out.printf("You selected %s.\n", MenuData.coffeeItemNames[choice - 1]);

                System.out.print("Enter the quantity for this item: ");
                quantity = scanner.nextInt();

                System.out.print("Do you want to save this order? (1 for Yes, 0 for No): ");
                saveOrder = scanner.nextInt();

                if (saveOrder == 1) {
                    int price = MenuData.coffeeItemPrices[choice - 1] * quantity;
                    orders[orderCount++] = new Order("Coffee", choice - 1, quantity, price);

                    displayOrderSummary();
                    waitForEnter();
                } else {
                    System.out.println("Order Not Saved.");
                }
            } else {
                System.out.println("Invalid Coffee menu selection.");
            }
        }
    }

    public static void displayFriesMenu() {
        int choice, quantity, saveOrder;
        while (true) {
            System.out.println("\nFries Menu:");
            for (int i = 0; i < MenuData.NUM_FRIES_ITEMS; i++) {
                System.out.printf("%d. %s - %d PHP\n", i + 1, MenuData.friesItemNames[i], MenuData.friesItemPrices[i]);
            }
            System.out.print("Please select an option (1-10), input 0 to go back: ");
            choice = scanner.nextInt();

            if (choice == 0) return;

            if (choice >= 1 && choice <= MenuData.NUM_FRIES_ITEMS) {
                System.out.printf("You selected %s.\n", MenuData.friesItemNames[choice - 1]);

                System.out.print("Enter the quantity for this item: ");
                quantity = scanner.nextInt();

                System.out.print("Do you want to save this order? (1 for Yes, 0 for No): ");
                saveOrder = scanner.nextInt();

                if (saveOrder == 1) {
                    int price = MenuData.friesItemPrices[choice - 1] * quantity;
                    orders[orderCount++] = new Order("Fries", choice - 1, quantity, price);

                    displayOrderSummary();
                    waitForEnter();
                } else {
                    System.out.println("Order Not Saved.");
                }
            } else {
                System.out.println("Invalid Fries menu selection.");
            }
        }
    }

    public static void handleMyOrder() {
        if (orderCount == 0) {
            System.out.println("\nNo orders to display.");
            waitForEnter();
            return;
        }

        System.out.println("\nYour Order Summary:");
        int total = 0;
        for (int i = 0; i < orderCount; i++) {
            String itemName = "";
            switch (orders[i].getCategory()) {
                case "Breakfast":
                    itemName = MenuData.breakfastItemNames[orders[i].getItemIndex()];
                    break;
                case "ChickenAndPlatters":
                    itemName = MenuData.chickenAndPlattersItemNames[orders[i].getItemIndex()];
                    break;
                case "Burger":
                    itemName = MenuData.burgerItemNames[orders[i].getItemIndex()];
                    break;
                case "DrinksAndDesserts":
                    itemName = MenuData.drinksAndDessertsItemNames[orders[i].getItemIndex()];
                    break;
                case "Coffee":
                    itemName = MenuData.coffeeItemNames[orders[i].getItemIndex()];
                    break;
                case "Fries":
                    itemName = MenuData.friesItemNames[orders[i].getItemIndex()];
                    break;
            }

            System.out.printf("Item: %s\n", itemName);
            System.out.printf("Quantity: %d\n", orders[i].getQuantity());
            System.out.printf("Price: %d PHP\n", orders[i].getPrice());
            total += orders[i].getPrice();
            System.out.println();
        }
        System.out.printf("Total Price: %d PHP\n", total);
        System.out.println("Dining Option: " + (dineInOrTakeOut == 1 ? "Dine In" : "Take Out"));

        System.out.print("Input 1 to check out or 2 to cancel, 0 to go back: ");
        int action = scanner.nextInt();

        if (action == 0) return;
        if (action == 1) handleCheckout(total);
        else if (action == 2) {
            System.out.println("Cancelling all orders...");
            resetOrders();
        } else {
            System.out.println("Invalid option selected.");
        }
    }

    public static void handleCheckout(int total) {
        System.out.println("\nSelect Payment Method:");
        System.out.println("1. Cash");
        System.out.println("2. E-money");
        System.out.print("Select payment method: ");     
        int paymentMethod = scanner.nextInt();

        if (paymentMethod != 1 && paymentMethod != 2) {
            System.out.println("Invalid payment method selected.");
            return;
        }

        File directory = new File("prototype12");
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it does not exist
        }

        try {
            File file = new File(directory, "receipt.txt");
            FileWriter writer = new FileWriter(file, true);

            writer.write("Receipt\n");
            writer.write("====================================\n");
            writer.write("Order Summary:\n");

            int totalPrice = 0;
            for (int i = 0; i < orderCount; i++) {
                String itemName = "";
                switch (orders[i].getCategory()) {
                    case "Breakfast":
                        itemName = MenuData.breakfastItemNames[orders[i].getItemIndex()];
                        break;
                    case "ChickenAndPlatters":
                        itemName = MenuData.chickenAndPlattersItemNames[orders[i].getItemIndex()];
                        break;
                    case "Burger":
                        itemName = MenuData.burgerItemNames[orders[i].getItemIndex()];
                        break;
                    case "DrinksAndDesserts":
                        itemName = MenuData.drinksAndDessertsItemNames[orders[i].getItemIndex()];
                        break;
                    case "Coffee":
                        itemName = MenuData.coffeeItemNames[orders[i].getItemIndex()];
                        break;
                    case "Fries":
                        itemName = MenuData.friesItemNames[orders[i].getItemIndex()];
                        break;
                }

                writer.write(String.format("Item: %s\n", itemName));
                writer.write(String.format("Quantity: %d\n", orders[i].getQuantity()));
                writer.write(String.format("Price: %d PHP\n", orders[i].getPrice()));
                totalPrice += orders[i].getPrice();
                writer.write("\n");
            }

            writer.write(String.format("Total Price: %d PHP\n", totalPrice));
            writer.write("Dining Option: " + (dineInOrTakeOut == 1 ? "Dine In" : "Take Out") + "\n");
            writer.write("Payment Method: " + (paymentMethod == 1 ? "Cash" : "E-money") + "\n");
            writer.write("====================================\n");
            writer.close();

            System.out.println("Receipt saved successfully.");
            resetOrders();

        } catch (IOException e) {
            System.out.println("Error saving the receipt: " + e.getMessage());
        }
    }
    
    
    public static void resetOrders() {
        orders = new Order[100];
        orderCount = 0;
        System.out.println("Orders have been reset.");
    }

    public static void displayOrderSummary() {
        System.out.println("\nOrder Summary:");
        int total = 0;
        for (int i = 0; i < orderCount; i++) {
            String itemName = "";
            switch (orders[i].getCategory()) {
            	case "Breakfast":
            		itemName = MenuData.breakfastItemNames[orders[i].getItemIndex()];
            		break;
            	case "ChickenAndPlatters":
        			itemName = MenuData.chickenAndPlattersItemNames[orders[i].getItemIndex()];
        			break;
                case "Burger":
                    itemName = MenuData.burgerItemNames[orders[i].getItemIndex()];
                    break;
                case "DrinksAndDesserts":
                    itemName = MenuData.drinksAndDessertsItemNames[orders[i].getItemIndex()];
                    break;
                case "Coffee":
                    itemName = MenuData.coffeeItemNames[orders[i].getItemIndex()];
                    break;
                case "Fries":
                    itemName = MenuData.friesItemNames[orders[i].getItemIndex()];
                    break;
            }

            System.out.printf("Item: %s\n", itemName);
            System.out.printf("Quantity: %d\n", orders[i].getQuantity());
            System.out.printf("Price: %d PHP\n", orders[i].getPrice());
            total += orders[i].getPrice();
            System.out.println();
        }
        System.out.printf("Total Price: %d PHP\n", total);
    }

    public static void waitForEnter() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine(); // Consume the leftover newline
        scanner.nextLine(); // Wait for the user to press Enter
    }
}

class Order {
    private String category;
    private int itemIndex;
    private int quantity;
    private int price;

    public Order(String category, int itemIndex, int quantity, int price) {
        this.category = category;
        this.itemIndex = itemIndex;
        this.quantity = quantity;
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public int getItemIndex() {
        return itemIndex;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }
}

class MenuData {
    // Example data for the menus
	static final int NUM_BREAKFAST_ITEMS = 9;
	static final int NUM_CHICKENANDPLATTERS_ITEMS = 8;
    static final int NUM_BURGER_ITEMS = 9;
    static final int NUM_DRINKS_AND_DESSERTS_ITEMS = 10;
    static final int NUM_COFFEE_ITEMS = 9;
    static final int NUM_FRIES_ITEMS = 10;
    
    static final String[] chickenAndPlattersItemNames = {"Regular Chicken Meal", "Regular Spicy Chicken Meal", "2pc Chicken Meal","2pc Spicy Chicken Meal", "Chicken Bucket (6pc regular chicken)", "Chicken Bucket (8pc regular chicken)", "Spicy Chicken Bucket (6pc spicy chicken)", "Spicy Chicken Bucket (8pc spicy chicken)"};
    static final int[] chickenAndPlattersItemPrices = {89, 99, 159, 179, 369, 459, 429, 579};
    
    static final String[] breakfastItemNames = {"Combo Breakfast 1", "Combo Breakfast 2r", "Combo Breakfast 3", "Combo Breakfast 4", "Pancake", "Regular Peach Mango Pie","Large Peach Mango Pie", "Regular Brewed Coffee","Iced Coffee"};
    static final int[] breakfastItemPrices = {69, 69, 69, 69, 39, 48, 69, 49, 49};

    static final String[] burgerItemNames = {"Regular Burger","Cheeseburger","Bacon Cheeseburger","Jumbo Burger (1 drink)","B1 (1 regular burger, 1 small fries, 1 drink)","B2 (1 cheeseburger, 1 small fries, 1 drink)","B3 (1 bacon cheeseburger, 1 small fries, 1 drink)","B4 (2 regular burger, 2 small fries, 2 drink)","B5 (2 jumbo burger, 2 medium size fries, 2 drinks)"};
    static final int[] burgerItemPrices = {59, 79, 99, 139, 89, 119, 139, 169, 399};

    static final String[] drinksAndDessertsItemNames = {"Regular Soft Drink","Large Soft Drink","Iced Tea","Milkshake (Vanilla/Chocolate)","Chocolate Sundae","Vanilla Sundae","Caramel Sundae","Banana Split","Choco Chip Cookie (2 pcs)","Mango Float"};
    static final int[] drinksAndDessertsItemPrices = {40, 60, 45, 80, 40, 40, 45, 80, 50, 90};

    static final String[] coffeeItemNames = {"Regular Coffee","Large Regular Coffee","Espresso","Americano","Latte","Cappuccino","Mocha","Iced Coffee","Cold Brew"};
    static final int[] coffeeItemPrices = {50, 70, 55, 60, 80, 80, 90, 70, 85};

    static final String[] friesItemNames = {"Small Fries","Medium Fries","Large Fries","Cheese Fries","Loaded Fries","Garlic Parmesan Fries","Spicy Fries","BBQ Fries","Truffle Fries","Sweet Potato Fries"};
    static final int[] friesItemPrices = { 40, 60, 80, 70, 100, 80, 70, 75, 90, 85};
}
