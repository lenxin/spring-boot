package org.springframework.boot.env;

import org.apache.commons.logging.Log;

import org.springframework.boot.SpringApplication;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * {@link EnvironmentPostProcessor} to add the {@link RandomValuePropertySource}.
 *

 * @since 2.4.0
 */
public class RandomValuePropertySourceEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

	/**
	 * The default order of this post-processor.
	 */
	public static final int ORDER = Ordered.HIGHEST_PRECEDENCE + 1;

	private final Log logger;

	/**
	 * Create a new {@link RandomValuePropertySourceEnvironmentPostProcessor} instance.
	 * @param logger the logger to use
	 */
	public RandomValuePropertySourceEnvironmentPostProcessor(Log logger) {
		this.logger = logger;
	}

	@Override
	public int getOrder() {
		return ORDER;
	}

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		RandomValuePropertySource.addToEnvironment(environment, this.logger);
	}

}
