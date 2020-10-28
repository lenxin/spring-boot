package smoketest.data.r2dbc;

import io.r2dbc.spi.ConnectionFactory;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@SpringBootApplication
public class SampleR2dbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleR2dbcApplication.class, args);
	}

	@Bean
	public ApplicationRunner initializeDatabase(ConnectionFactory connectionFactory, ResourceLoader resourceLoader) {
		return (arguments) -> {
			Resource[] scripts = new Resource[] { resourceLoader.getResource("classpath:database-init.sql") };
			new ResourceDatabasePopulator(scripts).populate(connectionFactory).block();
		};

	}

}
