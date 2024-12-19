package EMPLOYEE;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.math.BigDecimal;

public class ViewPendingOrders {

    // Custom class to hold order details
    static class Order {
        int orderNumber;
        List<OrderItem> items = new ArrayList<>();
        String status;

        // Add item to the order
        void addItem(OrderItem item) {
            items.add(item);
        }

        // Calculate the total price of the order
        BigDecimal getTotalPrice() {
            BigDecimal totalPrice = BigDecimal.ZERO;
            for (OrderItem item : items) {
                totalPrice = totalPrice.add(item.totalAmount);
            }
            return totalPrice;
        }
    }

    // Custom class to hold individual order item details
    static class OrderItem {
        int quantity;
        String itemName;
        BigDecimal totalAmount;

        OrderItem(int quantity, String itemName, BigDecimal totalAmount) {
            this.quantity = quantity;
            this.itemName = itemName;
            this.totalAmount = totalAmount;
        }
    }

    public static void displayOrdersByStatus() {
        Scanner scanner = new Scanner(System.in);
        String csvFile = "OrderRecords/order_summary.csv";  // Path to your CSV file
        String line;
        String cvsSplitBy = ",";

        // Maps to hold orders based on their status
        Map<Integer, Order> pendingOrders = new HashMap<>();
        Map<Integer, Order> preparingOrders = new HashMap<>();
        Map<Integer, Order> completedOrders = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip the header line
            br.readLine();

            // Process data
            while ((line = br.readLine()) != null) {
                // Split the line by commas
                String[] orderDetails = line.split(cvsSplitBy);

                // Ensure we have enough columns in the CSV row (9 expected)
                if (orderDetails.length < 9) {
                    continue;  // Skip rows that don't have enough columns
                }

                // Extract relevant details from the CSV columns
                String orderNumberStr = orderDetails[0].trim();
                String itemName = orderDetails[1].trim();
                int quantity = Integer.parseInt(orderDetails[2].trim());
                String totalAmountStr = orderDetails[3].trim();  // This is the total price for the quantity
                String status = orderDetails[6].trim();         // Order status

                // Validate and parse order number
                int orderNumber = 0;
                if (!orderNumberStr.isEmpty()) {
                    try {
                        orderNumber = Integer.parseInt(orderNumberStr);
                    } catch (NumberFormatException e) {
                        System.out.println("\t\t\tInvalid order number: " + orderNumberStr);
                        continue;  // Skip rows with invalid order number
                    }
                }

                // Convert total amount to BigDecimal for more precise handling
                BigDecimal itemTotalPrice = new BigDecimal(totalAmountStr.trim());

                // Get the order or create a new one if it doesn't exist
                Order order = preparingOrders.getOrDefault(orderNumber, new Order());
                order.orderNumber = orderNumber;  // Set the order number (in case it's a new order)
                order.status = status;  // Set the order's status

                // Add item to the order
                order.addItem(new OrderItem(quantity, itemName, itemTotalPrice));

                // Put the order back into the map based on its status
                if (status.equalsIgnoreCase("pending")) {
                    pendingOrders.put(orderNumber, order);
                } else if (status.equalsIgnoreCase("preparing")) {
                    preparingOrders.put(orderNumber, order);
                } else if (status.equalsIgnoreCase("completed")) {
                    completedOrders.put(orderNumber, order);
                }
            }

            // Display orders by status with formatted columns
            System.out.println("\t\t\t========================================================================");
            System.out.println(String.format("\t\t\t| %-20s%-20s%-20s", "Pending", "Preparing", "Completed |"));
            System.out.println("\t\t\t========================================================================");

            // Get the maximum size of any order list
            int maxSize = Math.max(Math.max(pendingOrders.size(), preparingOrders.size()), completedOrders.size());

            // Display each order status in a row, making sure to align them across columns
            for (int i = 0; i < maxSize; i++) {
                String pendingOrder = (i < pendingOrders.size()) ? "Order#" + new ArrayList<>(pendingOrders.keySet()).get(i) : "";
                String preparingOrder = (i < preparingOrders.size()) ? "Order#" + new ArrayList<>(preparingOrders.keySet()).get(i) : "";
                String completedOrder = (i < completedOrders.size()) ? "Order#" + new ArrayList<>(completedOrders.keySet()).get(i) : "";

                System.out.println(String.format("\t\t\t| %-20s%-20s%-20s", pendingOrder, preparingOrder, completedOrder));
            }

            System.out.println("\t\t\t========================================================================");

            // User input for selecting an order
            System.out.print("\n\t\t\tSelect Order number (0 to go back): ");
            int selectedOrder = scanner.nextInt();

            if (selectedOrder == 0) {
                return;  // Exit if user chooses to go back
            }

            // Display order details for selected order
            Order selected = getOrderByNumber(selectedOrder, pendingOrders, preparingOrders, completedOrders);
            if (selected != null) {
                displayOrderDetails(selected);
                // Allow user to change status
                changeOrderStatus(scanner, selected, csvFile, pendingOrders, preparingOrders, completedOrders);
            } else {
                System.out.println("\t\t\tInvalid order number.");
            }

        } catch (IOException e) {
            System.out.println("\t\t\tError reading the CSV file: " + e.getMessage());
        }
    }

    private static void changeOrderStatus(Scanner scanner, Order selectedOrder, String csvFile, Map<Integer, Order> pendingOrders, Map<Integer, Order> preparingOrders, Map<Integer, Order> completedOrders) {
        System.out.println("\n\t\t\tChange the status to?");
        System.out.println("\t\t\t1. Pending");
        System.out.println("\t\t\t2. Preparing");
        System.out.println("\t\t\t3. Completed");
        System.out.print("Enter: ");
        int choice = scanner.nextInt();

        String newStatus = "";

        switch (choice) {
            case 1:
                newStatus = "pending";
                break;
            case 2:
                newStatus = "preparing";
                break;
            case 3:
                newStatus = "completed";
                break;
            default:
                System.out.println("\t\t\tInvalid choice.");
                return;
        }

        // Update the status in the order object
        selectedOrder.status = newStatus;

        // Rebuild the maps with the new status
        updateOrderStatusInMap(selectedOrder, pendingOrders, preparingOrders, completedOrders);

        // Now save the updated status back to the CSV
        saveUpdatedStatusToCSV(csvFile, pendingOrders, preparingOrders, completedOrders);

        System.out.println("\t\t\tOrder status updated to " + newStatus + ".");
    }

    private static void updateOrderStatusInMap(Order selectedOrder, Map<Integer, Order> pendingOrders, Map<Integer, Order> preparingOrders, Map<Integer, Order> completedOrders) {
        // Remove the order from all status maps
        pendingOrders.remove(selectedOrder.orderNumber);
        preparingOrders.remove(selectedOrder.orderNumber);
        completedOrders.remove(selectedOrder.orderNumber);

        // Add the order back to the correct map based on the new status
        if (selectedOrder.status.equalsIgnoreCase("pending")) {
            pendingOrders.put(selectedOrder.orderNumber, selectedOrder);
        } else if (selectedOrder.status.equalsIgnoreCase("preparing")) {
            preparingOrders.put(selectedOrder.orderNumber, selectedOrder);
        } else if (selectedOrder.status.equalsIgnoreCase("completed")) {
            completedOrders.put(selectedOrder.orderNumber, selectedOrder);
        }
    }

    private static void saveUpdatedStatusToCSV(String csvFile, Map<Integer, Order> pendingOrders, Map<Integer, Order> preparingOrders, Map<Integer, Order> completedOrders) {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            StringBuilder newCsvContent = new StringBuilder();
            String line;

            // Skip the header line
            boolean isFirstLine = true;

            // Read the original file and modify the status as needed
            while ((line = reader.readLine()) != null) {
                // Skip the header line (first line of the CSV)
                if (isFirstLine) {
                    newCsvContent.append(line).append("\n");
                    isFirstLine = false;
                    continue;
                }

                String[] orderDetails = line.split(",");
                if (orderDetails.length >= 7) {
                    try {
                        // Try parsing the order number
                        int orderNumber = Integer.parseInt(orderDetails[0].trim());
                        Order order = getOrderByNumber(orderNumber, pendingOrders, preparingOrders, completedOrders);
                        if (order != null) {
                            orderDetails[6] = order.status;  // Update the status in the row
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("\t\t\tSkipping invalid order number in row: " + line);
                    }
                }

                // Append the updated line to the new CSV content
                newCsvContent.append(String.join(",", orderDetails)).append("\n");
            }

            // Write the modified content back to the CSV
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
                writer.write(newCsvContent.toString());
            }
        } catch (IOException e) {
            System.out.println("\t\t\tError saving the updated CSV file: " + e.getMessage());
        }
    }


    private static Order getOrderByNumber(int orderNumber, Map<Integer, Order>... orderMaps) {
        for (Map<Integer, Order> map : orderMaps) {
            if (map.containsKey(orderNumber)) {
                return map.get(orderNumber);
            }
        }
        return null;
    }

    private static void displayOrderDetails(Order order) {
    	System.out.println("\t\t\t========================================================================");
        System.out.println(String.format("\t\t\t| %-15s%-15s%-30s%-20s%-15s |", 
                "Order No.", "Quantity", "Items", "Price", "Total Price"));
        System.out.println("\t\t\t========================================================================");

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItem item : order.items) {
            System.out.printf("| %-15d%-15d%-30s%-20s%-15s |\n", 
                    order.orderNumber, item.quantity, item.itemName, 
                    item.totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP) + " PHP", 
                    totalPrice.add(item.totalAmount).setScale(2, BigDecimal.ROUND_HALF_UP) + " PHP");
            totalPrice = totalPrice.add(item.totalAmount);
        }

        System.out.println("\t\t\t========================================================================");
        System.out.println(String.format("\t\t\t| %-15s%-15s%-30s%-20s%-15s |", 
                "", "", "", "Total", totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP) + " PHP"));
        System.out.println("\t\t\t========================================================================");
    }
}
