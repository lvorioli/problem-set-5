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
	
	public Database() throws IOException {
		readNumberOfLines();
		accounts = new BankAccount[numberOfAccounts];
		for(int i = 0; i < numberOfAccounts; i++) {
			String line = readLine(i);
			long accountNumber = Long.parseLong(line.substring(0, 9));
			String firstName = line.substring(48, 63).replaceAll("\\s","");
			String lastName = line.substring(28, 48).replaceAll("\\s","");
			int pin = Integer.parseInt(line.substring(9, 13));
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
			/*System.out.println(accountNumber);
			System.out.println(firstName);
			System.out.println(lastName);
			System.out.println(pin);
			System.out.println(birthDate);
			System.out.println(phoneNumber);
			System.out.println(address);
			System.out.println(city);
			System.out.println(state);
			System.out.println(postalCode);
			System.out.println(accountStatus);*/
			
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
		return br.readLine();
	}
	public boolean accountValidate(long accountNumber, int pin) {
		for(int i = 0; i < numberOfAccounts; i++) {
			if(true/*accountNumber == accounts[i].getAccountNumber() && pin == accounts[i].getUser().getPin()*/) {
				currentAccount = i;
				return true;
			}
		}
		return false;
	}
	public BankAccount getAccount(int i) {
		return accounts[i];
	}
	public int getCurrentAccount() {
		return currentAccount;
	}
	/*private BankAccount account[];
	public Database() {
		//Set up accounts
	}*/
	//Make an array of BankAccounts which is created when the ATM is launched
	//Methods
	/*public void createAccount(String firstName, String lastName, int pin, String birthDate, int phoneNumber, String address, String city, String state, int postalCode) {
		//Appends the file; Might change to boolean to check if input is valid
	}
	public boolean validateLogin(long accountNumber, int pin) {
		
	}
	public String retrieveFirstName() {
		
	}
	public String retrieveLastName() {
		
	}
	public int retrievePin() {
		
	}
	public String retrieveBirthDate() {
		
	}
	public int retrievePhoneNumber() {
		
	}
	public String retrieveAddress() {
		
	}
	public String retrieveCity() {
		
	}
	public String retrieveState() {
		
	}
	public int retrievePostalCode() {
		
	}
	public long retrieveAccountNumber() {
		
	}
	public double retrieveBalance() {
		
	}*/
	 
	/*public BankAccount getBankAccount(int i) {
		return account[i];
	}
	public void databaseInitialization() {
		for(int i = 0; i < ADD CONDITION; i++) {
			account[i] = 
		}
	}
	
	public void createAccount() {
		
	}*/
}