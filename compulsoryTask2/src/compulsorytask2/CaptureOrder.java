package compulsorytask2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.Scanner;

public class CaptureOrder {
	public static void main(String[] args) {
		// Create a scanner object to read user input in the console
		Scanner inputSc = new Scanner(System.in);
		
		// *PREPARE TO CAPTURE NEW ORDERS BY LOADING SAVED DATA FROM DRIVE*
		
		// *Read customers from file*
		// Read the customers file and save to a String ArrayList
		ArrayList<String> customersStr = readFile("./src/resources/customers.txt");
		
		// Create a customer ArrayList to store the customers
		ArrayList<Customer> customers = new ArrayList<>();
		
		// Loop through the String ArrayList and save each customer as a customer
		// object in the Customer ArrayList
		for (int i = 0; i < customersStr.size(); i++) {
			customers.add(new Customer(customersStr.get(i)));
		}
		
		// Sort by ID
		Collections.sort(customers, new SortCustomerById());
		
		// *Read restaurants from file*
		// Read the restaurants file and save to a String ArrayList
		ArrayList<String> restaurantsStr = readFile("./src/resources/restaurants.txt");
				
		// Create an empty ArrayList to store the restaurants
		ArrayList<Restaurant> restaurants = new ArrayList<>();
		
		// Loop through the String ArrayList and save each restaurant as a restaurant
		// object in the Restaurant ArrayList
		for (int i = 0; i < restaurantsStr.size(); i++) {
			restaurants.add(new Restaurant(restaurantsStr.get(i)));
		}
		
		// *Read drivers from file*
		// Read the drivers.txt file and save to an ArrayList
		ArrayList<String> driversStr = readFile("./src/resources/drivers.txt");
		
		// Convert the driver strings to driver class objects in a new ArrayList
		ArrayList<Driver> driversObj  = new ArrayList<>();
		
		// Loop over all the elements in the string ArrayList
		for (int i = 0; i < driversStr.size(); i++) {
			// Add this driver to object ArrayList
			driversObj.add(new Driver(driversStr.get(i)));
		}
		
		// *Read orders from file*
		// Read orders.txt file and save to ArrayList<String>
		ArrayList<String> ordersSummary = readFile("./src/resources/ordersSummary.txt");
		
		// *CAPTURE ORDER(S) FROM USER*
		
		// Run while loop so that user can place multiple orders for different
		// customers and restaurants
		while (true) {
			// *New Order object*
			// Create an empty Order object to save details of the order
			Order thisOrder = new Order();
			
			// Set the order number for this order
			int orderNum = ordersSummary.size();
			thisOrder.setOrderNum(orderNum);
			
			// *Customer*
			while (true) {
				// Get user input on the customer
				String custInput = getCustInput(customers);
				
				// If the user requests it, add a new customer with details provided by user
				if (Character.toLowerCase(custInput.charAt(0)) == 'n') {
					Customer newCustomer = new Customer(); 
					thisOrder.setCustomer(newCustomer);
					
					// Add customer to list of customers
					customers.add(thisOrder.getCustomer());
				}
				// Otherwise, find the customer according to the ID value entered by the user
				else {
					Customer newCustomer = findCust(custInput, customers);
					thisOrder.setCustomer(newCustomer);
				}
				
				// Check if customer info was captured. If not, ask user to try again
				if (thisOrder.getCustomer() != null) {
					
					// Check if there are drivers in the customer city, and if so
					// allocate a driver to the order
					String custCity = thisOrder.getCustomer().getCity();
					
					// Find the driver in the relevant city with the minimum load
					Driver minDriver = findMinDriver(driversObj, custCity);
					
					// If no drivers are available in customer city, try again
					if (minDriver == null) {
						System.out.println(String.format("Unfortunately no drivers are currently operating in %s",
															custCity));
						System.out.println("Please select a customer in a different city");
						continue;
					}
					
					// Set the driver for current order
					thisOrder.setDriver(minDriver);
					
					break;
				}
				
				System.out.println("Please try again");	
			}
			
			// *Restaurant*
			while (true) {
				// Get user input on the restaurant
				String restInput = getRestInput(restaurants);
				
				// If the user requests it, get add a new restaurant with details provided by user
				if (Character.toLowerCase(restInput.charAt(0)) == 'n') {
					Restaurant newRestaurant = new Restaurant();
					thisOrder.setRestaurant(newRestaurant);
					
					// Add restaurant to list of restaurants
					restaurants.add(thisOrder.getRestaurant());
				}
				
				// Otherwise, find the restaurant according to the ID value entered by the user
				else {
					thisOrder.setRestaurant(findRest(restInput, restaurants));
				}
				
				// Check if restaurant info was captured
				if (thisOrder.getRestaurant() != null) {
					
					// Check if the restaurant is in the same city as the customer
					String custCity = thisOrder.getCustomer().getCity().trim();
					String restCity = thisOrder.getRestaurant().getCity().trim();
					
					if (!custCity.equals(restCity)) {
						System.out.println("The restaurant and customer are not in the same city");
						System.out.println(String.format("Please select a restaurant in %s", custCity));
						continue;
					}
					
					break;
				}		
				
				System.out.println("Please try again");		
			}
							
			// *Meal / menu item*
			ArrayList<Meal> thisMeals = getMeals();
			thisOrder.setMeals(thisMeals);
			
			// *Total price*
			double totalPrice = 0;
			for (int i = 0; i < thisMeals.size(); i++) {
				totalPrice += thisMeals.get(i).getPrice() * thisMeals.get(i).getQuantity();
			}
			thisOrder.setTotalPrice(totalPrice);
			
			// *Special instructions for restaurant*
			System.out.println("Please add any special preparation instructions for the restaurant");
			String instructions = inputSc.nextLine();
			thisOrder.setInstructions(instructions);				
			
			// *Invoice & order summary*
			// Write invoice to file
			String invoice = thisOrder.toInvoice();
			String fn = "./src/resources/invoices/invoice-" + thisOrder.getOrderNum();
			writeString(fn, invoice);
			
			// Add the order just completed to the ArrayList with the order summaries
			ordersSummary.add(thisOrder.toSummary());
			
			// *Update driver load*
			// Find the index of the selected driver in the ArrayList of drivers
			Driver minDriver = thisOrder.getDriver();
			int driverIndex = -1;
			for (int i = 0; i < driversObj.size(); i++) {
				if (driversObj.get(i).getName().equals(minDriver.getName())) {
					driverIndex = i;
				}
			}
			
			// Add new order to selected driver's load
			int load = minDriver.getLoad();
			minDriver.setLoad(load + 1);
			
			// Update the driver in the ArrayList of drivers
			driversObj.set(driverIndex, minDriver);
			
			// *Check for new order*
			// Ask the user whether they want to place another order
			System.out.println("Would you like to place another order? y/n");
			String continueInput = inputSc.nextLine();
			
			// If they do not, exit the while loop
			if (Character.toLowerCase(continueInput.charAt(0)) == 'n') {
				break;
			}
		}
		
		// *NO MORE ORDERS - SAVE UPDATED DATA TO DRIVE BEFORE EXIT*
		
		// *Save customers*
		// Sort the list of customers by city
		Collections.sort(customers, new SortCustomerByCity());
		
		// Convert the ArrayList<Customer> to ArrayList<String>
		ArrayList<String> customersWrite = new ArrayList<>();
		for (int i = 0; i < customers.size(); i++) {
			customersWrite.add(customers.get(i).toString());
		}
		
		// Output the sorted list of customers to a file
		writeArrayList("./src/resources/customers.txt", customersWrite);
		
		// *Save restaurants*
		// Convert the ArrayList<Restaurant> to ArrayList<String>
		ArrayList<String> restaurantsWrite = new ArrayList<>();
		for (int i = 0; i < restaurants.size(); i++) {
			restaurantsWrite.add(restaurants.get(i).toString());
		}
		
		// Write the restaurants to a file
		writeArrayList("./src/resources/restaurants.txt", restaurantsWrite);
		
		// *Save drivers*
		// Convert the ArrayList<Driver> to ArrayList<String>
		ArrayList<String> driversWrite = new ArrayList<>();
		for (int i = 0; i < driversObj.size(); i++) {
			driversWrite.add(driversObj.get(i).toString());
		}
		
		// Output the updated list of drivers to the relevant file
		writeArrayList("./src/resources/drivers.txt", driversWrite);
		
		// *Save order summaries*
		// Sort the ArrayList of order summaries by customer name
		Collections.sort(ordersSummary);
		writeArrayList("./src/resources/ordersSummary.txt", ordersSummary);
		
	}
	
