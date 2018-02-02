package account.management;

public class Account {
	private int ID;
	private int holderID;
	private String type;
	private double balance;
	
	public int getID() {
		return ID;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}

	public int getHolderID() {
		return holderID;
	}

	public void setHolderID(int holderID) {
		this.holderID = holderID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public Account(int ID, int holderID, String type, double balance) {
		this.ID = ID;
		this.holderID = holderID;
		this.type = type;
		this.balance = balance;
	}
	
	@Override
	public String toString() {
		return "["+ID+"] | "+holderID+" | "+type+" | "+balance;
	}
}
