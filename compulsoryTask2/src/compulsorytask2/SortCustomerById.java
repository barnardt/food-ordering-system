package compulsorytask2;

import java.util.Comparator;

//This class uses Java's Comparator interface to allow to sort
//an ArrayList of Customer objects by ID
public class SortCustomerById implements Comparator<Customer> {
	public int compare(Customer a, Customer b) { 
		return a.getId() - b.getId(); 
	}
}


