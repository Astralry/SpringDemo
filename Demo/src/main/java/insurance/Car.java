package insurance;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Data
public class Car implements Serializable {
	private int age;
	
	private int accidents;

	public Car(int age, int accidents) {
		this.age = age;
		this.accidents = accidents;
	}	
	
	public int getInsurance() {
		return age * 1000;
	}

	
}
