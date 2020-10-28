package org.springframework.boot.env;

import java.util.List;

import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.logging.DeferredLogFactory;
import org.springframework.core.io.support.SpringFactoriesLoader;

/**
 * Factory interface used by the {@link EnvironmentPostProcessorApplicationListener} to
 * create the {@link EnvironmentPostProcessor} instances.
 *

 * @since 2.4.0
 */
@FunctionalInterface
public interface EnvironmentPostProcessorsFactory {

	/**
	 * Create all requested {@link EnvironmentPostProcessor} instances.
	 * @param logFactory a deferred log factory
	 * @param bootstrapContext a bootstrap context
	 * @return the post processor instances
	 */
	List<EnvironmentPostProcessor> getEnvironmentPostProcessors(DeferredLogFactory logFactory,
			ConfigurableBootstrapContext bootstrapContext);

	/**
	 * Return a {@link EnvironmentPostProcessorsFactory} backed by
	 * {@code spring.factories}.
	 * @param classLoader the source class loader
	 * @return an {@link EnvironmentPostProcessorsFactory} instance
	 */
	static EnvironmentPostProcessorsFactory fromSpringFactories(ClassLoader classLoader) {
		return new ReflectionEnvironmentPostProcessorsFactory(
				SpringFactoriesLoader.loadFactoryNames(EnvironmentPostProcessor.class, classLoader));
	}

	/**
	 * Return a {@link EnvironmentPostProcessorsFactory} that reflectively creates post
	 * processors from the given classes.
	 * @param classes the post processor classes
	 * @return an {@link EnvironmentPostProcessorsFactory} instance
	 */
	static EnvironmentPostProcessorsFactory of(Class<?>... classes) {
		return new ReflectionEnvironmentPostProcessorsFactory(classes);
	}

	/**
	 * Return a {@link EnvironmentPostProcessorsFactory} that reflectively creates post
	 * processors from the given class names.
	 * @param classNames the post processor class names
	 * @return an {@link EnvironmentPostProcessorsFactory} instance
	 */
	static EnvironmentPostProcessorsFactory of(String... classNames) {
		return new ReflectionEnvironmentPostProcessorsFactory(classNames);
	}

}
