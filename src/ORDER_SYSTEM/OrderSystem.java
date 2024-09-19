package ORDER_SYSTEM;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class OrderSystem {
    static Scanner scanner = new Scanner(System.in);
    static Order[] orders = new Order[100];
    static OrderCount orderCount = new OrderCount(0);
    static int dineInOrTakeOut;

    public static void main(String[] args) {
    	int mainChoice;
    	BreakfastMenu breakfastMenu = new BreakfastMenu(orders, orderCount);
    	ChickenAndPlattersMenu chickenAndPlattersMenu = new ChickenAndPlattersMenu(orders, orderCount);
    	Burger burger = new Burger(orders, orderCount);
    	DrinksAndDessert drinksAndDessert = new  DrinksAndDessert(orders, orderCount);
    	Coffee coffee = new Coffee(orders, orderCount);
    	Fries fries = new Fries(orders, orderCount);
    	HandleMyOrder handleOrder = new HandleMyOrder(scanner, orders, orderCount, dineInOrTakeOut);
    	
    	displayDineInOrTakeOut(); 
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
                	breakfastMenu.displayBreakfastMenu();
                    break;
                case 2:
                	chickenAndPlattersMenu.displayChickenAndPlatters();
                    break;
                case 3:
                    burger.displayBurgerMenu();
                    break;
                case 4:
                	drinksAndDessert.displayDrinksAndDessertsMenu();
                    break;
                case 5:
                	coffee.displayCoffeeMenu();
                    break;
                case 6:
                    fries.displayFriesMenu();
                    break;
                case 7:
                    handleOrder.handleMyOrder();
                    break;
                case 0:
                    displayDineInOrTakeOut(); 
                    break;
                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        } while (true); 
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
                    dineInOrTakeOut = 1;
                    return;
                case 2:
                    dineInOrTakeOut = 2; 
                    return;
                case 3:
                    System.out.println("Exiting system.");
                    System.exit(0); 
                    break;
                default:
                    System.out.println("Invalid option. Please select again.");
            }
        }
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
        scanner.nextLine(); 
        scanner.nextLine(); 
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
