package smoketest.flyway;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SampleFlywayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleFlywayApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(PersonRepository repository) {
		return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {
				System.err.println(repository.findAll());
			}

		};
	}

}
