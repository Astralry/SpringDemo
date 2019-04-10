package insurance;

import lombok.Data;

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
	
	private String carName;
	
	private int age;
	
	private int accidents;	
	
	private int insurance;

	public Customer(@NotEmpty String name, @NotEmpty String postalCode, @NotEmpty String dob) {
		this.name = name;
		this.postalCode = postalCode;
		this.dob = dob;
	}
	
	Customer(){
		
	}

	public Customer(@NotEmpty String name, @NotEmpty String postalCode, @NotEmpty String dob, String carName,
			int age, int accidents) {
		this.name = name;
		this.postalCode = postalCode;
		this.dob = dob;
		this.carName = carName;
		this.age = age;
		this.accidents = accidents;
	}
	
	public int getInsurance() {
		if(!(carName==null)) {
			insurance = age*1000;
		}else
			insurance=0;
		return insurance;
	}

//	public static Customer create(Map<String, String> map) {
//		String name = map.get("name");
//		String role = map.get("role");
//		Customer temp = new Customer(name, role);
//		System.out.println("in static method, Customer name is: " + name + " and role is: " + role);
//		return temp;
//	}



}