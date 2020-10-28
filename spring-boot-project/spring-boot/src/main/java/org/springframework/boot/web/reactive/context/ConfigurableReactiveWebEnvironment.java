package org.springframework.boot.web.reactive.context;

import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Specialization of {@link ConfigurableEnvironment} for reactive application contexts.
 *

 * @since 2.0.0
 * @see ConfigurableReactiveWebApplicationContext#getEnvironment()
 */
public interface ConfigurableReactiveWebEnvironment extends ConfigurableEnvironment {

}
