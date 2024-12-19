package EMPLOYEE;

import java.util.List;
import java.util.Scanner;

public class OrderUpdate {

    // Method to update the order
    public static void updateOrder(String orderNumber, List<Order> selectedOrderItems) {
        int updateChoice = -1;
        Scanner scanner = new Scanner(System.in);

        // Prompt the user with update options
        while (updateChoice != 3) {
            System.out.println("\n\t\t\tChoose what you want to update:");
            System.out.println("\t\t\t1. Modify quantity");
            System.out.println("\t\t\t2. Remove order");
            System.out.println("\t\t\t3. Go back");
            System.out.print("\n\t\t\tEnter choice: ");
            if (scanner.hasNextInt()) {
                updateChoice = scanner.nextInt();

                switch (updateChoice) {
                    case 1:
                    	ModifyQuantity.modifyQuantity(orderNumber, selectedOrderItems);
                        break;
                    case 2:
                    	RemoveOrder.removeOrder(orderNumber, selectedOrderItems);
                        break;
                    case 3:
                        // Cancel the update
                        System.out.println("\t\t\tUpdate cancelled. Returning to order details...");
                        return; // Return to the order details menu
                    default:
                        System.out.println("\t\t\tInvalid option. Please select 1, 2, or 3.");
                }
            } else {
                System.out.println("\t\t\tInvalid input. Please enter a number (1, 2, or 3).");
                scanner.next(); // Clear invalid input
            }
        }
    }

}