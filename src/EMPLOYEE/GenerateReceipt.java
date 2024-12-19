package EMPLOYEE;

import java.io.BufferedReader;   // Add this import
import java.io.FileReader;       // Add this import
import java.io.IOException;      // Add this import
import java.util.Scanner;        // Add this import for user input

public class GenerateReceipt {

    public static void generateReceipt(String orderNumber) {
        String csvFile = "OrderRecords/order_summary.csv"; // CSV file path
        String line;
        String cvsSplitBy = ",";
        Scanner scanner = new Scanner(System.in); // Create a Scanner object to get user input

        double totalPrice = 0.0;
        double totalTax = 0.0;
        double totalDiscount = 0.0;
        String date = "";
        String time = "";
        String diningOption = "";
        String paymentMethod = "";
        boolean orderFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip the header line
            br.readLine();

            // Search for the order number in the CSV file
            while ((line = br.readLine()) != null) {
                String[] orderDetails = line.split(cvsSplitBy);

                // Ensure row has enough columns (at least 10)
                if (orderDetails.length >= 10) {
                    String orderNum = orderDetails[0].trim(); // Trim spaces

                    if (orderNum.equalsIgnoreCase(orderNumber)) {  // Case-insensitive comparison
                        orderFound = true;

                        // Parse order details for each item in the order
                        String itemName = orderDetails[1].trim();
                        int quantity = Integer.parseInt(orderDetails[2].trim());
                        double itemTotalPrice = Double.parseDouble(orderDetails[3].trim().replace(" PHP", "").trim()); // Handling PHP format
                        paymentMethod = orderDetails[4].trim();
                        diningOption = orderDetails[5].trim();
                        date = orderDetails[7].trim();
                        time = orderDetails[8].trim();
                        double discount = Double.parseDouble(orderDetails[9].trim());  // Get the discount value from the CSV

                        // Calculate unit price and correct total price
                        double unitPrice = itemTotalPrice / quantity; // Calculate unit price
                        double totalItemPrice = unitPrice * quantity; // Correct total price

                        // Aggregate the prices, tax, and discount
                        totalPrice += totalItemPrice; // Accumulate total price for all items
                        totalDiscount += discount; // Sum up all discounts
                    }
                }
            }

            // If no matching order is found
            if (!orderFound) {
                System.out.println("\t\t\tOrder number not found.");
                return;
            }

            // Calculate tax and grand total after discount
             // 12% tax
            double grandTotal = totalPrice - totalDiscount;
            totalTax = grandTotal  * 0.12;
            grandTotal = grandTotal + totalTax;
            // Ask user for the amount paid and ensure it's sufficient
            double amountPaid = 0.0;
            while (amountPaid < grandTotal) {
                System.out.print("\t\t\tEnter the amount paid by the customer: ₱");
                
                // Check if the input is valid
                if(scanner.hasNextDouble()) {
                    amountPaid = scanner.nextDouble(); // Read the amount paid from user input

                    // Check if the entered amount is sufficient
                    if (amountPaid < grandTotal) {
                        double amountDue = grandTotal - amountPaid;
                        System.out.printf("\t\t\tAmount paid is insufficient! Amount Due: ₱%.2f%n", amountDue);
                    }
                } else {
                    System.out.println("\t\t\tInvalid input! Please enter a valid amount.");
                    scanner.next(); // Consume the invalid input
                }
            }

            // If valid amount is entered, print the receipt
            double amountDue = grandTotal - amountPaid;

            // Printing the formatted receipt
            System.out.println("\t\t\t==================================================");
            System.out.printf("\t\t\t\tORDER #%s%n", orderNumber);
            System.out.println("\t\t\t--------------------------------------------------");
            System.out.printf("\t\t\tDATE: %s%n", date);
            System.out.printf("\t\t\tTIME: %s%n", time);
            System.out.printf("\t\t\tDINING OPTION: %s%n", diningOption);
            System.out.printf("\t\t\tPAYMENT METHOD: %s%n", paymentMethod);
            System.out.println("\t\t\t--------------------------------------------------");
            System.out.println("\t\t\tQTY   ITEM             UNIT PRICE      TOTAL PRICE");
            System.out.println("\t\t\t--------------------------------------------------");

            // Re-read the file to print all items for the order
            try (BufferedReader br2 = new BufferedReader(new FileReader(csvFile))) {
                br2.readLine(); // Skip header again
                while ((line = br2.readLine()) != null) {
                    String[] orderDetails = line.split(cvsSplitBy);
                    if (orderDetails.length >= 10) {
                        String orderNum = orderDetails[0].trim();

                        if (orderNum.equalsIgnoreCase(orderNumber)) {
                            String itemName = orderDetails[1].trim();
                            int quantity = Integer.parseInt(orderDetails[2].trim());
                            double itemTotalPrice = Double.parseDouble(orderDetails[3].trim().replace(" PHP", "").trim());

                            // Recalculate unit price for each item
                            double unitPrice = itemTotalPrice / quantity; // Calculate unit price
                            double totalItemPrice = unitPrice * quantity; // Correct total price

                            System.out.printf("\t\t\t%-5d %-15s %-15s %-15s%n", quantity, itemName,
                                    String.format("₱%.2f", unitPrice), String.format("₱%.2f", totalItemPrice));
                        }
                    }
                }
            }

            System.out.println("\t\t\t--------------------------------------------------");
            System.out.printf("\t\t\t%-45s %-15s%n", "TOTAL:", String.format("₱%.2f", totalPrice));
            System.out.printf("\t\t\t%-45s %-15s%n", "TAX (12%):", String.format("₱%.2f", totalTax));
            System.out.printf("\t\t\t%-45s %-15s%n", "DISCOUNT:", String.format("₱%.2f", totalDiscount));
            System.out.printf("\t\t\t%-45s %-15s%n", "GRAND TOTAL:", String.format("₱%.2f", grandTotal));
            System.out.printf("\t\t\t%-45s %-15s%n", "AMOUNT PAID:", String.format("₱%.2f", amountPaid));
            System.out.printf("\t\t\t%-45s %-15s%n", "AMOUNT DUE:", String.format("₱%.2f", amountDue));
            System.out.println("\t\t\t==================================================");

        } catch (IOException e) {
            System.out.println("\t\t\tError reading the CSV file: " + e.getMessage());
        } 
    }
}