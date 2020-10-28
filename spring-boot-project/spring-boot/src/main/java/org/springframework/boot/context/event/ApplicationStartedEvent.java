package org.springframework.boot.context.event;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Event published once the application context has been refreshed but before any
 * {@link ApplicationRunner application} and {@link CommandLineRunner command line}
 * runners have been called.
 *

 * @since 2.0.0
 */
@SuppressWarnings("serial")
public class ApplicationStartedEvent extends SpringApplicationEvent {

	private final ConfigurableApplicationContext context;

	/**
	 * Create a new {@link ApplicationStartedEvent} instance.
	 * @param application the current application
	 * @param args the arguments the application is running with
	 * @param context the context that was being created
	 */
	public ApplicationStartedEvent(SpringApplication application, String[] args,
			ConfigurableApplicationContext context) {
		super(application, args);
		this.context = context;
	}

	/**
	 * Return the application context.
	 * @return the context
	 */
	public ConfigurableApplicationContext getApplicationContext() {
		return this.context;
	}

}
