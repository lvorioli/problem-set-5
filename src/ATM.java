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
			System.out.println("\n\nInvalid Selection\n");
			displayMainMenu();
			break;
		}
		
	}
	public void openAccount() throws IOException {
		int i = 0;
		String firstName;
		String lastName;
		String pin;
		String birthDate;
		String phoneNumber;
		String address;
		String city;
		String state;
		String postalCode;
		System.out.print("\n\n\n");
		do {
			System.out.print("\n\n");
			System.out.print("First Name: ");
			if(i == 0) {
				in.nextLine();
				i++;
			}
			firstName = in.nextLine();
			System.out.print("\nLast Name: ");
			lastName = in.nextLine();
			System.out.print("\nPin: ");
			pin = in.nextLine();
			System.out.print("\nBirth Date: ");
			birthDate = in.nextLine();
			System.out.print("\nPhone Number: ");
			phoneNumber = in.nextLine();
			System.out.print("\nAddress: ");
			address = in.nextLine().replaceAll("\\s+","");
			System.out.print("\nCity: ");
			city = in.nextLine().replaceAll("\\s+","");
			System.out.print("\nState: ");
			state = in.nextLine();
			System.out.print("\nPostal Code: ");
			postalCode = in.nextLine();
			System.out.print("\n\n");
		}while(openAccountFormatValidate(firstName, lastName, pin, birthDate, phoneNumber, address, city, state, postalCode) == false);
		getDatabase().writeLine(firstName, lastName, pin, birthDate, phoneNumber, address, city, state, postalCode);
		database = new Database(-1);
		System.out.println("Account Successfully Created\n\n\n");
		displayMainMenu();
		//getDatabase().createAccount(firstName, lastName, pin, birthDate, phoneNumber, address, city, state, postalCode);
		//Append database with information
		//BankAccount newAccount = new BankAccount((long)Math.random(), new User(firstName, lastName, pin, birthdate, phoneNumber, address, city, state, postalCode), 0);
	}
	public void login() throws IOException {
		System.out.print("\n\n\n");
		//Change to Strings for checking and for correct pins
		String accountNumber;
		String pin;
		boolean validate;
		int i = 0;
		do {
			System.out.print("\n\nAccount number: ");
			if(i == 0) {
				in.nextLine();
				i++;
			}
			accountNumber = in.nextLine();
			System.out.print("\nPin: ");
			pin = in.nextLine();
			validate = getDatabase().accountValidate(accountNumber, pin) && loginFormatValidate(accountNumber, pin);
			if(validate == false) {
				System.out.println("\n\nIncorrect Credentials");
			}
		}while(validate == false);
		
		account = getDatabase().getAccount(getDatabase().getCurrentAccount());
		System.out.println("\n\n\n\n\nWelcome, " + getBankAccount().getUser().getFirstName() + "\n");
		displaySubMenu();
	}
	public void displaySubMenu() throws IOException {
		System.out.print("Deposit funds(1)\nWithdraw funds(2)\nTransfer funds(3)\nView balance(4)\nView personal information(5)\nUpdate personal information(6)\nClose account(7)\nLogout(8)\n\nMake a selection: ");
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
			System.out.print("\n\n\n\n\n");
			displayMainMenu();
			break;
		default:
			System.out.println("Invalid Selection");
			displaySubMenu();
			break;
		}
	}
	public void viewBalanceMenu() throws IOException {
		System.out.printf("\n\nBalance Amount: %.2f", account.getBalance());
		System.out.print("\n\nExit to menu screen(ANY KEY)\n\nMake a selection: ");
		in.nextLine();
		in.nextLine();
		System.out.print("\n\n\n\n\n");
		displaySubMenu();
	}
	public void depositMenu() throws IOException {
		int returnCode = 1;
		String depositAmount;
		//String oldBalance = String.format("%1$-15s",Double.toString(getBankAccount().getBalance()));
		System.out.print("\n\n\n\n\nDeposit Amount: ");
		in.nextLine();
		do{
			if(returnCode == 0) {
				System.out.print("\n\nPlease enter a valid deposit amount\n\nDeposit Amount: ");
			}
			depositAmount = in.nextLine();
			if(digitsValidate(depositAmount) == true) {
				returnCode = account.deposit(Double.parseDouble(depositAmount));
			}
			else {
				returnCode = 0;
			}
			
		}while(returnCode == 0);
		databaseUpdate();
		System.out.printf("\n\n$%.2f has been deposited\n", Double.parseDouble(depositAmount));
		System.out.print("\nMake another deposit(1)\nExit to menu screen(2)\n\nMake a selection: ");
		int menuSelection = in.nextInt();
		if(menuSelection == 1) {
			depositMenu();
		}
		else {
			System.out.print("\n\n\n\n\n");
			displaySubMenu();
		}
	}
	public void withdrawMenu() throws IOException {
		int returnCode = 2;
		String withdrawAmount;
		System.out.print("\n\n\n\n\nWithdraw Amount: ");
		in.nextLine();
		do{
			if(returnCode == 1) {
				System.out.print("\n\nPlease enter a valid withdraw amount\n\nWithdraw Amount: ");
			}
			else if(returnCode == 0) {
				System.out.print("\n\nWithdraw amount is too high\n\nWithdraw Amount: ");
			}
			withdrawAmount = in.nextLine();
			if(digitsValidate(withdrawAmount) == true) {
				returnCode = account.withdraw(Double.parseDouble(withdrawAmount));
			}
			else {
				returnCode = 1;
			}
			
		}while(returnCode != 2);
		databaseUpdate();
		System.out.printf("\n\n$%.2f has been withdrawn\n", Double.parseDouble(withdrawAmount));
		System.out.print("\nMake another withdraw(1)\nExit to menu screen(2)\n\nMake a selection: ");
		Double menuSelection = in.nextDouble();
		if(menuSelection == 1) {
			withdrawMenu();
		}
		else {
			System.out.print("\n\n\n\n\n");
			displaySubMenu();
		}
	}
	public void transferMenu() throws IOException {
		int returnCode = 2;
		double transferAmount;
		System.out.print("\n\n\n\n\nTransfer Amount: ");
		do{
			if(returnCode == 1) {
				System.out.print("\n\nPlease enter a valid transfer amount\nTansfer Amount: ");
			}
			else if(returnCode == 0) {
				System.out.print("\n\nInsufficient funds\n\nTransfer Amount: ");
			}
			transferAmount = in.nextDouble();
			returnCode = account.withdraw(transferAmount);
		}while(returnCode != 2);
		
		boolean validate = false;
		String transferAccountNumber;
		in.nextLine();
		do {
			System.out.print("\nRecipient's Account Number: ");
			transferAccountNumber = in.nextLine();
			if(getDatabase().findAccount(Long.parseLong(transferAccountNumber)) == getDatabase().getNumberOfAccounts() && digitsValidate(transferAccountNumber) == true) {
				System.out.println("\n\nInvalid Account");
			}
			else {
				validate = true;
			}
		}while(validate == false);
		databaseUpdate();
		int transferAccountIndex = getDatabase().findAccount(Long.parseLong(transferAccountNumber));
		int currentAccountIndex = getDatabase().getCurrentAccount();
		getDatabase().setCurrentAccount(transferAccountIndex);
		account = getDatabase().getAccount(getDatabase().getCurrentAccount());
		account.deposit(transferAmount);
		databaseUpdate();
		getDatabase().setCurrentAccount(currentAccountIndex);
		account = getDatabase().getAccount(getDatabase().getCurrentAccount());
		System.out.printf("\n$%.2f has been transfered to account %s\n", transferAmount, transferAccountNumber);
		System.out.print("\nMake another transfer(1)\nExit to menu screen(2)\n\nMake a selection: ");
		Double menuSelection = in.nextDouble();
		if(menuSelection == 1) {
			transferMenu();
		}
		else {
			System.out.print("\n\n\n\n\n");
			displaySubMenu();
		}
	}
	public void viewInformation() throws IOException {
		System.out.print("\n\n\n\n");
		boolean validate = false;
		int menuSelection;
		do {
			System.out.print("\nSee All Information(1)\nSelect Information(2)\n\nMake a selection: ");
			menuSelection = in.nextInt();
			if(menuSelection == 1 || menuSelection == 2) {
				validate = true;
			}
		}while(validate == false);
		switch(menuSelection) {
		case 1:
			System.out.println("\n\nAccount Number: " + 
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
			System.out.print("\n\nAccount Number(1)\nFirst Name(2)\nLast Name(3)\nPin(4)\nBirth Date(5)\nPhone Number(6)\nAddress(7)\nCity(8)\nState(9)\nPostal Code(10)\n\nMake a selection: ");
			int menuSelection2 = in.nextInt();
			switch(menuSelection2) {
			case 1:
				System.out.println("\n\nAccount Number: " + account.getAccountNumber());
				break;
			case 2:
				System.out.println("\n\nFirst Name: " + account.getUser().getFirstName());
				break;
			case 3:
				System.out.println("\n\nLast Name: " + account.getUser().getLastName());
				break;
			case 4:
				System.out.println("\n\nPin: " + account.getUser().getPin());
				break;
			case 5:
				System.out.println("\n\nBirth Date: " + account.getUser().getBirthDate());
				break;
			case 6:
				System.out.println("\n\nPhone Number: " + account.getUser().getPhoneNumber());
				break;
			case 7:
				System.out.println("\n\nAddress: " + account.getUser().getAddress());
				break;
			case 8:
				System.out.println("\n\nCity: " + account.getUser().getCity());
				break;
			case 9:
				System.out.println("\n\nState: " + account.getUser().getState());
				break;
			case 10:
				System.out.println("\n\nPostal Code: " + account.getUser().getPostalCode());
				break;
			}
			break;
		}
		System.out.print("\nView Information(1)\nExit to menu screen(2)\n\nMake a selection: ");
		int menuSelection2 = in.nextInt();
		if(menuSelection2 == 1) {
			viewInformation();
		}
		System.out.print("\n\n\n\n\n");
		displaySubMenu();
	}
	public void updateInformation() throws IOException {
		System.out.print("\n\n\n\n\nFirst Name(1)\nLast Name(2)\nPin(3)\nBirth Date(4)\nPhone Number(5)\nAddress(6)\nCity(7)\nState(8)\nPostal Code(9)\n\nMake a selection: ");
		int menuSelection = in.nextInt();
		switch(menuSelection) {
		case 1:
			String newFirstName;
			do {
				System.out.print("\n\nFirst Name: ");
				in.nextLine();
				newFirstName = in.nextLine();
			}while(informationUpdateValidate(newFirstName, 0) == false);
			account.getUser().setFirstName(newFirstName);
			databaseUpdate();
			System.out.println("\n\nFirst name updated to " + newFirstName + "\n");
			break;
		case 2:
			String newLastName;
			do {
				System.out.print("\n\nLast Name: ");
				in.nextLine();
				newLastName = in.nextLine();
			}while(informationUpdateValidate(newLastName, 1) == false);
			account.getUser().setLastName(newLastName);
			databaseUpdate();
			System.out.println("\n\nLast name updated to " + newLastName + "\n");
			break;
		case 3:
			String currentPin, newPin;
			boolean validate = true;
			do {
				if(validate == false) {
					System.out.println("\n\nIncorrect Pin Entered");
				}
				do {
					System.out.print("\n\nCurrent Pin: ");
					in.nextLine();
					currentPin = in.nextLine();	
				}while(informationUpdateValidate(currentPin, 2) == false);
				if(account.getUser().getPin().equals(currentPin)) {
					validate = true;
				}
				else {
					validate = false;
				}
			}while(validate == false);
			do {
				System.out.print("\nNew Pin: ");
				newPin = in.nextLine();
			}while(informationUpdateValidate(newPin, 2) == false);
			account.getUser().setPin(newPin);
			databaseUpdate();
			System.out.println("\n\nPIN updated\n");
			break;
		case 4:
			String newBirthDate;
			do {
				System.out.print("\n\nBirth Date: ");
				in.nextLine();
				newBirthDate = in.nextLine();
			}while(informationUpdateValidate(newBirthDate, 3) == false);
			account.getUser().setBirthDate(newBirthDate);
			databaseUpdate();
			System.out.println("\n\nBirth date updated to " + newBirthDate + "\n");
			break;
		case 5:
			String newPhoneNumber;
			do {
				System.out.print("\n\nPhone Number: ");
				in.nextLine();
				newPhoneNumber = in.nextLine();
			}while(informationUpdateValidate(newPhoneNumber, 4) == false);
			account.getUser().setPhoneNumber(Long.parseLong(newPhoneNumber));
			databaseUpdate();
			System.out.println("\n\nPhone number updated to " + newPhoneNumber + "\n");
			break;
		case 6:
			String newAddress;
			do {
				System.out.print("\n\nAddress: ");
				in.nextLine();
				newAddress = in.nextLine().replaceAll("\\s+","");
			}while(informationUpdateValidate(newAddress, 5) == false);
			account.getUser().setAddress(newAddress);
			databaseUpdate();
			System.out.println("\n\nAddress updated to " + newAddress + "\n");
			break;
		case 7:
			String newCity;
			do {
				System.out.print("\n\nCity: ");
				in.nextLine();
				newCity = in.nextLine().replaceAll("\\s+","");
			}while(informationUpdateValidate(newCity, 6) == false);
			account.getUser().setCity(newCity);
			databaseUpdate();
			System.out.println("\n\nCity updated to " + newCity + "\n");
			break;
		case 8:
			String newState;
			do {
				System.out.println("\n\nState: ");
				in.nextLine();
				newState = in.nextLine();
			}while(informationUpdateValidate(newState, 7) == false);
			account.getUser().setState(newState);
			databaseUpdate();
			System.out.println("\n\nState updated to " + newState + "\n");
			break;
		case 9:
			String newPostalCode;
			do {
				System.out.println("\n\nPostal Code: ");
					in.nextLine();
					newPostalCode = in.nextLine();
			}while(informationUpdateValidate(newPostalCode, 8) == false);
			account.getUser().setPostalCode(newPostalCode);
			databaseUpdate();
			System.out.println("\n\nPostal code updated to " + newPostalCode + "\n");
			break;
		}
		System.out.print("Update personal Information(1)\nExit to menu screen(2)\n\nMake a selection: ");
		int menuSelection2 = in.nextInt();
		if(menuSelection2 == 1) {
			updateInformation();
		}
		System.out.print("\n\n\n\n\n");
		displaySubMenu();
		
		
	}
	public void closeAccount() throws IOException {
		System.out.println("\n\n");
		int i = 0;
		String pin;
		boolean validate = true;
		do {
			do {
				System.out.print("\n\nEnter PIN to close account: ");
				if(i == 0) {
					in.nextLine();
					i++;	
				}
				pin = in.nextLine();
			}while(informationUpdateValidate(pin, 2) == false);
			if(account.getUser().getPin().equals(pin)) {
				validate = true;
			}
			else {
				validate = false;
			}
		}while(validate == false);
		account.setAccountStatus('N');
		databaseUpdate();
		System.out.print("\nAccount closed\n\nExit to main menu screen(ANY KEY)\n\nMake a selection: ");
		in.nextLine();
		System.out.print("\n\n\n\n\n");
		displayMainMenu();
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
	public boolean openAccountFormatValidate(String firstName, String lastName, String pin, String birthDate, String phoneNumber, String address, String city, String state, String postalCode) {
		boolean validate = true;
		if(firstName.length() > 15 || firstName.isEmpty() == true) {
			System.out.println("Invalid First Name");
			validate = false;
		}
		if(lastName.length() > 20 || lastName.isEmpty() == true) {
			System.out.println("Invalid Last Name");
			validate = false;
		}
		if(pin.length() > 4 || pin.isEmpty() == true) {
			System.out.println("Invalid PIN");
			validate = false;
		}
		if(birthDate.length() != 8 || birthDate.isEmpty() == true) {
			System.out.println("Invalid Birth Date");
			validate = false;
		}
		if(phoneNumber.length() != 10 || phoneNumber.isEmpty() == true) {
			System.out.println("Invalid Phone Number");
			validate = false;
		}
		if(address.length() > 30 || address.isEmpty() == true) {
			System.out.println("Invalid Address");
			validate = false;
		}
		if(city.length() > 30 || city.isEmpty() == true) {
			System.out.println("Invalid City");
			validate = false;
		}
		if(state.length() != 2 || state.isEmpty() == true) {
			System.out.println("Invalid State");
			validate = false;
		}
		if(postalCode.length() != 5 || postalCode.isEmpty() == true) {
			System.out.println("Invalid Postal Code");
			validate = false;
		}
		String[] inputs = {firstName, lastName, pin, birthDate, phoneNumber, address, city, state, postalCode};
		for(int index = 0; index < 9; index++) {
			for(int i = 0; i < inputs[index].length(); i++) {
				if(index == 0 || index == 1) {
					if(Character.isLetter(inputs[index].charAt(i)) == false) {
						System.out.println("Invalid Input");
						validate = false;
					}
				}
				else if(index == 6 || index == 7) {
					if(Character.isLetter(inputs[index].charAt(i)) == false && Character.isWhitespace(inputs[index].charAt(i)) == false) {
						System.out.println("Invalid Input");
						validate = false;
					}
					
				}
				else if(index == 2 || index == 3 || index == 4 || index == 8) {
					if(Character.isDigit(inputs[index].charAt(i)) == false) {
						System.out.println("Invalid Input");
						validate = false;
					}
				}
				else {
					if(Character.isDigit(inputs[index].charAt(i)) == false && Character.isLetter(inputs[index].charAt(i)) == false) {
						System.out.println("Invalid Input");
						validate = false;
					}
				}
			}
		}
		return validate;
	}
	public boolean loginFormatValidate(String accountNumber, String pin) {
		if(accountNumber.length() != 9) {
			return false;
		}
		if(pin.length() != 4) {
			return false;
		}
		for(int i = 0; i < 9; i++) {
			if(Character.isDigit(accountNumber.charAt(i)) == false) {
				return false;
			}
		}
		for(int i = 0; i < 4; i++) {
			if(Character.isDigit(pin.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}
	public boolean digitsValidate(String input) {
		if(input.isEmpty()) {
			return false;
		}
		for(int i = 0; i < input.length(); i++) {
			if(Character.isDigit(input.charAt(i)) == false && input.charAt(input.length() - 3) != '.') {
				return false;
			}
		}
		return true;
	}
	public boolean informationUpdateValidate(String input, int code) {
		boolean validate = true;
		switch(code) {
		case 0:
			if(input.length() > 15 || input.isEmpty() == true) {
				System.out.println("\nInvalid First Name");
				validate = false;
			}
			break;
		case 1:
			if(input.length() > 20 || input.isEmpty() == true) {
				System.out.println("\nInvalid Last Name");
				validate = false;
			}
			break;
		case 2:
			if(input.length() > 4 || input.isEmpty() == true) {
				System.out.println("\nInvalid PIN");
				validate = false;
			}
			break;
		case 3:
			if(input.length() != 8 || input.isEmpty() == true) {
				System.out.println("\nInvalid Birth Date");
				validate = false;
			}
			break;
		case 4:
			if(input.length() != 10 || input.isEmpty() == true) {
				System.out.println("\nInvalid Phone Number");
				validate = false;
			}
			break;
		case 5:
			if(input.length() > 30 || input.isEmpty() == true) {
				System.out.println("\nInvalid Address");
				validate = false;
			}
			break;
		case 6:
			if(input.length() > 30 || input.isEmpty() == true) {
				System.out.println("\nInvalid City");
				validate = false;
			}
			break;
		case 7:
			if(input.length() != 2 || input.isEmpty() == true) {
				System.out.println("\nInvalid State");
				validate = false;
			}
			break;
		case 8:
			if(input.length() != 5 || input.isEmpty() == true) {
				System.out.println("\nInvalid Postal Code");
				validate = false;
			}
			break;
		}
		if(validate == true) {
			for(int i = 0; i < input.length(); i++) {
				if(code == 0 || code == 1) {
					if(Character.isLetter(input.charAt(i)) == false) {
						System.out.println("\nInvalid Input");
						return false;
					}
					
				}
				else if(code == 6 || code == 7) {
					if(Character.isLetter(input.charAt(i)) == false && Character.isWhitespace(input.charAt(i)) == false) {
						System.out.println("\nInvalid Input");
						return false;
					}
				}
				else if(code == 2|| code == 3 || code == 4 || code == 8) {
					if(Character.isDigit(input.charAt(i)) == false) {
						System.out.println("\nInvalid Input");
						return false;
					}
				}
				else {
					if(Character.isDigit(input.charAt(i)) == false && Character.isLetter(input.charAt(i)) == false) {
						System.out.println("Invalid Input");
						return false;
					}
				}
			}
		}
		return validate;
	}
	//Actions with in an app separated by one space
	//Applications separated by 5 spaces
	//Action followed by OUTPUT separated by 2 spaces
	//Message from one app to another app 3 spaces
	
}