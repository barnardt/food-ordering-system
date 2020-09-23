package compulsorytask2;

import java.util.Scanner;

public class Customer {
	// Static restaurant ID counter variable
	static int id_counter = 0;
		
	// Attributes
	private int id;
	private String name;
	private String contactNum;
	private String city;
	private String address;
	private String suburb;
	private String email;
	
	// Constructor where each attribute is specified individually
	public Customer(String name, String contactNum, String city, 
						String address, String suburb, String email) {
		this.setId(id_counter);
		id_counter++;
		
		this.setName(name);
		this.setContactNum(contactNum);
		this.setCity(city);
		this.setAddress(address);
		this.setSuburb(suburb);
		this.setEmail(email);
	}
	
	// Constructor where attributes are contained in comma-separated string
	public Customer(String str) {
		
		
		String[] arrOfStr = str.split(", ", 7);
		
		this.setId(Integer.parseInt(arrOfStr[0]));
		this.setName(arrOfStr[1]);
		this.setContactNum(arrOfStr[2]);
		this.setCity(arrOfStr[3]);
		this.setAddress(arrOfStr[4]);
		this.setSuburb(arrOfStr[5]);
		this.setEmail(arrOfStr[6]);
		
		id_counter++;
		
	}
	
	// Constructor where attributes are obtained from user input
	public Customer() {
		this.setId(id_counter);
		id_counter++;
		
		// New scanner object to get user input
		Scanner inputSc = new Scanner(System.in);
		
		// Ask the user for all the relevant attributes of the customer
		System.out.println("Please enter the full name of the customer:");
		this.setName(inputSc.nextLine());
		
		System.out.println("Please enter the customer's contact number:");
		this.setContactNum(inputSc.nextLine());
		
		System.out.println("Please enter the customer's city:");
		this.setCity(inputSc.nextLine());
		
		System.out.println("Please enter the customer's address (street number and name):");
		this.setAddress(inputSc.nextLine());
		
		System.out.println("Please enter the customer's suburb:");
		this.setSuburb(inputSc.nextLine());
		
		System.out.println("Please enter the customer's email:");
		this.setEmail(inputSc.nextLine());
		
		System.out.println("Thank you \n");
	}
	
	// Method to output a short description of the Customer
	public String shortDesc() {
		String output = String.format("ID: %s; Name: %s; City: %s", getId(), getName(), getCity());
		return output;
	}
	
	// Method to generate a complete comma-separate string from Customer attributes
	public String toString() {
		String output = String.format("%s, %s, %s, %s, %s, %s, %s", getId(), getName(), getContactNum(),
											getCity(), getAddress(), getSuburb(), getEmail());
		return output;
	}
	
	// Getter and setter methods
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactNum() {
		return contactNum;
	}

	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSuburb() {
		return suburb;
	}

	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
