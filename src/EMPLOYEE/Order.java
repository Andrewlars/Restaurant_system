package EMPLOYEE;

public class Order {
    private String orderNumber;
    private String itemName;
    private int quantity;
    private double totalAmount;
    private double discount; // Added discount field
    private String paymentMethod;
    private String diningOption;
    private String date;

    // Constructor with discount
    public Order(String orderNumber, String itemName, int quantity, double totalAmount, double discount, String paymentMethod, String diningOption, String date) {
        this.orderNumber = orderNumber;
        this.itemName = itemName;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.discount = discount;
        this.paymentMethod = paymentMethod;
        this.diningOption = diningOption;
        this.date = date;
    }

    // Getter for quantity
    public int getQuantity() {
        return quantity;
    }

    // Setter for quantity
    public void setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
            // Recalculate total amount whenever quantity is modified
            this.totalAmount = calculateTotalAmount(); // Use the helper method to update the totalAmount
        } else {
            System.out.println("Quantity must be greater than zero.");
        }
    }

    // Getter for total price (totalAmount)
    public double getTotalPrice() {
        return totalAmount;
    }

    // Setter for total price
    public void setTotalPrice(double totalAmount) {
        if (totalAmount >= 0) {
            this.totalAmount = totalAmount;
            // Recalculate quantity based on new totalAmount and unitPrice
            if (getUnitPrice() > 0) {
                this.quantity = (int) (totalAmount / getUnitPrice()); // Update quantity based on totalAmount
            }
        } else {
            System.out.println("Total price cannot be negative.");
        }
    }

    // Helper method to calculate total amount
    private double calculateTotalAmount() {
        return getUnitPrice() * quantity; // Calculate total amount based on unit price and quantity
    }

    // Method to get the unit price (unitPrice = totalAmount / quantity)
    public double getUnitPrice() {
        if (quantity != 0) {
            return totalAmount / quantity;  // Avoid division by zero
        } else {
            return 0.0;  // Return 0 if quantity is zero
        }
    }

    // Getter for itemName
    public String getItemName() {
        return itemName;
    }

    // Getter for paymentMethod
    public String getPaymentMethod() {
        return paymentMethod;
    }

    // Getter for diningOption
    public String getDiningOption() {
        return diningOption;
    }

    // Getter for date
    public String getDate() {
        return date;
    }

    // Getter for discount
    public double getDiscount() {
        return discount;
    }

    // Setter for discount
    public void setDiscount(double discount) {
        if (discount >= 0) {
            this.discount = discount;
            // Recalculate the total after discount
            this.totalAmount = getTotalPriceAfterDiscount();
        } else {
            System.out.println("Discount cannot be negative.");
        }
    }

    // Method to calculate total price after applying discount
    public double getTotalPriceAfterDiscount() {
        return totalAmount - discount;
    }

    // Optional: ToString for easy printing of order details
    @Override
    public String toString() {
        return "Order #" + orderNumber + " - " + itemName + ": " + quantity + " x " + String.format("%.2f PHP", getUnitPrice()) + " = " + String.format("%.2f PHP", getTotalPrice());
    }

    // Getter for orderNumber
    public Object getOrderNumber() {
        return orderNumber;
    }
}