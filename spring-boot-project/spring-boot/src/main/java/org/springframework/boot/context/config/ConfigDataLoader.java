package org.springframework.boot.context.config;

import java.io.IOException;

import org.apache.commons.logging.Log;

import org.springframework.boot.BootstrapContext;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.ConfigurableBootstrapContext;

/**
 * Strategy class that can be used used to load {@link ConfigData} for a given
 * {@link ConfigDataResource}. Implementations should be added as a
 * {@code spring.factories} entries. The following constructor parameter types are
 * supported:
 * <ul>
 * <li>{@link Log} - if the resolver needs deferred logging</li>
 * <li>{@link ConfigurableBootstrapContext} - A bootstrap context that can be used to
 * store objects that may be expensive to create, or need to be shared
 * ({@link BootstrapContext} or {@link BootstrapRegistry} may also be used).</li>
 * </ul>
 * <p>
 * Multiple loaders cannot claim the same resource.
 *
 * @param <R> the resource type


 * @since 2.4.0
 */
public interface ConfigDataLoader<R extends ConfigDataResource> {

	/**
	 * Returns if the specified resource can be loaded by this instance.
	 * @param context the loader context
	 * @param resource the resource to check.
	 * @return if the resource is supported by this loader
	 */
	default boolean isLoadable(ConfigDataLoaderContext context, R resource) {
		return true;
	}

	/**
	 * Load {@link ConfigData} for the given resource.
	 * @param context the loader context
	 * @param resource the resource to load
	 * @return the loaded config data or {@code null} if the location should be skipped
	 * @throws IOException on IO error
	 * @throws ConfigDataResourceNotFoundException if the resource cannot be found
	 */
	ConfigData load(ConfigDataLoaderContext context, R resource)
			throws IOException, ConfigDataResourceNotFoundException;

}
