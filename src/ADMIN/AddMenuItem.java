package ADMIN;
import java.util.Scanner;

public class AddMenuItem {
	Scanner scanner = new Scanner(System.in);
	void AddMenu() {
		System.out.println("1. Enter the Item Name:");
		waitForEnter();
	}
	 public void waitForEnter() {
         System.out.print("Press Enter to continue...");
         scanner.nextLine(); 
         scanner.nextLine(); 
     }
}
