package org.springframework.boot.env;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;

import org.springframework.boot.BootstrapContext;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.logging.DeferredLogFactory;
import org.springframework.boot.util.Instantiator;

/**
 * {@link EnvironmentPostProcessorsFactory} implementation that uses reflection to create
 * instances.
 *

 */
class ReflectionEnvironmentPostProcessorsFactory implements EnvironmentPostProcessorsFactory {

	private final List<String> classNames;

	ReflectionEnvironmentPostProcessorsFactory(Class<?>... classes) {
		this(Arrays.stream(classes).map(Class::getName).toArray(String[]::new));
	}

	ReflectionEnvironmentPostProcessorsFactory(String... classNames) {
		this(Arrays.asList(classNames));
	}

	ReflectionEnvironmentPostProcessorsFactory(List<String> classNames) {
		this.classNames = classNames;
	}

	@Override
	public List<EnvironmentPostProcessor> getEnvironmentPostProcessors(DeferredLogFactory logFactory,
			ConfigurableBootstrapContext bootstrapContext) {
		Instantiator<EnvironmentPostProcessor> instantiator = new Instantiator<>(EnvironmentPostProcessor.class,
				(parameters) -> {
					parameters.add(DeferredLogFactory.class, logFactory);
					parameters.add(Log.class, logFactory::getLog);
					parameters.add(ConfigurableBootstrapContext.class, bootstrapContext);
					parameters.add(BootstrapContext.class, bootstrapContext);
					parameters.add(BootstrapRegistry.class, bootstrapContext);
				});
		return instantiator.instantiate(this.classNames);
	}

}
