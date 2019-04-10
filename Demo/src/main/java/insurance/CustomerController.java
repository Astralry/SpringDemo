package insurance;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class CustomerController {

	private final CustomerRepository repository;

	CustomerController(CustomerRepository repository) {
		this.repository = repository;
	}

	// Aggregate root

	@GetMapping("/customers")
	List<Customer> all() {
		return repository.findAll();
	}
	
	@PostMapping("/customers")
	Customer newEmployee(@RequestBody Customer newCustomer) {
		return repository.save(newCustomer);
	}

	// Single item

	@GetMapping("/customers/{id}")
	Customer one(@PathVariable Long id) {

		return repository.findById(id)
			.orElseThrow(() -> new CustomerNotFoundException(id));
	}
	
	@GetMapping("/customers/{id}/car/insurance")

	@PutMapping("/customers/{id}")
	Customer replaceCustomer(@RequestBody Customer newCustomer, @PathVariable Long id) {

		return repository.findById(id)
			.map(customer -> {
				customer.setName(newCustomer.getName());
				customer.setPostalCode(newCustomer.getPostalCode());
				customer.setDob(newCustomer.getDob());
				return repository.save(customer);
			})
			.orElseGet(() -> {
				newCustomer.setId(id);
				return repository.save(newCustomer);
			});
	}
	
	@PutMapping("/customers/{id}/car")
	Customer modifyCar(@RequestBody Customer newCustomer, @PathVariable Long id) {

		return repository.findById(id)
			.map(customer -> {
				customer.setCarName(newCustomer.getCarName());
				customer.setAge(newCustomer.getAge());
				customer.setAccidents(newCustomer.getAccidents());
				return repository.save(customer);
			})
			.orElseGet(() -> {
				newCustomer.setId(id);
				return repository.save(newCustomer);
			});
	}
	
	

	@DeleteMapping("/customers/{id}")
	void deleteEmployee(@PathVariable Long id) {
		repository.deleteById(id);
	}
}