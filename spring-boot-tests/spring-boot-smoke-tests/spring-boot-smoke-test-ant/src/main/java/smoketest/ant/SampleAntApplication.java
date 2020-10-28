package smoketest.ant;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SampleAntApplication implements CommandLineRunner {

	@Override
	public void run(String... args) {
		System.out.println("Spring Boot Ant Example");
	}

	public static void main(String[] args) {
		SpringApplication.run(SampleAntApplication.class, args);
	}

}
