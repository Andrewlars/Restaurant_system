package ADMIN;
import java.util.Scanner;

public class DeleteMenuItem {
	Scanner scanner = new Scanner(System.in);
	void DeleteMenu() {
		System.out.println("1. Enter the Item Name:");
	}
	public void waitForEnter() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine(); 
        scanner.nextLine(); 
    }
}
