package EMPLOYEE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CancelOrders {

    // Custom class to hold order details
    static class Order {
        int orderNumber;
        List<OrderItem> items = new ArrayList<>();
        
        // Add item to the order
        void addItem(OrderItem item) {
            items.add(item);
        }
        
        // Calculate the total price of the order
        double getTotalPrice() {
            double totalPrice = 0;
            for (OrderItem item : items) {
                totalPrice += item.totalAmount;
            }
            return totalPrice;
        }
    }

    // Custom class to hold individual order item details
    static class OrderItem {
        int quantity;
        String itemName;
        double totalAmount;

        OrderItem(int quantity, String itemName, double totalAmount) {
            this.quantity = quantity;
            this.itemName = itemName;
            this.totalAmount = totalAmount;  // totalAmount is already the total for that quantity, no need to multiply again
        }
    }

    // Method to display pending orders and mark them as served
    public static void cancelOrder() {
        String csvFile = "OrderRecords/order_summary.csv";  // Path to your CSV file
        String line;
        String cvsSplitBy = ",";
        
        // A map to hold the orders, with the order number as the key
        Map<Integer, Order> ordersMap = new TreeMap<>(Collections.reverseOrder());  // Sorting by order number in descending order

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip the header line
            br.readLine();

            // Process data first
            while ((line = br.readLine()) != null) {
                String[] orderDetails = line.split(cvsSplitBy);

                // Ensure we have enough columns in the CSV row (9 expected)
                if (orderDetails.length < 9) continue;

                // Extract order details
                int orderNumber = Integer.parseInt(orderDetails[0].trim());
                String itemName = orderDetails[1].trim();
                int quantity = Integer.parseInt(orderDetails[2].trim());
                double totalAmount = Double.parseDouble(orderDetails[3].trim());  // Parse as double for decimals
                String status = orderDetails[6].trim(); // Order status

                // Only process orders with "pending" status
                if (status.equalsIgnoreCase("pending")) {
                    // Add item to the order
                    Order order = ordersMap.getOrDefault(orderNumber, new Order());
                    order.orderNumber = orderNumber;  // Set order number
                    
                    // We directly use the totalAmount from the CSV file here, which is already the total for the quantity
                    order.addItem(new OrderItem(quantity, itemName, totalAmount));

                    // Put the order back into the map
                    ordersMap.put(orderNumber, order);
                }
            }

            // Display the orders in descending order
            if (ordersMap.isEmpty()) {
                System.out.println("\t\t\tNo pending orders found.");
                return;
            }

            // Print header
            clearScreen();
            System.out.println("\t\t\t===============================================================================================================");
            System.out.println(String.format("\t\t\t| %-13s  %-13s  %-27s  %-27s  %-19s |", 
                    "Order No.", "Quantity", "Items", "Total Price By Item Qty", "Total Price"));
            System.out.println("\t\t\t===============================================================================================================");

            // After all data has been processed, print everything in the unified format
            for (Order order : ordersMap.values()) {
                double totalPrice = order.getTotalPrice();

                // Print the order details along with its items
                for (OrderItem item : order.items) {
                    System.out.printf("\t\t\t| %-13d  %-13d  %-27s  %-27s  %-19s |\n", 
                            order.orderNumber, item.quantity, item.itemName, 
                            String.format("%.2f PHP", item.totalAmount), 
                            (item == order.items.get(order.items.size() - 1) ? String.format("%.2f PHP", totalPrice) : ""));
                }

                // Print the closing line for the order
                System.out.println("\t\t\t===============================================================================================================");
            }
            

            // Get user input to mark an order as completed
            Scanner scanner = new Scanner(System.in);
            while (true) {  // Infinite loop until a valid number is entered
                System.out.print("\n\n\t\t\tInput the Order number to mark as served (0 to go back): ");
                String input = scanner.nextLine();

                try {
                    int orderInput = Integer.parseInt(input);  // Attempt to parse input as an integer
                    if (ordersMap.containsKey(orderInput)) {
                        updateOrderStatus(csvFile, orderInput);
                        System.out.println("\t\t\tOrder #" + orderInput + " marked as cancelled.\n");
                        //timer
                        for (int i = 5; i > 0; i--) { // Countdown 
                            System.out.println("\t\t\t" + i + " returning to menu in...");
                            try {
                                Thread.sleep(1000); // Wait for 1 second
                            } catch (InterruptedException e) {
                                System.out.println("Timer was interrupted!");
                            }
                        }
                        //timer
                        break;  // Exit the loop after marking the order as served
                    } else if (orderInput == 0) {
                        System.out.println("\t\t\tGoing back...");
                        return;  // Exit to go back
                    } else {
                        System.out.println("\n\n\t\t\tInvalid order number.\n");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\n\n\t\t\tInvalid input. Please enter a valid order number.\n");
                }
            }

        } catch (IOException e) {
            System.out.println("\t\t\tError reading the CSV file: " + e.getMessage());
        }
    }
    // Method to update the status of an order to "served"
    private static void updateOrderStatus(String csvFile, int orderNumber) {
        StringBuilder fileContent = new StringBuilder();
        String line;
        String cvsSplitBy = ",";
        boolean updated = false;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Read the file line by line
            while ((line = br.readLine()) != null) {
                String[] orderDetails = line.split(cvsSplitBy);

                // Check if the order number matches
                if (orderDetails[0].trim().equals(String.valueOf(orderNumber))) {
                    // Change the status to "served"
                    orderDetails[6] = "cancelled";
                    updated = true;
                }

                // Rebuild the line with the updated status
                fileContent.append(String.join(",", orderDetails)).append("\n");
            }

            // If the order was updated, write the content back to the file
            if (updated) {
                try (FileWriter writer = new FileWriter(csvFile)) {
                    writer.write(fileContent.toString());
                }
            }
        } catch (IOException e) {
            System.out.println("\t\t\tError updating the CSV file: " + e.getMessage());
        }
    }
    
    public static void clearScreen() {
        for (int i = 0; i < 50; i++) {  // Print 50 newlines
            System.out.println();
        }   
    }
    
    public static void clearScreenBottom() {
        for (int i = 0; i < 40; i++) {  // Print 50 newlines
            System.out.println();
        }   
    }
}
