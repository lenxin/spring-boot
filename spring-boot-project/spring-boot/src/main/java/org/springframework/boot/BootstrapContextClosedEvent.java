package org.springframework.boot;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * {@link ApplicationEvent} published by a {@link BootstrapContext} when it's closed.
 *

 * @since 2.4.0
 * @see BootstrapRegistry#addCloseListener(org.springframework.context.ApplicationListener)
 */
public class BootstrapContextClosedEvent extends ApplicationEvent {

	private final ConfigurableApplicationContext applicationContext;

	BootstrapContextClosedEvent(BootstrapContext source, ConfigurableApplicationContext applicationContext) {
		super(source);
		this.applicationContext = applicationContext;
	}

	/**
	 * Return the {@link BootstrapContext} that was closed.
	 * @return the bootstrap context
	 */
	public BootstrapContext getBootstrapContext() {
		return (BootstrapContext) this.source;
	}

	/**
	 * Return the prepared application context.
	 * @return the application context
	 */
	public ConfigurableApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

}
