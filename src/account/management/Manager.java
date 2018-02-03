package account.management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import account.management.Account;
import account.management.Holder;

public class Manager {
	public static final String DRIVER = "org.sqlite.JDBC";
	public static final String DB_URL = "jdbc:sqlite:Bank.db";

	private Connection con;
	private Statement stat;

	public Manager() {
		try {
			Class.forName(Manager.DRIVER);
		} catch (ClassNotFoundException e) {
			System.err.println("No JDBC driver!");
			e.printStackTrace();
		}

		try {
			con = DriverManager.getConnection(DB_URL);
			stat = con.createStatement();
		} catch (SQLException e) {
			System.err.println("Problem with establishing the connection.");
			e.printStackTrace();
		}

		createTables();
	}

	public void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			System.err.println("Problem with closing the connection.");
			e.printStackTrace();
		}
	}

	public void createTables() {
		String createHolderTable = "CREATE TABLE IF NOT EXISTS Holder (holderID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
				+ "name VARCHAR(255), surname VARCHAR(255), pesel VARCHAR(255))";
		String createAccountTable = "CREATE TABLE IF NOT EXISTS Account (accountID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
				+ "holderID INT REFERENCES Holder(holderID), type VARCHAR(255), balance DOUBLE)";
		try {
			stat.execute(createHolderTable);
			stat.execute(createAccountTable);
		} catch (SQLException e) {
			System.err.println("The table couldn't be created.");
			e.printStackTrace();
		}
	}

	public void addHolder(String name, String surname, String pesel) {
		try {
			PreparedStatement prepStat = con.prepareStatement("INSERT INTO Holder VALUES (NULL, ?, ?, ?);");
			prepStat.setString(1, name);
			prepStat.setString(2, surname);
			prepStat.setString(3, pesel);
			prepStat.execute();
		} catch (SQLException e) {
			System.err.println("An error occurred while inserting a holder.");
			e.printStackTrace();
		}
	}

	public void createAccount(int holderID, String type, double balance) {
		try {
			PreparedStatement prepStat = con.prepareStatement("INSERT INTO Account VALUES (NULL, ?, ?, ?);");
			prepStat.setInt(1, holderID);
			prepStat.setString(2, type);
			prepStat.setDouble(3, balance);
			prepStat.execute();
		} catch (SQLException e) {
			System.err.println("An error occurred while inserting an account.");
			e.printStackTrace();
		}
	}

	public void createAccountForSpecificPerson(String pesel, String type, double balance) {
		try {
			int holderId;
			ResultSet result = stat.executeQuery("SELECT holderID FROM Holder WHERE pesel = " + pesel);
			while (result.next()) {
				holderId = result.getInt("holderID");
				createAccount(holderId, type, balance);
			}
		} catch (SQLException e) {
			System.err.println("An error occurred while assigning an account to a given person.");
			e.printStackTrace();
		}
	}

	public double getAccountBalance(int accountID) {
		double balance = 0;
		try {
			ResultSet result = stat.executeQuery("SELECT balance FROM Account WHERE accountID = " + accountID);
			while (result.next()) {
				balance = result.getDouble("balance");
			}
		} catch (SQLException e) {
			System.err.println("An error occurred while getting data from account.");
			e.printStackTrace();
		}
		return balance;
	}

	public double updateAccountBalance(int accountID, String operation, double amount) {
		double balance = 0;
		double curr_balance = 0;
		switch (operation) {
		case "withdraw":
			try {
				ResultSet result = stat.executeQuery("SELECT balance FROM Account WHERE accountID = " + accountID);
				while (result.next()) {
					balance = result.getDouble("balance");
					curr_balance = balance - amount;
				}
				try {
					PreparedStatement prepStat = con
							.prepareStatement("UPDATE Account SET balance = ? WHERE accountID = " + accountID);
					prepStat.setDouble(1, curr_balance);
					prepStat.execute();
				} catch (SQLException e) {
					System.err.println("An error occurred while updating an account balance.");
					e.printStackTrace();
				}
			} catch (SQLException e) {
				System.err.println("An error occurred while getting data from account.");
				e.printStackTrace();
			}
			break;
		case "deposit":
			try {
				ResultSet result = stat.executeQuery("SELECT balance FROM Account WHERE accountID = " + accountID);
				while (result.next()) {
					balance = result.getDouble("balance");
					curr_balance = balance + amount;
				}
				try {
					PreparedStatement prepStat = con
							.prepareStatement("UPDATE Account SET balance = ? WHERE accountID = " + accountID);
					prepStat.setDouble(1, curr_balance);
					prepStat.execute();
				} catch (SQLException e) {
					System.err.println("An error occurred while updating an account balance.");
					e.printStackTrace();
				}
			} catch (SQLException e) {
				System.err.println("An error occurred while getting data from account.");
				e.printStackTrace();
			}
			break;
		}
		return curr_balance;
	}

	public List<Holder> getAllHolders() {
		List<Holder> holders = new LinkedList<Holder>();
		try {
			ResultSet result = stat.executeQuery("SELECT * FROM Holder");
			int id;
			String name, surname, pesel;
			while (result.next()) {
				id = result.getInt("holderID");
				name = result.getString("name");
				surname = result.getString("surname");
				pesel = result.getString("pesel");
				holders.add(new Holder(id, name, surname, pesel));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return holders;
	}

	public List<Account> getAllAccounts() {
		List<Account> accounts = new LinkedList<Account>();
		try {
			ResultSet result = stat.executeQuery("SELECT * FROM Account");
			int id, holderId;
			String type;
			double balance;
			while (result.next()) {
				id = result.getInt("accountID");
				holderId = result.getInt("holderID");
				type = result.getString("type");
				balance = result.getDouble("balance");
				accounts.add(new Account(id, holderId, type, balance));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return accounts;
	}

	public void modifyHolderData(int holderID, String newName, String newSurname, String newPesel) {
		try {
			PreparedStatement prepStat = con
					.prepareStatement("UPDATE Holder SET name = ?, surname = ?, pesel = ? WHERE holderID = " + holderID);
			prepStat.setString(1, newName);
			prepStat.setString(2, newSurname);
			prepStat.setString(3, newPesel);
			prepStat.execute();
		} catch (SQLException e) {
			System.err.println("An error occurred while modifying an holder data.");
			e.printStackTrace();
		}
	}

	public void closeAccount(int accountID) {
		try {
			PreparedStatement prepStat = con.prepareStatement("DELETE FROM Account WHERE accountID = " + accountID);
			prepStat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
