package org.springframework.boot.context.event;

import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

/**
 * Event published when a {@link SpringApplication} is starting up and the
 * {@link Environment} is first available for inspection and modification.
 *

 * @since 1.0.0
 */
@SuppressWarnings("serial")
public class ApplicationEnvironmentPreparedEvent extends SpringApplicationEvent {

	private final ConfigurableBootstrapContext bootstrapContext;

	private final ConfigurableEnvironment environment;

	/**
	 * Create a new {@link ApplicationEnvironmentPreparedEvent} instance.
	 * @param application the current application
	 * @param args the arguments the application is running with
	 * @param environment the environment that was just created
	 * @deprecated since 2.4.0 in favor of
	 * {@link #ApplicationEnvironmentPreparedEvent(ConfigurableBootstrapContext, SpringApplication, String[], ConfigurableEnvironment)}
	 */
	@Deprecated
	public ApplicationEnvironmentPreparedEvent(SpringApplication application, String[] args,
			ConfigurableEnvironment environment) {
		this(null, application, args, environment);
	}

	/**
	 * Create a new {@link ApplicationEnvironmentPreparedEvent} instance.
	 * @param bootstrapContext the bootstrap context
	 * @param application the current application
	 * @param args the arguments the application is running with
	 * @param environment the environment that was just created
	 */
	public ApplicationEnvironmentPreparedEvent(ConfigurableBootstrapContext bootstrapContext,
			SpringApplication application, String[] args, ConfigurableEnvironment environment) {
		super(application, args);
		this.bootstrapContext = bootstrapContext;
		this.environment = environment;
	}

	/**
	 * Return the bootstap context.
	 * @return the bootstrap context
	 * @since 2.4.0
	 */
	public ConfigurableBootstrapContext getBootstrapContext() {
		return this.bootstrapContext;
	}

	/**
	 * Return the environment.
	 * @return the environment
	 */
	public ConfigurableEnvironment getEnvironment() {
		return this.environment;
	}

}
