/**
 * Just like last time, the User class is responsible for retrieving
 * (i.e., getting), and updating (i.e., setting) user information.
 * This time, though, you'll need to add the ability to update user
 * information and display that information in a formatted manner.
 * 
 * Most of the functionality for this class should have already been
 * implemented last time. You can always reference my Github repository
 * for inspiration (https://github.com/rwilson-ucvts/java-sample-atm).
 */

public class User {
	
	private String firstName;
	private String lastName;
	private String pin;
	private String birthDate;
	private long phoneNumber;
	private String address;
	private String city;
	private String state;
	private String postalCode;
	
	//Constructor
	public User(String firstName, String lastName, String pin, String birthDate, long phoneNumber, String address, String city, String state, String postalCode) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.pin = pin;
		this.birthDate = birthDate;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
	}
	
	//Getters
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getPin() {
		return pin;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getPostalCode() {
		return postalCode;
	}
	
	//Setters
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
}