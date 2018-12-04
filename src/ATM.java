import java.io.IOException;

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
	public void interactATM() throws IOException {
		in = new Scanner(System.in);
		displayMainMenu();
	}
	public void displayMainMenu() throws IOException {
		System.out.print("Open account(1)\nLogin(2)\nQuit(3)\n\nMake A Selection: ");
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
	public void openAccount() throws IOException {
		System.out.print("\n\n\n\n\n");
		System.out.print("First Name: ");
		in.nextLine();
		String firstName = in.nextLine();
		System.out.print("\nLast Name: ");
		String lastName = in.nextLine();
		System.out.print("\nPin: ");
		String pin = in.nextLine();
		System.out.print("\nBirth Date: ");
		String birthDate = in.nextLine();
		System.out.print("\nPhone Number: ");
		String phoneNumber = in.nextLine();
		System.out.print("\nAddress: ");
		String address = in.nextLine();
		System.out.print("\nCity: ");
		String city = in.nextLine();
		System.out.print("\nState: ");
		String state = in.nextLine();
		System.out.print("\nPostal Code: ");
		String postalCode = in.nextLine();
		getDatabase().writeLine(firstName, lastName, pin, birthDate, phoneNumber, address, city, state, postalCode);
		database = new Database();
		System.out.println("\n\nAccount Successfully Created\n\n\n");
		displayMainMenu();
		//getDatabase().createAccount(firstName, lastName, pin, birthDate, phoneNumber, address, city, state, postalCode);
		//Append database with information
		//BankAccount newAccount = new BankAccount((long)Math.random(), new User(firstName, lastName, pin, birthdate, phoneNumber, address, city, state, postalCode), 0);
	}
	public void login() throws IOException {
		System.out.print("\n\n\n\n\n");
		System.out.print("Account number: ");
		long accountNumber = in.nextLong();
		System.out.print("\nPin: ");
		int pin = in.nextInt();
		if(getDatabase().accountValidate(accountNumber, Long.toString(pin)) == true) {
			account = getDatabase().getAccount(getDatabase().getCurrentAccount());
			System.out.println("Welcome, " + getBankAccount().getUser().getFirstName());
			displaySubMenu();
		}
		//DO ELSE LATER
	}
	public void displaySubMenu() throws IOException {
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
	private void viewBalanceMenu() throws IOException {
		System.out.println("\n\nBalance Amount: " + account.getBalance());
		System.out.print("\nExit to menu screen(2)\nMake a selection: ");
		int menuSelection = in.nextInt();
		displaySubMenu();
	}
	public void depositMenu() throws IOException {
		int returnCode = 1;
		double depositAmount;
		String oldBalance = String.format("%1$-15s",Double.toString(getBankAccount().getBalance()));
		System.out.print("\n\nDeposit Amount: ");
		do{
			if(returnCode == 0) {
				System.out.println("\nPlease enter a valid deposit amount: ");
			}
			depositAmount = in.nextDouble();
			returnCode = account.deposit(depositAmount);
		}while(returnCode == 0);
		
		/*String oldLine = Long.toString(getBankAccount().getAccountNumber()) +
				 		getBankAccount().getUser().getPin() +
				 		oldBalance +
				 		String.format("%1$-20s", getBankAccount().getUser().getLastName()) + 
				 		String.format("%1$-15s", getBankAccount().getUser().getFirstName()) +
				 		getBankAccount().getUser().getBirthDate() + 
				 		getBankAccount().getUser().getPhoneNumber() + 
				 		String.format("%1$-30s", getBankAccount().getUser().getAddress()) + 
				 		String.format("%1$-30s", getBankAccount().getUser().getCity()) + 
				 		getBankAccount().getUser().getState() + 
				 		getBankAccount().getUser().getPostalCode() + 
				 		"Y";*/
		String newLine = Long.toString(getBankAccount().getAccountNumber()) +
		 				getBankAccount().getUser().getPin() +
		 				String.format("%1$-15s", Double.toString(getBankAccount().getBalance())) +
		 				String.format("%1$-20s", getBankAccount().getUser().getLastName()) + 
		 				String.format("%1$-15s", getBankAccount().getUser().getFirstName()) +
		 				getBankAccount().getUser().getBirthDate() + 
		 				getBankAccount().getUser().getPhoneNumber() + 
		 				" " + 
		 				String.format("%1$-30s", getBankAccount().getUser().getAddress()) + 
		 				String.format("%1$-30s", getBankAccount().getUser().getCity()) + 
		 				getBankAccount().getUser().getState() + 
		 				getBankAccount().getUser().getPostalCode() + 
		 				"Y";
		getDatabase().updateDatabase(newLine/*, oldLine*/);
		int currentAccount = getDatabase().getCurrentAccount();
		database = new Database();
		getDatabase().setCurrentAccount(currentAccount);
		System.out.println("\n$" + depositAmount + " has been deposited.");
		System.out.print("\nMake another deposit(1)\nExit to menu screen(2)\n\nMake a selection: ");
		int menuSelection = in.nextInt();
		if(menuSelection == 1) {
			depositMenu();
		}
		else {
			displaySubMenu();
		}
	}
	public void withdrawMenu() throws IOException {
		int returnCode = 2;
		long withdrawAmount;
		System.out.print("\n\nWithdraw Amount: ");
		do{
			if(returnCode == 1) {
				System.out.println("\nPlease enter a valid withdraw amount: ");
			}
			else if(returnCode == 0) {
				System.out.println("\nWithdraw amount is too high: ");
			}
			withdrawAmount = in.nextLong();
			returnCode = account.withdraw(withdrawAmount);
		}while(returnCode != 2);
		
		System.out.println("\n$" + withdrawAmount + " has been withdrawn.");
		System.out.print("\nMake another withdraw(1)\nExit to menu screen(2)\n\nMake a selection: ");
		Double menuSelection = in.nextDouble();
		if(menuSelection == 1) {
			withdrawMenu();
		}
		else {
			displaySubMenu();
		}
	}
	public void transferMenu() {
		
	}
	public void viewInformation() {
		System.out.println("What Information would you like to see?\nAll(1)\nSelect Information(2)");
		int menuSelection = in.nextInt();
		switch(menuSelection) {
		case 1:
			System.out.println("Account Number: " + 
							   account.getAccountNumber() + 
							   "\nFirst Name: " + 
							   account.getUser().getFirstName() + 
							   "\nLast Name: " + 
							   account.getUser().getLastName() + 
							   "\nPin: " + 
							   account.getUser().getPin() + 
							   "\nBirth Date: " + 
							   account.getUser().getBirthDate() + 
							   "\nPhone Number: " + 
							   account.getUser().getPhoneNumber() + 
							   "\nAddress: " + 
							   account.getUser().getAddress() + 
							   "\nCity: " + 
							   account.getUser().getCity() +
							   "\nState: " + 
							   account.getUser().getState() +
							   "\nPostal Code: " + 
							   account.getUser().getPostalCode());
			break;
		case 2:
			System.out.println("Account Number(1)\nFirst Name(2)\nLast Name(3)\nPin(4)\nBirth Date(5)\nPhone Number(6)\nAddress(7)\nCity(8)\nState(9)\nPostal Code(10)");
			int menuSelection2 = in.nextInt();
			switch(menuSelection2) {
			case 1:
				System.out.println("Account Number: " + account.getAccountNumber());
				break;
			case 2:
				System.out.println("First Name: " + account.getUser().getFirstName());
				break;
			case 3:
				System.out.println("Last Name: " + account.getUser().getLastName());
				break;
			case 4:
				System.out.println("Pin: " + account.getUser().getPin());
				break;
			case 5:
				System.out.println("Birth Date: " + account.getUser().getBirthDate());
				break;
			case 6:
				System.out.println("Phone Number: " + account.getUser().getPhoneNumber());
				break;
			case 7:
				System.out.println("Address: " + account.getUser().getAddress());
				break;
			case 8:
				System.out.println("City: " + account.getUser().getCity());
				break;
			case 9:
				System.out.println("State: " + account.getUser().getState());
				break;
			case 10:
				System.out.println("Postal Code: " + account.getUser().getPostalCode());
				break;
			}
			break;
		}
	}
	public void updateInformation() {
		
	}
	public void closeAccount() {
		
	}
	//Actions with in an app separated by one space
	//Applications separated by 5 spaces
	//Action followed by OUTPUT separated by 2 spaces
	
}