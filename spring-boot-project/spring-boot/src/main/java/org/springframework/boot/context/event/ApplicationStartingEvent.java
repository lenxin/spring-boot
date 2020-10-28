package org.springframework.boot.context.event;

import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;

/**
 * Event published as early as conceivably possible as soon as a {@link SpringApplication}
 * has been started - before the {@link Environment} or {@link ApplicationContext} is
 * available, but after the {@link ApplicationListener}s have been registered. The source
 * of the event is the {@link SpringApplication} itself, but beware of using its internal
 * state too much at this early stage since it might be modified later in the lifecycle.
 *


 * @since 1.5.0
 */
@SuppressWarnings("serial")
public class ApplicationStartingEvent extends SpringApplicationEvent {

	private final ConfigurableBootstrapContext bootstrapContext;

	/**
	 * Create a new {@link ApplicationStartingEvent} instance.
	 * @param application the current application
	 * @param args the arguments the application is running with
	 * @deprecated since 2.4.0 in favor of
	 * {@link #ApplicationStartingEvent(ConfigurableBootstrapContext, SpringApplication, String[])}
	 */
	@Deprecated
	public ApplicationStartingEvent(SpringApplication application, String[] args) {
		this(null, application, args);
	}

	/**
	 * Create a new {@link ApplicationStartingEvent} instance.
	 * @param bootstrapContext the bootstrap context
	 * @param application the current application
	 * @param args the arguments the application is running with
	 */
	public ApplicationStartingEvent(ConfigurableBootstrapContext bootstrapContext, SpringApplication application,
			String[] args) {
		super(application, args);
		this.bootstrapContext = bootstrapContext;
	}

	/**
	 * Return the bootstap context.
	 * @return the bootstrap context
	 * @since 2.4.0
	 */
	public ConfigurableBootstrapContext getBootstrapContext() {
		return this.bootstrapContext;
	}

}
