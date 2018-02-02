package account.management;

public class Holder {
	private int ID;
	private String name;
	private String surname;
	private String pesel;
	
	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	public Holder(int ID, String name, String surname, String pesel) {
		this.ID = ID;
		this.name = name;
		this.surname = surname;
		this.pesel = pesel;
	}
	
	@Override
	public String toString() {
		return "["+ID+"] | "+name+" | "+surname+" | "+pesel;
	}
}
