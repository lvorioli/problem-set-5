import java.io.*; 
/**
 * This class will serve as the intermediary between our ATM program and
 * the database of BankAccounts. It'll be responsible for fetching accounts
 * when users try to login, as well as updating those accounts after any
 * changes are made.
 */

public class Database {
	private int numberOfAccounts;
	private BankAccount[] accounts;
	private int currentAccount;
	
	public Database(int currentAccount) throws IOException, NumberFormatException {
		this.currentAccount = currentAccount;
		readNumberOfLines();
		accounts = new BankAccount[numberOfAccounts];
		for(int i = 0; i < numberOfAccounts; i++) {
			String line = readLine(i);
			long accountNumber = Long.parseLong(line.substring(0, 9));
			String firstName = line.substring(48, 63).replaceAll("\\s","");
			String lastName = line.substring(28, 48).replaceAll("\\s","");
			String pin = line.substring(9, 13);
			String birthDate = line.substring(63, 71);
			long phoneNumber = Long.parseLong(line.substring(71, 81));
			String address = line.substring(81, 111).replaceAll("\\s","");
			String city = line.substring(111, 141).replaceAll("\\s","");
			String state = line.substring(141, 143);
			String postalCode = line.substring(143, 148);
			double balance = Double.parseDouble(line.substring(13, 28));
			char accountStatus = line.charAt(148);
			accounts[i] = new BankAccount(
							  accountNumber,
							  new User(
								  firstName,
								  lastName,
								  pin,
								  birthDate,
								  phoneNumber,
								  address,
								  city,
								  state,
								  postalCode
							  ),
							  balance,
							  accountStatus
							  );
		}
	}
	public void readNumberOfLines() throws IOException {
		String fileName = "C:\\Users\\lvoor\\Desktop\\APCSA\\pset5\\accounts-db.txt";
		File file = new File(fileName);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line;
		while((line = br.readLine()) != null){
		    numberOfAccounts++;
		}
	}
	public String readLine(int i) throws IOException {
		String fileName = "C:\\Users\\lvoor\\Desktop\\APCSA\\pset5\\accounts-db.txt";
		File file = new File(fileName);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		int counter = 0;
		while(counter < i){
			br.readLine();
			counter++;
		}
		String line = br.readLine();
		br.close();
		fr.close();
		return line;
	}
	public void writeLine(String firstName, String lastName, String pin, String birthDate, String phoneNumber, String address, String city, String state, String postalCode) throws IOException {
		String fileName = "C:\\Users\\lvoor\\Desktop\\APCSA\\pset5\\accounts-db.txt";
		File file = new File(fileName);
		FileWriter fr = new FileWriter(file, true);
		BufferedWriter br = new BufferedWriter(fr);
		String input = "\n" + Long.toString(100000001 + numberOfAccounts) + 
				                     pin + 
				                     String.format("%-15s", "0.00") + 
				                     String.format("%-20s", lastName) +
				                     String.format("%-15s", firstName) + 
				                     String.format("%-8s", birthDate) + 
				                     String.format("%-10s", phoneNumber) + 
				                     String.format("%-30s", address) + 
				                     String.format("%-30s", city) + 
				                     state +
				                     postalCode +
				                     "Y";
		br.write(input);
		br.close();
		fr.close();
	}
	public boolean accountValidate(String accountNumber, String pin) {
		for(int i = 0; i < numberOfAccounts; i++) {
			if(accounts[i].getAccountStatus() == 'Y' && (accountNumber.equals(Long.toString(accounts[i].getAccountNumber())) && pin.equals(accounts[i].getUser().getPin()))) {
				currentAccount = i;
				return true;
			}
		}
		return false;
	}
	public void updateDatabase(String newLine) throws IOException {
		String newText[] = new String[numberOfAccounts];
		for(int i = 0; i < numberOfAccounts; i++) {
			if(i == currentAccount) {
				newText[i] = newLine;
			}
			else {
				newText[i] = readLine(i);
			}
		}
		PrintWriter writer = new PrintWriter("C:\\Users\\lvoor\\Desktop\\APCSA\\pset5\\accounts-db.txt");
		writer.print("");
		writer.close();
		
		String fileName = "C:\\Users\\lvoor\\Desktop\\APCSA\\pset5\\accounts-db.txt";
		File file = new File(fileName);
		FileWriter fr = new FileWriter(file, true);
		BufferedWriter br = new BufferedWriter(fr);
		
		for(int i = 0; i < numberOfAccounts; i++) {
			if(i == 0) {
				br.write(newText[i]);
			}
			else{
				br.write("\n".concat(newText[i]));
			}
		}
		br.close();
		fr.close();
	}
	public BankAccount getAccount(int i) {
		return accounts[i];
	}
	public int getCurrentAccount() {
		return currentAccount;
	}
	public int getNumberOfAccounts() {
		return numberOfAccounts;
	}
	public void setCurrentAccount(int currentAccount) {
		this.currentAccount = currentAccount;
	}
	public int findAccount(long accountNumber) {
		int i = 0;
		for(i = 0; i < numberOfAccounts; i++) {
			if((accountNumber == accounts[i].getAccountNumber()) && accounts[i].getAccountStatus() == 'Y') {
				return i;
			}
		}
		return numberOfAccounts;
	}
}