package ORDER_SYSTEM;
import java.util.Scanner;

public class BreakfastMenu {
    Scanner scanner = new Scanner(System.in);
    private Order[] orders;
    private OrderCount orderCount; // use wrapper

    public BreakfastMenu(Order[] orders, OrderCount orderCount) {
        this.orders = orders;
        this.orderCount = orderCount;
    }

    public void displayBreakfastMenu() {
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
                    orders[orderCount.count++] = new Order("Breakfast", choice - 1, quantity, price); // update count

                    displayOrderSummary();
                    waitForEnter();
                } else {
                    System.out.println("Order Not Saved.");
                }
            } else {
                System.out.println("Invalid Breakfast menu selection.");
            }
        }
    }

    public void displayOrderSummary() {
        System.out.println("\nOrder Summary:");
        int total = 0;
        for (int i = 0; i < orderCount.count; i++) { // use orderCount from wrapper
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

    public void waitForEnter() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
        scanner.nextLine();
    }
}


        