package ORDER_SYSTEM;

import EMPLOYEE.Login;
import EMPLOYEE.EmployeeMenu;
import ADMIN.AdminSystem;
import ADMIN.AdminAuthForLogout;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainOrderSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Shared scanner

        // Initialize menu objects
        ChickenAndPlattersMenu chickenMenu = new ChickenAndPlattersMenu();
        BreakfastMenu breakfastMenu = new BreakfastMenu();
        DrinksAndDessertMenu drinksAndDessertMenu = new DrinksAndDessertMenu();
        BurgerMenu burgerMenu = new BurgerMenu();
        CoffeeMenu coffeeMenu = new CoffeeMenu();
        FriesMenu friesMenu = new FriesMenu();

        // Initialize order management
        HandleMyOrder handleOrder = new HandleMyOrder();

        // Start with the Dine-In/Take-Out menu
        boolean exitProgram = showDiningMenu(scanner, chickenMenu, breakfastMenu, drinksAndDessertMenu, burgerMenu, coffeeMenu, friesMenu, handleOrder);

        // Loop until user decides to exit
        while (!exitProgram) {
            int roleChoice = getRoleChoice(scanner);

            if (roleChoice == 0) {
                System.out.println("\n\t\t\tExiting the program. Goodbye!");
                System.exit(0);
            }

            handleRoleSelection(roleChoice, scanner, chickenMenu, breakfastMenu, drinksAndDessertMenu, burgerMenu, coffeeMenu, friesMenu, handleOrder);
            // Show dining menu again after role actions
            exitProgram = showDiningMenu(scanner, chickenMenu, breakfastMenu, drinksAndDessertMenu, burgerMenu, coffeeMenu, friesMenu, handleOrder);
        }
    }

    // Method to handle the Dining Menu
    private static boolean showDiningMenu(
            Scanner scanner,
            ChickenAndPlattersMenu chickenMenu,
            BreakfastMenu breakfastMenu,
            DrinksAndDessertMenu drinksAndDessertMenu,
            BurgerMenu burgerMenu,
            CoffeeMenu coffeeMenu,
            FriesMenu friesMenu,
            HandleMyOrder handleOrder
    ) {
        boolean loggedIn = true;

        while (loggedIn) {
        	clearScreen();
            System.out.println("\t\t\t\t\t\tDINING OPTION");
            System.out.println("\t\t\t===================================================================");
            System.out.println("\t\t\t|                        [1] Dine In                              |");
            System.out.println("\t\t\t|                        [2] Take Out                             |");
            System.out.println("\t\t\t|                        [3] Logout                               |");
            System.out.println("\t\t\t===================================================================\n\n");

            System.out.print("\t\t\tEnter: ");
            int diningChoice = getValidatedInput(scanner, 1, 3);

            switch (diningChoice) {
                case 1:
                    System.out.println("\t\t\tYou selected Dine In.");
                    HandleMyOrder.setDiningOption("Dine In");
                    displayMainMenu(scanner, chickenMenu, breakfastMenu, drinksAndDessertMenu, burgerMenu, coffeeMenu, friesMenu, handleOrder);
                    break;
                case 2:
                    System.out.println("\t\t\tYou selected Take Out.");
                    HandleMyOrder.setDiningOption("Take Out");
                    displayMainMenu(scanner, chickenMenu, breakfastMenu, drinksAndDessertMenu, burgerMenu, coffeeMenu, friesMenu, handleOrder);
                    break;
                case 3:
                    System.out.println("\t\t\tLogging out...");
                    AdminAuthForLogout adminAuth = new AdminAuthForLogout();
                    if (adminAuth.login(scanner)) {
                        System.out.println("\t\t\tLogout successful.");
                        //timer
                        for (int i = 3; i > 0; i--) { // Countdown 
                            System.out.println("\t\t\t" + i + " Redirecting to role selection...");
                            try {
                                Thread.sleep(1000); // Wait for 1 second
                            } catch (InterruptedException e) {
                                System.out.println("Timer was interrupted!");
                            }
                        }
                        //timer
                        return false; // Return to role selection
                    } else {
                        System.out.println("\t\t\tAuthentication failed.");
                        //timer
                        for (int i = 3; i > 0; i--) { // Countdown 
                            System.out.println("\t\t\t" + i + " Returning to dining menu...");
                            try {
                                Thread.sleep(1000); // Wait for 1 second
                            } catch (InterruptedException e) {
                                System.out.println("Timer was interrupted!");
                            }
                        }
                        //timer
                    }
                    break;
            }
        }

        return true; // Stay in the program
    }

    // Method to handle role selection
    private static void handleRoleSelection(
            int roleChoice,
            Scanner scanner,
            ChickenAndPlattersMenu chickenMenu,
            BreakfastMenu breakfastMenu,
            DrinksAndDessertMenu drinksAndDessertMenu,
            BurgerMenu burgerMenu,
            CoffeeMenu coffeeMenu,
            FriesMenu friesMenu,
            HandleMyOrder handleOrder
    ) {
        switch (roleChoice) {
            case 1:
                AdminSystem adminSystem = new AdminSystem();
                adminSystem.start();
                break;
            case 2:
                handleEmployeeLogin(scanner);
                break;
            case 3:
                System.out.println("\t\t\tReturning to dining options...");
                break;
            case 0:
            	System.exit(0);
            default:
                System.out.println("\t\t\tInvalid role choice. Please try again.");
        }
    }

    // Method to handle employee login
    private static void handleEmployeeLogin(Scanner scanner) {
        Login login = new Login();
        boolean loginSuccess = false;

        while (!loginSuccess) {
            System.out.print("\t\t\tEnter Employee ID: ");
            String employeeID = scanner.nextLine();
            System.out.print("\t\t\tEnter password: ");
            String employeePassword = scanner.nextLine();

            if (login.handleLogin(employeeID, employeePassword)) {
                loginSuccess = true;
                EmployeeMenu employeeMenu = new EmployeeMenu();
                employeeMenu.start();
            } else {
                System.out.println("\t\t\tLogin failed. Returning to role selection...");
                return;
            }
        }
    }

    // Method to display the main menu for customers
    private static void displayMainMenu(
            Scanner scanner,
            ChickenAndPlattersMenu chickenMenu,
            BreakfastMenu breakfastMenu,
            DrinksAndDessertMenu drinksAndDessertMenu,
            BurgerMenu burgerMenu,
            CoffeeMenu coffeeMenu,
            FriesMenu friesMenu,
            HandleMyOrder handleOrder
    ) {
        boolean inMainMenu = true;

        while (inMainMenu) {
            clearScreen();
            System.out.println("\t\t\t\t\t\tWELCOME TO THE RESTAURANT!");
            System.out.println("\t\t\t==================================================================");
            System.out.println("\t\t\t|                        [1] Breakfast Menu                      |");
            System.out.println("\t\t\t|                        [2] Chicken And Platters                |");
            System.out.println("\t\t\t|                        [3] Burger Menu                         |");
            System.out.println("\t\t\t|                        [4] Drinks & Desserts Menu              |");
            System.out.println("\t\t\t|                        [5] Coffee Menu                         |");
            System.out.println("\t\t\t|                        [6] Fries Menu                          |");
            System.out.println("\t\t\t|                        [7] My Order                            |");
            System.out.println("\t\t\t|                        [0] Go Back                             |");
            System.out.println("\t\t\t==================================================================\n\n");

            System.out.print("\t\t\tEnter: ");
            int choice = getValidatedInput(scanner, 0, 7);

            switch (choice) {
                case 0:
                    System.out.println("\t\t\tGoing back to dining options...");
                    inMainMenu = false;
                    break;
                case 1:
                    breakfastMenu.displayMenu(scanner, handleOrder);
                    break;
                case 2:
                    chickenMenu.displayMenu(scanner, handleOrder);
                    break;
                case 3:
                    burgerMenu.displayMenu(scanner, handleOrder);
                    break;
                case 4:
                    drinksAndDessertMenu.displayMenu(scanner, handleOrder);
                    break;
                case 5:
                    coffeeMenu.displayMenu(scanner, handleOrder);
                    break;
                case 6:
                    friesMenu.displayMenu(scanner, handleOrder);
                    break;
                case 7:
                    handleOrder.showOrderSummary();
                    break;
                default:
                    System.out.println("\t\t\tInvalid choice. Please try again.");
            }
        }
    }

    // Method to get a validated input
    private static int getValidatedInput(Scanner scanner, int min, int max) {
        int input = -1;
        boolean valid = false;

        while (!valid) {
            try {
                input = Integer.parseInt(scanner.nextLine().trim());
                if (input >= min && input <= max) {
                    valid = true;
                } else {
                    System.out.printf("\t\t\tInvalid input. Please enter a number between %d and %d.\n", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.println("\t\t\tInvalid input. Please enter a valid number.");
            }
        }
        return input;
    }

    public static int getRoleChoice(Scanner scanner) {
        clearScreen();
        System.out.println("\t\t\t\t\t\tUSER TYPE SELECTION");
        System.out.println("\t\t\t===================================================================");
        System.out.println("\t\t\t|                        [1] Admin                                |");
        System.out.println("\t\t\t|                        [2] Employee                             |");
        System.out.println("\t\t\t|                        [3] Customer                             |");
        System.out.println("\t\t\t|                        [0] Exit                                 |");
        System.out.println("\t\t\t===================================================================\n\n");
        System.out.print("\t\t\tEnter: ");
        return getValidatedInput(scanner, 0, 3);
    }

    public static void clearScreen() {
        for (int i = 0; i < 5; i++) {
            System.out.println();
        }
    }
    
    public static void clearScreenBottom() {
        for (int i = 0; i < 3; i++) {  // Print 50 newlines
            System.out.println();
        }   
    }
}
