package compulsorytask2;

import java.util.Comparator;

// This class uses Java's Comparator interface to allow to sort
// an ArrayList of Customer objects by city
public class SortCustomerByCity implements Comparator<Customer> {
	public int compare(Customer a, Customer b) { 
        return a.getCity().compareTo(b.getCity()); 
    } 
}

