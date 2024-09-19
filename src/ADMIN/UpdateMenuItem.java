package ADMIN;
import java.util.Scanner;

public class UpdateMenuItem {
	Scanner scanner = new Scanner(System.in);
	void UpdateMenu() {
		System.out.println("1. Enter the Item Name:");
	}
	public void waitForEnter() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine(); // Consume the leftover newline
        scanner.nextLine(); // Wait for the user to press Enter
    }
}
