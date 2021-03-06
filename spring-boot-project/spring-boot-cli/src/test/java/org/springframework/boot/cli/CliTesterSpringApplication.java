package org.springframework.boot.cli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.context.WebServerPortFileWriter;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Custom {@link SpringApplication} used by {@link CliTester}.
 *

 */
public class CliTesterSpringApplication extends SpringApplication {

	public CliTesterSpringApplication(Class<?>... sources) {
		super(sources);
	}

	@Override
	protected void postProcessApplicationContext(ConfigurableApplicationContext context) {
		context.addApplicationListener(new WebServerPortFileWriter());
	}

}
