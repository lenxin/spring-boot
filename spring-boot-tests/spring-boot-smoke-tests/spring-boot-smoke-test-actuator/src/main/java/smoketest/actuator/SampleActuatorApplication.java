package smoketest.actuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SampleActuatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleActuatorApplication.class, args);
	}

	@Bean
	public HealthIndicator helloHealthIndicator() {
		return () -> Health.up().withDetail("hello", "world").build();
	}

}
