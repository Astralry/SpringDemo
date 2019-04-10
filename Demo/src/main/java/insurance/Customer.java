package insurance;

import lombok.Data;

import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
class Customer {

	private @Id @GeneratedValue Long id;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String postalCode;
	
	@NotEmpty
	private String dob;
	
	private HashMap<String, Car> carDb;
	
	private String carName;
	private int age;
	private int accidents;

	public Customer(@NotEmpty String name, @NotEmpty String postalCode, @NotEmpty String dob) {
		this.name = name;
		this.postalCode = postalCode;
		this.dob = dob;
		this.carDb = new HashMap<String , Car>();
	}
	
	Customer(){
		this.carDb = new HashMap<String , Car>();
	}

	public Customer(@NotEmpty String name, @NotEmpty String postalCode, @NotEmpty String dob, String carName,
			int age, int accidents) {
		this.name = name;
		this.postalCode = postalCode;
		this.dob = dob;
		this.carDb = new HashMap<String , Car>();
		Car car = new Car(carName, age, accidents);
		this.carDb.put(carName, car);
	}
	
	public void setCarDb(String carName, int age, int accidents) {
		this.carDb.put(carName, new Car(carName, age, accidents));
	}


//	public static Customer create(Map<String, String> map) {
//		String name = map.get("name");
//		String role = map.get("role");
//		Customer temp = new Customer(name, role);
//		System.out.println("in static method, Customer name is: " + name + " and role is: " + role);
//		return temp;
//	}



}