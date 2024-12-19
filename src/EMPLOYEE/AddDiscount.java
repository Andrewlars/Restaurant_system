package EMPLOYEE;

import java.io.*;
import java.util.*;

public class AddDiscount {

    // Method to add a discount based on user status (Student/PWD)
    public static void addDiscount(String orderNumber) {
        String csvFile = "OrderRecords/order_summary.csv"; // Path to the CSV file
        String tempFile = "OrderRecords/order_summary_temp.csv"; // Temporary file to write updated data
        String line;
        String cvsSplitBy = ",";
        boolean orderFound = false;

        Scanner scanner = new Scanner(System.in);

        // Ask the user to input 1 for student or 2 for PWD
        System.out.println("\t\t\tEnter 1 for Student or 2 for PWD (Person with Disability): ");
        int statusOption = scanner.nextInt();
        double discountRate = 0.0;

        if (statusOption == 1) {
            discountRate = 0.05;  // 5% discount for students
        } else if (statusOption == 2) {
            discountRate = 0.10;  // 10% discount for PWDs
        } else {
            System.out.println("\t\t\tInvalid input. No discount will be applied.");
            return;  // Exit if invalid input
        }

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

            // Read the header line
            String header = br.readLine();
            if (header != null) {
                // Check if the header already contains a "Discount" column
                if (!header.contains("Discount")) {
                    bw.write(header + ", Discount\n"); // Write header with added Discount column if it doesn't exist
                } else {
                    bw.write(header + "\n"); // If "Discount" is already there, just write the header as it is
                }
            }

            // Process each line of the CSV file
            while ((line = br.readLine()) != null) {
                String[] orderDetails = line.split(cvsSplitBy);

                // Ensure there are at least 9 fields (before the Discount column)
                if (orderDetails.length >= 9) {
                    String orderNum = orderDetails[0].trim();

                    // Check if the order number matches
                    if (orderNum.equals(orderNumber)) {
                        // Apply discount calculation
                        double totalPrice = Double.parseDouble(orderDetails[3].trim().replace(" PHP", "").trim()); // Handle PHP format
                        double discount = totalPrice * discountRate;

                        // If there is already a discount value in the discount column, overwrite it
                        if (orderDetails.length > 9) {
                            // Rewrite the existing discount value
                            orderDetails[9] = String.format("%.2f", discount);
                        } else {
                            // If there's no discount column yet, add it
                            orderDetails = Arrays.copyOf(orderDetails, orderDetails.length + 1); // Increase array size by 1
                            orderDetails[9] = String.format("%.2f", discount);  // Add the discount
                        }

                        // Flag to indicate that the order was found and updated
                        orderFound = true;
                    }

                    // Write the updated line (with or without discount)
                    bw.write(String.join(",", orderDetails) + "\n");
                }
            }

            if (!orderFound) {
                System.out.println("\t\t\tOrder number not found.");
            } else {
                System.out.println("\t\t\tDiscount applied successfully.");
            }

        } catch (IOException e) {
            System.out.println("\t\t\tError reading or writing the CSV file: " + e.getMessage());
        }

        // Replace original CSV with updated data (from temporary file)
        File oldFile = new File(csvFile);
        File newFile = new File(tempFile);
        if (oldFile.delete()) {
            newFile.renameTo(oldFile);  // Rename the updated temporary file to original file
        } else {
            System.out.println("\t\t\tFailed to delete original file.");
        }
    }
}
