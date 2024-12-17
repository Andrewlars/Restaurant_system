package EMPLOYEE;

import ORDER_SYSTEM.MainOrderSystem;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class TicketOrder {
	
	public static void clearScreen() {
        for (int i = 0; i < 50; i++) {  // Print 50 newlines
            System.out.println();
        }   
    }
    
    public static void clearScreenBottom() {
        for (int i = 0; i < 30; i++) {  // Print 50 newlines
            System.out.println();
        }   
    }
    
    public static void clearScreenSmall() {
        for (int i = 0; i < 20; i++) {  // Print 50 newlines
            System.out.println();
        }   
    }

    // WatchService to monitor changes in the CSV file
    private static WatchService watchService;

    // Method to initialize the WatchService for monitoring the CSV file
    private static void startFileWatcher(String csvFilePath) throws IOException {
        Path path = Paths.get(csvFilePath).getParent(); // Get the directory of the CSV file
        watchService = FileSystems.getDefault().newWatchService();
        path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

        // Start a separate thread to monitor the file
        new Thread(() -> {
            while (true) {
                try {
                    WatchKey key = watchService.take(); // Wait for a change
                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                            Path modifiedFilePath = (Path) event.context();
                            if (modifiedFilePath.toString().equals(new File(csvFilePath).getName())) {
                                // CSV file was modified, reload the orders
                                System.out.println("CSV file changed. Reloading orders...");
                                fetchOrdersFromCSV(csvFilePath); // Reload orders data
                            }
                        }
                    }
                    key.reset();
                } catch (InterruptedException e) {
                    System.out.println("File watcher interrupted: " + e.getMessage());
                    break;
                }
            }
        }).start();
    }

    // Method to fetch the order data from the CSV file
    private static Map<String, List<Order>> fetchOrdersFromCSV(String csvFile) {
        Map<String, List<Order>> ordersMap = new HashMap<>();
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip the header line
            br.readLine();

            // Read through the CSV file and populate the orders map
            while ((line = br.readLine()) != null) {
                String[] orderDetails = line.split(cvsSplitBy);
                if (orderDetails.length < 8) continue; // Skip incomplete rows

                String orderNumber = orderDetails[0].trim();
                String itemName = orderDetails[1].trim();
                int quantity = Integer.parseInt(orderDetails[2].trim());
                double totalAmount = Double.parseDouble(orderDetails[3].trim().replace(" PHP", "").trim()); // Now parsing as double
                String paymentMethod = orderDetails[4].trim();
                String diningOption = orderDetails[5].trim();
                String status = orderDetails[6].trim();
                String date = orderDetails[7].trim();

                // Only consider served orders now
                if ("served".equalsIgnoreCase(status)) {
                    Order order = new Order(orderNumber, itemName, quantity, totalAmount, paymentMethod, diningOption, date);
                    ordersMap.putIfAbsent(orderNumber, new ArrayList<>());
                    ordersMap.get(orderNumber).add(order);
                }
            }

        } catch (IOException e) {
            System.out.println("\t\t\tError reading the CSV file: " + e.getMessage());
        }

        return ordersMap;
    }

    // Method to display the orders and generate the receipt
    public static void handleOrder() {

        String csvFile = "OrderRecords/order_summary.csv"; // Adjust this path if necessary
        try {
            startFileWatcher(csvFile); // Start the file watcher when handling orders
        } catch (IOException e) {
            System.out.println("\t\t\tError starting file watcher: " + e.getMessage());
            return;
        }

        // Fetch orders from the CSV file
        Map<String, List<Order>> ordersMap = fetchOrdersFromCSV(csvFile);

        if (ordersMap.isEmpty()) {
            System.out.println("\t\t\tNo served orders found.");
            return;
        }

        MainOrderSystem.clearScreen();
        System.out.println("\t\t\tOrder numbers:\n\n");
        int index = 1;
        for (String orderNumber : ordersMap.keySet()) {
            System.out.println("\t\t\t[" + index++ + "] Order#" + orderNumber);
        }

        // Get user input to select an order
        Scanner scanner = new Scanner(System.in);
        int selectedOrderIndex = -1;
        while (true) {
            try {
                System.out.print("\t\t\tSelect Order number (0 to go back): ");
                if (scanner.hasNextInt()) {
                    selectedOrderIndex = scanner.nextInt();
                    if (selectedOrderIndex == 0) {
                        System.out.println("\t\t\tGoing back...");
                        return;
                    }
                    if (selectedOrderIndex > 0 && selectedOrderIndex <= ordersMap.size()) {
                        break; // Valid input
                    }
                } else {
                    System.out.println("\n\n\n\t\t\tInvalid choice. Please enter a number.");
                    scanner.next(); // Clear invalid input
                }
            } catch (InputMismatchException e) {
                System.out.println("\n\n\n\t\t\tInvalid input. Please enter a valid integer.\n\n\n");
                scanner.next(); // Clear invalid input
            }
        }

        // Find the selected order number based on index
        String selectedOrderNumber = new ArrayList<>(ordersMap.keySet()).get(selectedOrderIndex - 1);
        List<Order> selectedOrderItems = ordersMap.get(selectedOrderNumber);

        // Display the selected order details in tabular format
        System.out.println("\t\t\t========================================================================");
        System.out.printf("\t\t\t| %-13s  %-10s  %-10s  %-12s  %-15s |%n", "Order No.", "Quantity", "Items", "Price", "Total Price");
        System.out.println("\t\t\t========================================================================");

        boolean isFirstRow = true;
        double totalPrice = 0;
        for (Order order : selectedOrderItems) {
            double totalItemPrice = order.getTotalPrice();
            totalPrice += totalItemPrice;

            System.out.printf("\t\t\t| %-13s  %-10d  %-10s  %-12s  %-15s |%n",
                    isFirstRow ? selectedOrderNumber : "",
                    order.getQuantity(), order.getItemName(),
                    String.format("%.2f PHP", order.getUnitPrice()), String.format("%.2f PHP", totalItemPrice));
            isFirstRow = false;
        }
        double result = totalPrice *.12;
        System.out.println("\t\t\t========================================================================");
        System.out.printf("\t\t\t| %-51s  %-15s |%n", "Total + Tax", String.format("%.2f PHP", totalPrice+result));
        System.out.println("\t\t\t========================================================================");
        clearScreenSmall();

        // Ask if the user wants to generate the receipt
        System.out.print("\n\t\t\tDo you want to generate receipt (1 yes / 0 no)? ");
        int generateReceipt = scanner.nextInt();

        if (generateReceipt == 1) {
            generateReceiptFile(selectedOrderNumber, selectedOrderItems, totalPrice);
            System.out.println("\n\t\t\tReceipt has been generated and saved to the 'Receipts' folder.\n");
        } else {
            System.out.println("\n\t\t\tReceipt generation cancelled.\n");
        }
    }

    // Receipt space calculator
    public static String padToWidth(String label, String value, int totalWidth) {
        int spacesNeeded = totalWidth - (label.length() + value.length());
        spacesNeeded = Math.max(0, spacesNeeded); // Ensure non-negative space count
        StringBuilder padding = new StringBuilder();
        for (int i = 0; i < spacesNeeded; i++) {
            padding.append(" ");
        }
        return label + padding + value; // Combine label, spaces, and value
    }

    // for individual price of item
    public static String periodValue(String value, int totalWidth) {
        int spacesNeeded = totalWidth - value.length();
        spacesNeeded = Math.max(0, spacesNeeded); // Ensure non-negative space count
        StringBuilder padding = new StringBuilder();
        for (int i = 0; i < spacesNeeded; i++) {
            padding.append(".");
        }
        return padding + value; // Combine spaces and value
    }

    // for total price of item
    public static String spaceRight(String value, int totalWidth) {
        int spacesNeeded = totalWidth - value.length();
        spacesNeeded = Math.max(0, spacesNeeded); // Ensure non-negative space count
        StringBuilder padding = new StringBuilder();
        for (int i = 0; i < spacesNeeded; i++) {
            padding.append(" ");
        }
        return value + padding; // Combine spaces and value
    }
    
    public static String spaceLeft(String value, int totalWidth) {
        int spacesNeeded = totalWidth - value.length();
        spacesNeeded = Math.max(0, spacesNeeded); // Ensure non-negative space count
        StringBuilder padding = new StringBuilder();
        for (int i = 0; i < spacesNeeded; i++) {
            padding.append(" ");
        }
        return padding + value; // Combine spaces and value
    }
    // Receipt space calculator ending


    private static void generateReceiptFile(String orderNumber, List<Order> orderItems, double totalPrice) {
        String folderPath = "Receipts";

        // Create the folder if it doesn't exist
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdir();
        }

        String fileName = folderPath + File.separator + "receipt_order_#" + orderNumber + ".txt";

        Scanner scanner = new Scanner(System.in);
        double amountPaid = 0;

        // Define tax rate (e.g., 12%)
        double taxRate = 0.12;
        double tax = totalPrice * taxRate;
        double grandTotal = totalPrice + tax;

               // Prompt user for a valid amount
        while (true) {
            System.out.print("\t\t\tEnter the Amount Paid by the customer: ");
            if (scanner.hasNextDouble()) {
                amountPaid = scanner.nextDouble();
                if (amountPaid >= grandTotal) {
                    break; // Valid input, exit loop
                }
            } else {
                scanner.nextLine(); // Clear invalid input
            }
            System.out.println("\t\t\tInvalid amount. The amount paid must be greater than or equal to " + Math.ceil(grandTotal) + " PHP.");
        }

        double amountDue = amountPaid - grandTotal;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("\t==================================================\n");
            writer.write("\t                   ORDER #" + orderNumber + "\n");
            writer.write("\t--------------------------------------------------\n");

            // Write order details
            Order firstOrder = orderItems.get(0);
            writer.write("\t" + padToWidth("DATE:", firstOrder.getDate(), 50) + "\n");
            writer.write("\t" + padToWidth("DINING OPTION:", firstOrder.getDiningOption(), 50) + "\n");
            writer.write("\t" + padToWidth("PAYMENT METHOD:", firstOrder.getPaymentMethod(), 50) + "\n\n");
            writer.write("\t--------------------------------------------------\n");

            // Write table headers
            writer.write(String.format("\t%-5s %-16s %-15s %-5s\n", "QTY", "ITEM", "UNIT PRICE", "TOTAL PRICE"));
            writer.write("\t--------------------------------------------------\n\n");

            // Write individual item details
            for (Order order : orderItems) {
            	writer.write(
            			"\t" +
            			spaceRight(String.format("%d", order.getQuantity()), 6) +
            			spaceRight(order.getItemName(), 17) +
            			spaceLeft(String.format("₱%.2f", order.getUnitPrice()), 10) +
            			"      " +
            			spaceLeft(String.format("₱%.2f", order.getTotalPrice()), 11) +
            			"\n"
            			);
//                writer.write(String.format(
//                    "\t%-5d %-16s %-15s %-5s\n",
//                    order.getQuantity(),
//                    order.getItemName(),
//                    String.format("%.2f PHP", order.getUnitPrice()),
//                    String.format("%.2f PHP", order.getTotalPrice())
//                ));
            }

            // Write total price, tax, and grand total
            writer.write("\n\t" + padToWidth("TOTAL:", String.format("₱%.2f", totalPrice), 50) + "\n");
            writer.write("\t" + padToWidth("TAX (12%):", String.format("₱%.2f", tax), 50) + "\n");
            writer.write("\t" + padToWidth("GRAND TOTAL:", String.format("₱%.2f", grandTotal), 50) + "\n");

            // Write amount paid and amount due
            writer.write("\n\t" + padToWidth("AMOUNT PAID:", String.format("₱%.2f", amountPaid), 50) + "\n");
            writer.write("\t" + padToWidth("AMOUNT DUE:", String.format("₱%.2f", amountDue), 50) + "\n");

            writer.write("\n\t==================================================\n");
        } catch (IOException e) {
            System.out.println("\t\t\tError writing the receipt: " + e.getMessage());
        }
    }

    // Order class to hold order details
    private static class Order {
        private String orderNumber;
        private String itemName;
        private int quantity;
        private double totalAmount;  // Changed to double to support decimals
        private String paymentMethod;
        private String diningOption;
        private String date;

        public Order(String orderNumber, String itemName, int quantity, double totalAmount, String paymentMethod, String diningOption, String date) {
            this.orderNumber = orderNumber;
            this.itemName = itemName;
            this.quantity = quantity;
            this.totalAmount = totalAmount;
            this.paymentMethod = paymentMethod;
            this.diningOption = diningOption;
            this.date = date;
        }

        public double getTotalPrice() {
            // Return total amount as a double (already parsed as double)
            return totalAmount;
        }

        public double getUnitPrice() {
            // Return the unit price calculated from totalAmount and quantity
            return totalAmount / quantity;
        }

        public String getItemName() {
            return itemName;
        }

        public int getQuantity() {
            return quantity;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public String getDiningOption() {
            return diningOption;
        }

        public String getDate() {
            return date;
        }
    }
}

