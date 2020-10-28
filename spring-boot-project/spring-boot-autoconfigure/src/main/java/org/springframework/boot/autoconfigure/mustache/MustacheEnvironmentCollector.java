package org.springframework.boot.autoconfigure.mustache;

import com.samskivert.mustache.DefaultCollector;
import com.samskivert.mustache.Mustache.Collector;
import com.samskivert.mustache.Mustache.VariableFetcher;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

/**
 * Mustache {@link Collector} to expose properties from the Spring {@link Environment}.
 *


 * @since 1.2.2
 */
public class MustacheEnvironmentCollector extends DefaultCollector implements EnvironmentAware {

	private ConfigurableEnvironment environment;

	private final VariableFetcher propertyFetcher = new PropertyVariableFetcher();

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = (ConfigurableEnvironment) environment;
	}

	@Override
	public VariableFetcher createFetcher(Object ctx, String name) {
		VariableFetcher fetcher = super.createFetcher(ctx, name);
		if (fetcher != null) {
			return fetcher;
		}
		if (this.environment.containsProperty(name)) {
			return this.propertyFetcher;
		}
		return null;
	}

	private class PropertyVariableFetcher implements VariableFetcher {

		@Override
		public Object get(Object ctx, String name) {
			return MustacheEnvironmentCollector.this.environment.getProperty(name);
		}

	}

}
