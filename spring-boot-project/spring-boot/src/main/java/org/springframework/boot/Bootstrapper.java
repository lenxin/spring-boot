package org.springframework.boot;

/**
 * Callback interface that can be used to initialize a {@link BootstrapRegistry} before it
 * is used.
 *

 * @since 2.4.0
 * @see SpringApplication#addBootstrapper(Bootstrapper)
 * @see BootstrapRegistry
 */
public interface Bootstrapper {

	/**
	 * Initialize the given {@link BootstrapRegistry} with any required registrations.
	 * @param registry the registry to initialize
	 */
	void intitialize(BootstrapRegistry registry);

}
