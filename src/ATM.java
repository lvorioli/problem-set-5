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
			in.close();
			System.out.print("\n\n\n\n\nSession Terminated");
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
		database = new Database(-1);
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
		System.out.println("Deposit funds(1)\nWithdraw funds(2)\nTransfer funds(3)\nView balance(4)\nView personal information(5)\nUpdate personal information(6)\nClose account(7)\nLogout(8)\n\nMake a selection: ");
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
			break;
		case 8:
			displayMainMenu();
			break;
		default:
			System.out.println("Invalid Selection");
			displaySubMenu();
			break;
		}
	}
	private void viewBalanceMenu() throws IOException {
		System.out.printf("\n\nBalance Amount: %.2f", account.getBalance());
		System.out.print("\nExit to menu screen(1)\n\nMake a selection: ");
		int menuSelection = in.nextInt();
		displaySubMenu();
	}
	public void depositMenu() throws IOException {
		int returnCode = 1;
		double depositAmount;
		//String oldBalance = String.format("%1$-15s",Double.toString(getBankAccount().getBalance()));
		System.out.print("\n\nDeposit Amount: ");
		do{
			if(returnCode == 0) {
				System.out.println("\nPlease enter a valid deposit amount: ");
			}
			depositAmount = in.nextDouble();
			returnCode = account.deposit(depositAmount);
		}while(returnCode == 0);
		databaseUpdate();
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
		double withdrawAmount;
		System.out.print("\n\nWithdraw Amount: ");
		do{
			if(returnCode == 1) {
				System.out.println("\nPlease enter a valid withdraw amount: ");
			}
			else if(returnCode == 0) {
				System.out.println("\nWithdraw amount is too high: ");
			}
			withdrawAmount = in.nextDouble();
			returnCode = account.withdraw(withdrawAmount);
		}while(returnCode != 2);
		databaseUpdate();
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
	public void transferMenu() throws IOException {
		int returnCode = 2;
		double transferAmount;
		System.out.print("\n\nTransfer Amount: ");
		do{
			if(returnCode == 1) {
				System.out.println("\nPlease enter a valid transfer amount: ");
			}
			else if(returnCode == 0) {
				System.out.println("\nInsufficient funds: ");
			}
			transferAmount = in.nextDouble();
			returnCode = account.withdraw(transferAmount);
		}while(returnCode != 2);
		
		boolean validate = false;
		long transferAccountNumber;
		do {
			System.out.print("Recipient's Account Number: ");
		transferAccountNumber = in.nextLong();
			if(getDatabase().findAccount(transferAccountNumber) == -1) {
				System.out.println("Invalid Account");
			}
			else {
				validate = true;
			}
		}while(validate = false);
		
		//ADD ERROR HANDLING
		databaseUpdate();
		int transferAccountIndex = getDatabase().findAccount(transferAccountNumber);
		System.out.println(transferAccountIndex);
		int currentAccountIndex = getDatabase().getCurrentAccount();
		getDatabase().setCurrentAccount(transferAccountIndex);
		account = getDatabase().getAccount(getDatabase().getCurrentAccount());
		account.deposit(transferAmount);
		databaseUpdate();
		getDatabase().setCurrentAccount(currentAccountIndex);
		account = getDatabase().getAccount(getDatabase().getCurrentAccount());
		System.out.println("\n$" + transferAmount + " has been transfered to account " + transferAccountNumber + ".");
		System.out.print("\nMake another transfer(1)\nExit to menu screen(2)\n\nMake a selection: ");
		Double menuSelection = in.nextDouble();
		if(menuSelection == 1) {
			withdrawMenu();
		}
		else {
			displaySubMenu();
		}
	}
	public void viewInformation() throws IOException {
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
			System.out.println("Account Number(1)\nFirst Name(2)\nLast Name(3)\nPin(4)\nBirth Date(5)\nPhone Number(6)\nAddress(7)\nCity(8)\nState(9)\nPostal Code(10)\n\nMake a selection: ");
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
		System.out.print("View Information(1)\nExit to menu screen(2)\n\nMake a selection: ");
		int menuSelection2 = in.nextInt();
		if(menuSelection2 == 1) {
			viewInformation();
		}
		displaySubMenu();
	}
	
	public void updateInformation() throws IOException {
		System.out.println("First Name(1)\nLast Name(2)\nPin(3)\nBirth Date(4)\nPhone Number(5)\nAddress(6)\nCity(7)\nState(8)\nPostal Code(9)\n\nMake a selection: ");
		int menuSelection = in.nextInt();
		switch(menuSelection) {
		case 1:
			System.out.print("First Name: ");
			in.nextLine();
			String newFirstName = in.nextLine();
			account.getUser().setFirstName(newFirstName);
			databaseUpdate();
			break;
		case 2:
			System.out.print("Last Name: ");
			in.nextLine();
			String newLastName = in.nextLine();
			account.getUser().setLastName(newLastName);
			databaseUpdate();
			break;
		case 3:
			System.out.print("Current Pin: ");
			in.nextLine();
			String currentPin = in.nextLine();
			System.out.print("New Pin: ");
			String newPin = in.nextLine();
			//ADD ERROR HANDLING
			account.getUser().setPin(newPin, currentPin);
			databaseUpdate();
			break;
		case 4:
			System.out.print("Birth Date: ");
			in.nextLine();
			String newBirthDate = in.nextLine();
			account.getUser().setBirthDate(newBirthDate);
			databaseUpdate();
			break;
		case 5:
			System.out.print("Phone Number: ");
			in.nextLine();
			long newPhoneNumber = in.nextLong();
			account.getUser().setPhoneNumber(newPhoneNumber);
			databaseUpdate();
			break;
		case 6:
			System.out.print("Address: ");
			in.nextLine();
			String newAddress = in.nextLine();
			account.getUser().setAddress(newAddress);
			databaseUpdate();
			break;
		case 7:
			System.out.print("City: ");
			in.nextLine();
			String newCity = in.nextLine();
			account.getUser().setCity(newCity);
			databaseUpdate();
			break;
		case 8:
			System.out.println("State: ");
			in.nextLine();
			String newState = in.nextLine();
			account.getUser().setState(newState);
			databaseUpdate();
			break;
		case 9:
			System.out.println("Postal Code: ");
			in.nextLine();
			String newPostalCode = in.nextLine();
			account.getUser().setPostalCode(newPostalCode);
			databaseUpdate();
			break;
		}
		System.out.print("Update personal Information(1)\nExit to menu screen(2)\n\nMake a selection: ");
		int menuSelection2 = in.nextInt();
		if(menuSelection2 == 1) {
			updateInformation();
		}
		displaySubMenu();
		
		
	}
	public void closeAccount() throws IOException {
		System.out.print("Enter PIN to close account: ");
		int pin = in.nextInt();
		//Add validation
		if(pin == Integer.parseInt(account.getUser().getPin())) {
			account.setAccountStatus('N');
			databaseUpdate();
			System.out.print("Account closed\n\nExit to main menu screen(ANY KEY)\n\nMake a selection: ");
			in.nextLine();
			in.nextLine();
			displayMainMenu();
		}
	}
	public void databaseUpdate() throws IOException {
		String newLine = Long.toString(getBankAccount().getAccountNumber()) +
 				getBankAccount().getUser().getPin() +
 				String.format("%1$-15s", Double.toString(getBankAccount().getBalance())) +
 				String.format("%1$-20s", getBankAccount().getUser().getLastName()) + 
 				String.format("%1$-15s", getBankAccount().getUser().getFirstName()) +
 				getBankAccount().getUser().getBirthDate() + 
 				getBankAccount().getUser().getPhoneNumber() +  
 				String.format("%1$-30s", getBankAccount().getUser().getAddress()) + 
 				String.format("%1$-30s", getBankAccount().getUser().getCity()) + 
 				getBankAccount().getUser().getState() + 
 				getBankAccount().getUser().getPostalCode() + 
 				getBankAccount().getAccountStatus();
getDatabase().updateDatabase(newLine);
int currentAccount = getDatabase().getCurrentAccount();
database = new Database(currentAccount);
	}
	/*public boolean validateInput(String menu, String input) {
		
	}*/
	//Actions with in an app separated by one space
	//Applications separated by 5 spaces
	//Action followed by OUTPUT separated by 2 spaces
	
}