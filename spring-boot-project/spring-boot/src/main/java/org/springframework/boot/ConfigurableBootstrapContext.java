package org.springframework.boot;

/**
 * A {@link BootstrapContext} that also provides configuration methods via the
 * {@link BootstrapRegistry} interface.
 *

 * @since 2.4.0
 * @see BootstrapRegistry
 * @see BootstrapContext
 * @see DefaultBootstrapContext
 */
public interface ConfigurableBootstrapContext extends BootstrapRegistry, BootstrapContext {

}