	// Method to get user input on the customer
	public static String getCustInput(ArrayList<Customer> customers) {
		// Define new scanner object to get user input
		Scanner inputSc = new Scanner(System.in);
		
		// Print out the list of customers already saved
		System.out.println("The list of customers is:");
		for (int i = 0; i < customers.size(); i++) {
			System.out.println(customers.get(i).shortDesc());
		}
		
		// Ask the user to select an existing customer or add a new one
		System.out.println("Enter the ID of the customer. If you would like to add "+
							"a new customer, enter 'n'");
		String output = inputSc.nextLine();
		
		return output;
	}
	
	// Method to find the customer according to the ID value entered by the user
	public static Customer findCust(String custInput, ArrayList<Customer> customers) {
		try {
			// Parse the user input to an integer
			int orderCustID = Integer.parseInt(custInput.trim());
			
			// Search the list of existing customers for the one with ID matching user input
			for (int i = 0; i < customers.size(); i++) {
				if (customers.get(i).getId() == orderCustID) {
					// When we find a match, return the relevant customer
					return customers.get(i);
				}
			}
			
			// If we don't find the customer id, display a message and return null
			System.out.println("Customer not found");
			return null;
			
		}
		// Error handling if user input can't be parsed to an integer
		catch (Exception e) {
			System.out.println("That is not a valid ID");
			return null;
		}	
	}
	
