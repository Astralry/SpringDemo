package insurance;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = "application/hal+json")
class CustomerController {

	private final CustomerRepository repository;

	CustomerController(CustomerRepository repository) {
		this.repository = repository;
	}

	// Aggregate root

	@GetMapping("/customers")
	Resources<Resource<Customer>> allCustomer() {

		List<Resource<Customer>> customers = repository.findAll().stream()
			.map(customer -> new Resource<>(customer,
				linkTo(methodOn(CustomerController.class).getCustomer(customer.getId())).withSelfRel(),
				linkTo(methodOn(CustomerController.class).allCustomer()).withRel("customers")))
			.collect(Collectors.toList());

		return new Resources<>(customers,
			linkTo(methodOn(CustomerController.class).allCustomer()).withSelfRel());
	}
	
	@PostMapping("/customers")
	Customer newCustomer(@RequestBody Customer newCustomer) {
		return repository.save(newCustomer);
	}

	// Single item

	@GetMapping("/customers/{id}")
	Resource<Customer> getCustomer(@PathVariable Long id) {

		Customer customer = repository.findById(id)
			.orElseThrow(() -> new CustomerNotFoundException(id));
		
		return new Resource<>(customer,
				linkTo(methodOn(CustomerController.class).getCustomer(id)).withSelfRel(),
				//linkTo(methodOn(Car.class).getCarName()).withSelfRel(),
				//linkTo(methodOn(CustomerController.class).getAllCustomerCars(id)).withRel("car"),
				linkTo(methodOn(CustomerController.class).allCustomer()).withRel("customer"));
	}
	
	@GetMapping("/customers/{id}/car")
	Optional<Object> getAllCustomerCars(@PathVariable Long id){
		
//		List<Resource<Optional<Object>>> cars = repository.findById(id)
//				.map(customer -> new Resource<>(customer,
//					linkTo(methodOn(CustomerController.class).getCustomerCar(id, customer.getCarDb())).withSelfRel(),
//					linkTo(methodOn(CustomerController.class).getAllCustomerCars(id)).withRel("cars")))
//				.collect(Collectors.toList());
//		return new Resources<>(cars,
//				linkTo(methodOn(CustomerController.class).getAllCustomerCars(id)).withSelfRel());
//		
		
		return repository.findById(id)
			.map(customer -> {
				return customer.getCarDb();
			});
	}
	
	@GetMapping("/customers/{id}/car/{carId}")
	Optional<Object> getCustomerCar(@PathVariable Long id, @PathVariable String carId){
		return repository.findById(id)
			.map(customer -> {
				return customer.getCarDb().get(carId);
			});
	}
	@GetMapping("/customers/{id}/car/{carName}/insurance")
	Optional<Object> getCustomerCarInsurance(@PathVariable Long id, @PathVariable String carId){
		return repository.findById(id)
			.map(customer -> {
				return customer.getCarDb().get(carId).getInsurance();
			});
	}

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
				customer.setCarDb(newCustomer.getCarName(), newCustomer.getAge(), newCustomer.getAccidents());
				return repository.save(customer);
			})
			.orElseGet(() -> {
				newCustomer.setId(id);
				return repository.save(newCustomer);
			});
	}
	
	@DeleteMapping("/customers/{id}/car/{carName}")
	Optional<Object> deleteCustomerCar(@PathVariable Long id, @PathVariable String carId){
		return repository.findById(id)
			.map(customer -> {
				return customer.getCarDb().remove(carId);
			});
	}

	@DeleteMapping("/customers/{id}")
	void deleteCustomer(@PathVariable Long id) {
		repository.deleteById(id);
	}
}