import java.util.Scanner;

/**
 * Just like last time, the ATM class is responsible for managing all
 * of the user interaction. This means login procedures, displaying the
 * menu, and responding to menu selections. In the enhanced version, the
 * ATM class will have the added responsibility of interfacing with the
 * Database class to write and read information to and from the database.
 * 
 * Most of the functionality for this class should have already been
 * implemented last time. You can always reference my Github repository
 * for inspiration (https://github.com/rwilson-ucvts/java-sample-atm).
 */

public class ATM {
	
	private Scanner in;
	private BankAccount account;
	private Database database;
	
	//Constructor
	public ATM(Database database) {
		this.database = database;
	}
	
	//Getter
	public BankAccount getBankAccount() {
		return account;
	}
	public Database getDatabase() {
		return database;
	}
	
	//Setter
	public void setBankAccount(BankAccount account) {
		this.account = account;
	}
	public void setDatabase(Database database) {
		this.database = database;
	}
	//Methods
	public void interactATM() {
		in = new Scanner(System.in);
		displayMainMenu();
	}
	public void displayMainMenu() {
		System.out.println("Open account(1)\nLogin(2)\nQuit(3)");
		int selection = in.nextInt();
		switch(selection) {
		case 1:
			openAccount();
			break;
		case 2:
			login();
			break;
		case 3:
			break;
		default:
			System.out.println("Invalid Selection");
			displayMainMenu();
			break;
		}
		
	}
	public void openAccount() {
		System.out.print("First Name: ");
		String firstName = in.nextLine();
		System.out.print("\nLast Name: ");
		String lastName = in.nextLine();
		System.out.print("\nPin: ");
		int pin = in.nextInt();
		System.out.print("\nBirth Date: ");
		String birthDate = in.nextLine();
		System.out.print("\nPhone Number: ");
		int phoneNumber = in.nextInt();
		System.out.print("\nAddress: ");
		String address = in.nextLine();
		System.out.print("\nCity: ");
		String city = in.nextLine();
		System.out.print("\nState: ");
		String state = in.nextLine();
		System.out.print("\nPostal Code: ");
		int postalCode = in.nextInt();
		//getDatabase().createAccount(firstName, lastName, pin, birthDate, phoneNumber, address, city, state, postalCode);
		//Append database with information
		//BankAccount newAccount = new BankAccount((long)Math.random(), new User(firstName, lastName, pin, birthdate, phoneNumber, address, city, state, postalCode), 0);
	}
	public void login() {
		System.out.print("Account number: ");
		long accountNumber = in.nextLong();
		System.out.print("\nPin: ");
		int pin = in.nextInt();
		if(getDatabase().accountValidate(accountNumber, pin) == true) {
			account = getDatabase().getAccount(getDatabase().getCurrentAccount());
			displaySubMenu();
		}
		//DO ELSE LATER
	}
	public void displaySubMenu() {
		System.out.println("Deposit funds(1)\nWithdraw funds(2)\nTransfer funds(3)\nView balance(4)\nView personal information(5)\nUpdate personal information(6)\nClose account(7)\nLogout(8)");
		int selection = in.nextInt();
		switch(selection) {
		case 1:
			depositMenu();
			break;
		case 2:
			withdrawMenu();
			break;
		case 3:
			transferMenu();
			break;
		case 4:
			viewBalanceMenu();
			break;
		case 5:
			viewInformation();
			break;
		case 6:
			updateInformation();
			break;
		case 7:
			closeAccount();
		case 8:
			displayMainMenu();
		default:
			System.out.println("Invalid Selection");
			displaySubMenu();
			break;
		}
	}
	private void viewBalanceMenu() {
		System.out.println("\n\nBalance Amount: " + account.getBalance());
		System.out.print("\nExit to menu screen(2)\nMake a selection: ");
		int menuSelection = in.nextInt();
		displaySubMenu();
	}
	public void depositMenu() {
		
	}
	public void withdrawMenu() {
		
	}
	public void transferMenu() {
		
	}
	public void viewInformation() {
		
	}
	public void updateInformation() {
		
	}
	public void closeAccount() {
		
	}
	
}