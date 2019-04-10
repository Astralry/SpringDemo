package insurance;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadDatabase {

	@Bean
	CommandLineRunner initDatabase(CustomerRepository repository) {
		return args -> {
			log.info("Preloading " + repository.save(new Customer("Bilbo Baggins","A1B2C3","September222890")));
			log.info("Preloading " + repository.save(new Customer("Frodo Baggins","D4E5F6","September222968")));
			log.info("done");
		};
	}
}