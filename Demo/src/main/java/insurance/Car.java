package insurance;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Data
public class Car implements Serializable {
	
	private @Id String carName;
	
	private int age;
	
	private int accidents;

	public Car(String carName, int age, int accidents) {
		this.carName = carName;
		this.age = age;
		this.accidents = accidents;
	}	
	
	public int getInsurance() {
		return age * 1000;
	}

	
}
