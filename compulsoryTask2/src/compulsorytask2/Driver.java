package compulsorytask2;

public class Driver {
	// Attributes
	private String name;
	private String city;
	private int load;
	
	// Method to create a new driver object from a comma-separated string
	public Driver(String str) {
		// Split each driver string with the comma delimiter
		String[] arrOfStr = str.split(", ", 3);
		
		// Correct error in the source data (assume missing info = zero load)
		if (arrOfStr[2].equals("")) {
			arrOfStr[2] = "0";
		}
		
		// Create new Driver object and update details according to current element
		this.setName(arrOfStr[0]);
		this.setCity(arrOfStr[1]);
		this.setLoad(Integer.parseInt(arrOfStr[2].trim()));
	}
	
	// Method to output attributes as comma-separated string
	public String toString() {
		return String.format("%s, %s, %s", getName(), getCity(), load);
	}
	
	// Getters and setters
	public int getLoad() {
		return load;
	}

	public void setLoad(int load) {
		this.load = load;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
}
