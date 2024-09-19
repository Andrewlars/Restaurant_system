package ADMIN;

import java.util.Scanner;
class AdminMenu {
    private Scanner scanner;
    private AdminManager manager;
    AddMenuItem addmenuitem = new AddMenuItem();
    UpdateMenuItem updatemenuitem = new UpdateMenuItem();
    DeleteMenuItem deletemenuitem = new DeleteMenuItem();

    public AdminMenu(AdminManager manager) {
        this.manager = manager;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
    	
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add menu item");
            System.out.println("2. Update menu item");
            System.out.println("3. Delete menu item");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                	addmenuitem.AddMenu();
                    break;
                case 2:
                	updatemenuitem.UpdateMenu();
                    break;
                case 3:
                	deletemenuitem.DeleteMenu();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
