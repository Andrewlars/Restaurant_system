package ADMIN;

import java.util.Scanner;

public class AdminSystem {
    public static void main(String[] args) {
        int maxAdmins = 10; 
        AdminManager adminManager = new AdminManager(maxAdmins);
        AdminMenu adminMenu = new AdminMenu(adminManager);
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String regUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String regPassword = scanner.nextLine();
                    if (adminManager.register(regUsername, regPassword)) {
                        System.out.println("Registration successful!");
                    } else {
                        System.out.println("Username already exists or registration failed.");
                    }
                    break;

                case 2:
                    System.out.print("Enter username: ");
                    String loginUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();
                    if (adminManager.login(loginUsername, loginPassword)) {
                        System.out.println("Login successful!");
                        adminMenu.displayMenu();
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                    break;

                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
