package compulsorytask2;

import java.util.Scanner;

public class Restaurant {
	// Static restaurant ID counter variable
	static int id_counter = 0;
	
	// Attributes
	private int id;
	private String name;
	private String contactNum;
	private String city;
	
	
	// Constructor
	public Restaurant(String name, String contactNum, String city) {
		
		this.setId(id_counter);
		id_counter++;
		
		this.setName(name);
		this.setContactNum(contactNum);
		this.setCity(city);
	}
	
	// Alternative constructor with single comma-separated string argument
	public Restaurant(String str) {
		String[] arrOfStr = str.split(", ", 4);
		
		this.setId(Integer.parseInt(arrOfStr[0]));
		this.setName(arrOfStr[1]);
		this.setContactNum(arrOfStr[2]);
		this.setCity(arrOfStr[3]);
		
		id_counter++;
	}
	
	// Constructor where attributes are obtained from user input
	public Restaurant() {
		this.setId(id_counter);
		id_counter++;
		
		// New scanner object to get user input
		Scanner inputSc = new Scanner(System.in);
		
		// Ask the user for all the relevant attributes of the customer
		System.out.println("Please enter the name of the restaurant:");
		this.setName(inputSc.nextLine());
		
		System.out.println("Please enter the restaurant's contact number:");
		this.setContactNum(inputSc.nextLine());
		
		System.out.println("Please enter the restaurant's city:");
		this.setCity(inputSc.nextLine());
		
		System.out.println("Thank you \n");
	}
	
	// Method to return a short description of the restaurant
	public String shortDesc() {
		String output = String.format("ID: %s; Name: %s; City: %s", getId(), getName(), getCity());
		return output;
	}
	
	// Method to generate a complete comma-separate string from Customer attributes
	public String toString() {
		String output = String.format("%s, %s, %s, %s", getId(), getName(), getContactNum(),
											getCity());
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
}
