package compulsorytask2;

import java.util.ArrayList;
import java.util.Locale;

public class Order {
	// Attributes
	private int orderNum;
	private Customer customer;
	private Restaurant restaurant;
	private ArrayList<Meal> meals;
	private double totalPrice;
	private String instructions;
	private Driver driver;
	
	// Method to output only customer name and order number as a string
	public String toSummary() {
		return String.format("%s, %s", customer.getName(), orderNum);
	}
	
	// Method to output order details in invoice format
	public String toInvoice() {
		// Add details to string according to specifications
		String invoiceString =  "Order number " + orderNum + "\r\n" +
			"Customer: " + customer.getName() + "\r\n" +
			"Email: " + customer.getEmail() + "\r\n" +
			"Phone number: " + customer.getContactNum() + "\r\n" +
			"Location: " + customer.getCity() + "\r\n" +
			"\r\n" +
			"You have ordered the following from " + restaurant.getName() + " in " + restaurant.getCity() + ":\r\n" +
			"\r\n";
		
		// To add the meals we need to iterate over the list of meals
		for (int i = 0; i < meals.size(); i++) {
			invoiceString += meals.get(i).getQuantity() + " x " +
								meals.get(i).getName() + " " +
								String.format(Locale.ROOT, "(R%.2f)", meals.get(i).getPrice()) +"\r\n";
		}
		
		invoiceString += "\r\n" +
							"Special instructions: " + instructions + "\r\n" +
							"\r\n" +
							"Total: R" + String.format(Locale.ROOT, "%.2f", totalPrice) + "\r\n" +
							"\r\n" +
							driver.getName() + " is nearest to the restaurant and will be " +
							"delivering your order to you at:" + "\r\n" +
							"\r\n" +
							customer.getAddress() + ",\r\n" +
							customer.getSuburb() + "\r\n" +
							"\r\n" +
							"If you need to contact the restaurant, their number is " +
							restaurant.getContactNum() +".";
							
		return invoiceString;
	}
	
	// Getter and setter methods
	public int getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public ArrayList<Meal> getMeal() {
		return meals;
	}
	public void setMeal(ArrayList<Meal> meals) {
		this.meals = meals;
	}
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	public Driver getDriver() {
		return driver;
	}
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	public ArrayList<Meal> getMeals() {
		return meals;
	}
	public void setMeals(ArrayList<Meal> meals) {
		this.meals = meals;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
}