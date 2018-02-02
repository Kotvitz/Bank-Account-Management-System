package account.main;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import account.management.*;

public class Menu {
	
	public static void main(String[] args) {
		Manager mng = new Manager();
		int choice, ID;
		double amount;
		Scanner s = new Scanner(System.in);
		Welcome.message();
		do {
			System.out.println("\n\n\tMAIN MENU");
			System.out.println("\n\t1. CREATE NEW ACCOUNT");
			System.out.println("\n\t2. CHECK ACCOUNT BALANCE");
			System.out.println("\n\t3. CASH WITHDRAWAL");
			System.out.println("\n\t4. CASH DEPOSIT");
			System.out.println("\n\t5. ALL ACCOUNT AND HOLDER LIST");
			System.out.println("\n\t6. MODIFY AN ACCOUNT");
			System.out.println("\n\t7. CLOSE AN ACCOUNT");
			System.out.println("\n\t8. EXIT");
			System.out.print("\n\tPlease choose an option (1-8): ");
			choice = s.nextInt();
			switch (choice) {
			case 1:
				String name, surname, pesel, type;
				double balance;
				System.out.println("\nFirst enter the holder's details.");
				System.out.print("\nNAME: ");
				name = s.next();
				System.out.print("\nSURNAME: ");
				surname = s.next();
				System.out.print("\nPESEL: ");
				pesel = s.next();
				mng.addHolder(name, surname, pesel);
				System.out.println("\nAnd now enter account information.");
				System.out.print("\nTYPE: ");
				type = s.next();
				System.out.print("\nBALANCE: ");
				balance = s.nextDouble();
				mng.createAccountForSpecificPerson(pesel, type, balance);
				break;
			case 2:
				System.out.print("\nEnter the account ID: ");
				ID = s.nextInt();
				System.out.println("\nBalance: "+mng.getAccountBalance(ID));
				System.out.print("\nPress ENTER to continue... ");
				try {
					System.in.read();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 3:
				System.out.print("\nEnter the account ID: ");
				ID = s.nextInt();
				System.out.print("\nEnter an amount you want to withdraw: ");
				amount = s.nextDouble();
				System.out.println("\nYou have withdrawn the following amount: "+amount);
				System.out.println("\nCurrent balance: "+mng.updateAccountBalance(ID, "withdraw", amount));
				System.out.print("\nPress ENTER to continue... ");
				try {
					System.in.read();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 4:
				System.out.print("\nEnter the account ID: ");
				ID = s.nextInt();
				System.out.print("\nEnter an amount you want to deposit: ");
				amount = s.nextDouble();
				System.out.println("\nYou have deposited the following amount: "+amount);
				System.out.println("\nCurrent balance: "+mng.updateAccountBalance(ID, "deposit", amount));
				System.out.print("\nPress ENTER to continue... ");
				try {
					System.in.read();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 5:
				List<Account> accounts = mng.getAllAccounts();
				List<Holder> holders = mng.getAllHolders();
				
				System.out.println("\nAccounts: ");
				System.out.println("ID | HOLDER_ID | TYPE | BALANCE");
		        for(Account a: accounts)
		            System.out.println(a);
		       
		        System.out.print("\n");
		        
		        System.out.println("Holders:");
		        System.out.println("ID | NAME | SURNAME | PESEL");
		        for(Holder h: holders)
		            System.out.println(h);
		        
		        System.out.print("\nPress ENTER to continue... ");
				try {
					System.in.read();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 6:
				break;
			case 7:
				System.out.print("\nEnter the account ID: ");
				ID = s.nextInt();
				mng.closeAccount(ID);
				System.out.println("\nThis account has been closed.");
				System.out.print("\nPress ENTER to continue... ");
				try {
					System.in.read();
				} catch (IOException e) {
					e.printStackTrace();
				}
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