	// Method to get user input on the restaurant
	public static String getRestInput(ArrayList<Restaurant> restaurants) {
		// Define new scanner object to get user input
		Scanner inputSc = new Scanner(System.in);
		
		// Display the list of restaurant and ask the user to select one (or create a new one) 
		System.out.println("The list of restaurants is:");
		for (int i = 0; i < restaurants.size(); i++) {
			System.out.println(restaurants.get(i).shortDesc());
		}
		System.out.println("Enter the ID of the restaurant. If you would like to add "+
							"a new restaurant, enter 'n'");
		
		String output = inputSc.nextLine();
		
		return output;
	}
	
	// Method to find the restaurant according to the ID value entered by the user
	public static Restaurant findRest(String restInput, ArrayList<Restaurant> restaurants) {
		try {
			// Parse user input to an integer
			int orderRestID = Integer.parseInt(restInput.trim());
			
			// Search for a restaurant matching ID entered by the user
			for (int i = 0; i < restaurants.size(); i++) {
				if (restaurants.get(i).getId() == orderRestID) {
					// If we find a match, return it
					return restaurants.get(i);
				}
			}
			// Display message if no restaurant ID matches
			System.out.println("Restaurant not found");
			return null;
		}
		// Error handling if user doesn't enter a valid integer
		catch (Exception e) {
			System.out.println("That is not a valid ID");
			return null;
		}
		
	}
	
