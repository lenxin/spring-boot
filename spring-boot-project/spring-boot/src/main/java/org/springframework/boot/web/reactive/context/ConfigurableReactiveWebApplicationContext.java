package org.springframework.boot.web.reactive.context;

import org.springframework.context.ConfigurableApplicationContext;

/**
 * Interface to provide configuration for a reactive web application.
 *

 * @since 2.0.0
 */
public interface ConfigurableReactiveWebApplicationContext
		extends ConfigurableApplicationContext, ReactiveWebApplicationContext {

}
