package smoketest.actuator.noweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SampleActuatorNoWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleActuatorNoWebApplication.class, args);
	}

}
