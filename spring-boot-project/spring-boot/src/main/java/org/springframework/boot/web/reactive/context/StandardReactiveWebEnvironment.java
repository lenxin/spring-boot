package org.springframework.boot.web.reactive.context;

import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

/**
 * {@link Environment} implementation to be used by {@code Reactive}-based web
 * applications. All web-related (reactive-based) {@code ApplicationContext} classes
 * initialize an instance by default.
 *

 * @since 2.0.0
 */
public class StandardReactiveWebEnvironment extends StandardEnvironment implements ConfigurableReactiveWebEnvironment {

}
