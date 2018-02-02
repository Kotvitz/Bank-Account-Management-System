package account.main;

import java.util.Scanner;

import account.management.*;

public class Menu {
	
	public static void main(String[] args) {
		Manager mng = new Manager();
		int choice;
		Scanner s = new Scanner(System.in);
		Welcome.message();
		do {
			System.out.println("\n\n\tMAIN MENU");
			System.out.println("\n\t1. CREATE NEW ACCOUNT");
			System.out.println("\n\t2. ACCOUNT BALANCE");
			System.out.println("\n\t3. CASH WITHDRAWAL");
			System.out.println("\n\t4. CASH PAYMENT");
			System.out.println("\n\t5. ALL ACCOUNT HOLDER LIST");
			System.out.println("\n\t6. CLOSE AN ACCOUNT");
			System.out.println("\n\t7. MODIFY AN ACCOUNT");
			System.out.println("\n\t8. EXIT");
			System.out.print("\n\tPlease choose an option (1-8): ");
			choice = s.nextInt();
			switch (choice) {
			case 1:
				String name, surname, pesel;
				System.out.println("\nFirst enter the holder's details.");
				System.out.print("\nNAME: ");
				name = s.next();
				System.out.print("\nSURNAME: ");
				surname = s.next();
				System.out.print("\nPESEL: ");
				pesel = s.next();
				mng.addHolder(name, surname, pesel);
				String type;
				double balance;
				System.out.println("\nAnd now enter account information.");
				System.out.print("\nTYPE: ");
				type = s.next();
				System.out.print("\nBALANCE: ");
				balance = s.nextDouble();
				mng.createAccountForSpecificPerson(pesel, type, balance);
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				break;
			case 8:
				System.out.println("\n\tThanks for using account management system. Have a nice day! :)");
				break;
			default:
				System.out.println("\n\tThere is no such option!");
				break;
			}
		} while (choice != 8);
		mng.closeConnection();
	}
}
