package ORDER_SYSTEM;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class HandleMyOrder {
    private Scanner scanner;
    private Order[] orders;
    private OrderCount orderCount;
    private int dineInOrTakeOut;

    public HandleMyOrder(Scanner scanner, Order[] orders, OrderCount orderCount, int dineInOrTakeOut) {
        this.scanner = scanner;
        this.orders = orders;
        this.orderCount = orderCount;
        this.dineInOrTakeOut = dineInOrTakeOut;
    }

    public void handleMyOrder() {
        if (orderCount.count == 0) {  // use orderCount from local
            System.out.println("\nNo orders to display.");
            waitForEnter();
            return;
        }

        System.out.println("\nYour Order Summary:");
        int total = 0;
        for (int i = 0; i < orderCount.count ; i++) { // use orderCount from local
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

    private void waitForEnter() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine(); // Consume the leftover newline
        scanner.nextLine(); // Wait for the user to press Enter
    }

    private void handleCheckout(int total) {
        System.out.println("\nSelect Payment Method:");
        System.out.println("1. Cash");
        System.out.println("2. E-money");
        System.out.print("Select payment method: ");     
        int paymentMethod = scanner.nextInt();

        if (paymentMethod != 1 && paymentMethod != 2) {
            System.out.println("Invalid payment method selected.");
            return;
        }

        File directory = new File("Receipt");
        if (!directory.exists()) {
            directory.mkdirs(); 
        }

        try {
            File file = new File(directory, "receipt.txt");
            FileWriter writer = new FileWriter(file, true);

            writer.write("Receipt\n");
            writer.write("====================================\n");
            writer.write("Order Summary:\n");

            int totalPrice = 0;
            for (int i = 0; i < orderCount.count ; i++) {
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

    private void resetOrders() {
        orders = new Order[100];
        orderCount.count = 0;
        System.out.println("Orders have been reset.");
    }
}