	// Method to read a text file save line by line to a STRING ArrayList
	public static ArrayList<String> readFile(String fn) {
		// Create new ArrayList to save each driver to a new element
		ArrayList<String> driversStr = new ArrayList<>();
		
		try {
			// Open the file and create new scanner object
			File fileF = new File(fn);
			Scanner fileS = new Scanner(fileF);
			// Read the drivers one by one and add to the ArrayList
			while (fileS.hasNext()) {
				driversStr.add(fileS.nextLine());
			}
			fileS.close();
		}
		// Error handling if file not found
		catch (FileNotFoundException e) {
			System.out.println("Error: File not found");
		}
		
		return driversStr;
	}
	
	// Method to find the driver with the smallest load in the relevant city
	public static Driver findMinDriver(ArrayList<Driver> driversObj, String city) {
		// Check if there are any drivers in the customer city and create a
		// sub ArrayList of those drivers
		ArrayList<Driver> cityDrivers = new ArrayList<>();
		for (int i = 0; i < driversObj.size(); i++) {
			if ( driversObj.get(i).getCity().trim().equals(city.trim()) ) {
				cityDrivers.add(driversObj.get(i));
			}
		}
		
		// If there are no drivers in the relevant city, return null
		if (cityDrivers.size() == 0) {
			return null;
		}
		
		// Find the driver in the sub ArrayList with the smallest load
		Driver minDriver = cityDrivers.get(0);
		for (int i = 0; i < cityDrivers.size(); i++) {
			if (cityDrivers.get(i).getLoad() < minDriver.getLoad()) {
				minDriver = cityDrivers.get(i);
			}
		}
		
		return minDriver;
	}
	
	// Method to get details on the menu items (meals) the user wants to order
	public static ArrayList<Meal> getMeals() {
		// New scanner object to get user input
		Scanner inputSc = new Scanner(System.in);	
		
		// New arraylist with meals as the elements
		ArrayList<Meal> meals = new ArrayList<>();
		
		// Keep adding meals until user closes the order
		while (true) {
			// Get details on menu item, price and quantity, and save to new Meal object
			Meal thisMeal = new Meal();
			
			// Name of menu item
			System.out.println("Enter the name of the menu item you would like to order:");
			thisMeal.setName(inputSc.nextLine());
			
			// Price
			while (true) {
				System.out.println("Enter the price of the item in Rand");
				try {
					thisMeal.setPrice(Double.parseDouble(inputSc.nextLine().trim()));
				}
				catch (Exception e) {
					System.out.println("That is not a valid price. Please try again.");
					continue;
				}
				break;
			}
			
			// Quantity
			while (true) {
				System.out.println("How many of this item would you like to order?");
				try {
					thisMeal.setQuantity(Integer.parseInt(inputSc.nextLine().trim()));
				}
				catch (Exception e) {
					System.out.println("That is not a valid quantity. Please try again.");
					continue;
				}
				break;
			}
			
			// Add the meal object to the list of meals for the order
			meals.add(thisMeal);
			
			// Ask the user if they want to add more meals
			System.out.println("Thank you");
			System.out.println("Enter 'y' to add another menu item");
			System.out.println("Enter 'n' to close the order");
			String mealInput = inputSc.nextLine();
			
			// If not, exit the loop
			if (Character.toLowerCase(mealInput.charAt(0)) == 'n') {
				break;
			}
		}
		
		return meals;
	}
	
	// Method to write a single string to a file
	public static void writeString(String fn, String str) {
		try {
			// Write string to file
			Formatter fileF = new Formatter(fn);
			fileF.format( "%s", str );
			fileF.close();
		}
		// Error handling if we can't write for some reason
		catch (Exception e) {
			System.out.println("Error writing to file");
		}
	}
	
	// Method to write an ArrayList<String> to a file line by line
	public static void writeArrayList(String fn, ArrayList<String> lines) {
		try {
			Formatter fileF = new Formatter(fn);
			for (int i = 0; i < lines.size(); i++) {
				fileF.format( "%s\r\n", lines.get(i) );
			}
			fileF.close();
		}
		catch (Exception e) {
			System.out.println("Error writing to file");
		}
	}
}
