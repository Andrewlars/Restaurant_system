package EMPLOYEE;

import ORDER_SYSTEM.MainOrderSystem;
import java.util.InputMismatchException;
import java.util.Scanner;
import ORDER_SYSTEM.MenuItem;
import ORDER_SYSTEM.MainOrderSystem;
import ORDER_SYSTEM.Order;
import ORDER_SYSTEM.BreakfastMenu;
import ORDER_SYSTEM.BurgerMenu;
import ORDER_SYSTEM.ChickenAndPlattersMenu;
import ORDER_SYSTEM.DrinksAndDessertMenu;
import ORDER_SYSTEM.FriesMenu;
import ORDER_SYSTEM.CoffeeMenu;
import ORDER_SYSTEM.HandleMyOrder;

public class EmployeeMenu {
	
    // Menu objects for different categories
    ChickenAndPlattersMenu chickenMenu = new ChickenAndPlattersMenu();
    BreakfastMenu breakfastMenu = new BreakfastMenu();
    DrinksAndDessertMenu drinksAndDessertMenu = new DrinksAndDessertMenu();
    BurgerMenu burgerMenu = new BurgerMenu();
    CoffeeMenu coffeeMenu = new CoffeeMenu();
    FriesMenu friesMenu = new FriesMenu();
    HandleMyOrder handleOrder = new HandleMyOrder();
    
    private Scanner scanner;

    public EmployeeMenu() {
        scanner = new Scanner(System.in);
    }

    // Start method to display the employee menu and handle actions
    public boolean start() {
    	boolean running = true;

        while (running) {
        	MainOrderSystem.clearScreen();  // Clear screen for each new menu
			System.out.println("\t\t\t\t\t\tEMPLOYEE MENU");
			System.out.println("\t\t\t=====================================================================");
			System.out.println("\t\t\t|                        [1] Manage Orders                          |");
			System.out.println("\t\t\t|                        [2] Status of orders                       |");
			System.out.println("\t\t\t|                        [3] Manual order                           |");
			System.out.println("\t\t\t|                        [4] Order logs                             |");
			System.out.println("\t\t\t|                        [5] Exit to Employee                       |");
			System.out.println("\t\t\t=====================================================================\n\n");
			clearScreenBottom();

			try {
			    System.out.print("\t\t\tEnter: ");
			    int choice = scanner.nextInt();
			
			    switch (choice) {
			        case 1:
			            TicketOrder.handleOrder(); // Handle orders - Method should be defined in another class
			            break;
			        case 2:
			            ViewPendingOrders.displayOrdersByStatus(); // Display order status - Method should be defined in another class
			            break;
			        case 3:
			        	DiningOption.showDiningMenu(scanner, chickenMenu, breakfastMenu, drinksAndDessertMenu, burgerMenu, coffeeMenu, friesMenu, handleOrder); // Display dining menu
			            break;
			        case 4:
			        	OrderLogs.displayOrderLogs(); // Show order logs - Method should be defined in another class
			            break;
			        case 5:
			        	scanner.nextLine();
			            MainOrderSystem.getRoleChoice(scanner); // Clear the screen before exiting
			            return false;  // Exit and return to previous screen (e.g., employee role selection)
			        default:
			            System.out.println("\t\t\tInvalid option. Please try again.");
			    }
			} catch (InputMismatchException e) {
			    System.out.println("\t\t\tInvalid input. Please enter a number between 1 and 5.");
			    scanner.nextLine(); // Clear the invalid input from the scanner
			    
			    System.out.print("\n\t\t\tPress ENTER to continue...");
			    scanner.nextLine();
			}
        }
        
        return true;  // If the loop ends, the user has logged out
    }
    
    // Method to clear the screen (top)
    public static void clearScreen() {
        for (int i = 0; i < 5; i++) {  // Print 50 newlines
            System.out.println();
        }   
    }
    
    // Method to clear the screen (bottom)
    public static void clearScreenBottom() {
        for (int i = 0; i < 4; i++) {  // Print 40 newlines
            System.out.println();
        }   
    }
    
    // Main method to run the application
//    public static void main(String[] args) {
//        // Create an instance of EmployeeMenu
//        EmployeeMenu employeeMenu = new EmployeeMenu();
//        
//        // Start the employee menu
//        boolean loggedIn = employeeMenu.start();
//        
//        if (!loggedIn) {
//            System.out.println("\t\t\tExiting the Employee Menu. Returning to role selection...");
//        }
//    }
}
