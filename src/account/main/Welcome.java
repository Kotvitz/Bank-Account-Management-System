package account.main;

import java.io.IOException;

public abstract class Welcome {
	public static void message() {
		System.out.print("\n\n\tWelcome to the bank account management system. To go to the main menu, press ENTER. ");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
