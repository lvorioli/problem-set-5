/**
 * Just like last time, the BankAccount class is primarily responsible
 * for depositing and withdrawing money. In the enhanced version, there
 * will be the added requirement of transfering funds between accounts.
 * 
 * Most of the functionality for this class should have already been
 * implemented last time. You can always reference my Github repository
 * for inspiration (https://github.com/rwilson-ucvts/java-sample-atm).
 */

public class BankAccount {
	
	private long accountNumber;
	private User user;
	private double balance;
	private char accountStatus;
	
	//Constructor
	public BankAccount(long accountNumber, User user, double balance, char accountStatus) {
		this.accountNumber = accountNumber;
		this.user = user;
		this.balance = balance;
		this.accountStatus = accountStatus;
	}
	
	//Getters
	public long getAccountNumber() {
		return accountNumber;
	}
	public User getUser() {
		return user;
	}
	public double getBalance() {
		return balance;
	}
	public char getAccountStatus() {
		return accountStatus;
	}
	
	//Setters
	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public void setAccountStatus(char accountStatus) {
		this.accountStatus = accountStatus;
	}
	
	//Methods
	public int deposit(double amount) {
		if (amount <= 0) {
			return 0;
		} else {
			balance = balance + amount;
			
			return 1;
		}
	}
	public int withdraw(double amount) {
		if (amount > balance) {
			return 0;
		} else if (amount <= 0) {
			return 1;
		} else {
			balance = balance - amount;
			
			return 2;
		}
	}
}