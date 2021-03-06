package org.springframework.boot.context.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import org.apache.commons.logging.Log;

import org.springframework.boot.BootstrapContext;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.logging.DeferredLogFactory;
import org.springframework.boot.util.Instantiator;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.SpringFactoriesLoader;

/**
 * A collection of {@link ConfigDataLocationResolver} instances loaded via
 * {@code spring.factories}.
 *


 */
class ConfigDataLocationResolvers {

	private final List<ConfigDataLocationResolver<?>> resolvers;

	/**
	 * Create a new {@link ConfigDataLocationResolvers} instance.
	 * @param logFactory a {@link DeferredLogFactory} used to inject {@link Log} instances
	 * @param bootstrapContext the bootstrap context
	 * @param binder a binder providing values from the initial {@link Environment}
	 * @param resourceLoader {@link ResourceLoader} to load resource locations
	 */
	ConfigDataLocationResolvers(DeferredLogFactory logFactory, ConfigurableBootstrapContext bootstrapContext,
			Binder binder, ResourceLoader resourceLoader) {
		this(logFactory, bootstrapContext, binder, resourceLoader,
				SpringFactoriesLoader.loadFactoryNames(ConfigDataLocationResolver.class, null));
	}

	/**
	 * Create a new {@link ConfigDataLocationResolvers} instance.
	 * @param logFactory a {@link DeferredLogFactory} used to inject {@link Log} instances
	 * @param bootstrapContext the bootstrap context
	 * @param binder {@link Binder} providing values from the initial {@link Environment}
	 * @param resourceLoader {@link ResourceLoader} to load resource locations
	 * @param names the {@link ConfigDataLocationResolver} class names
	 */
	ConfigDataLocationResolvers(DeferredLogFactory logFactory, ConfigurableBootstrapContext bootstrapContext,
			Binder binder, ResourceLoader resourceLoader, List<String> names) {
		Instantiator<ConfigDataLocationResolver<?>> instantiator = new Instantiator<>(ConfigDataLocationResolver.class,
				(availableParameters) -> {
					availableParameters.add(Log.class, logFactory::getLog);
					availableParameters.add(Binder.class, binder);
					availableParameters.add(ResourceLoader.class, resourceLoader);
					availableParameters.add(ConfigurableBootstrapContext.class, bootstrapContext);
					availableParameters.add(BootstrapContext.class, bootstrapContext);
					availableParameters.add(BootstrapRegistry.class, bootstrapContext);
				});
		this.resolvers = reorder(instantiator.instantiate(names));
	}

	private List<ConfigDataLocationResolver<?>> reorder(List<ConfigDataLocationResolver<?>> resolvers) {
		List<ConfigDataLocationResolver<?>> reordered = new ArrayList<>(resolvers.size());
		StandardConfigDataLocationResolver resourceResolver = null;
		for (ConfigDataLocationResolver<?> resolver : resolvers) {
			if (resolver instanceof StandardConfigDataLocationResolver) {
				resourceResolver = (StandardConfigDataLocationResolver) resolver;
			}
			else {
				reordered.add(resolver);
			}
		}
		if (resourceResolver != null) {
			reordered.add(resourceResolver);
		}
		return Collections.unmodifiableList(reordered);
	}

	List<ConfigDataResolutionResult> resolve(ConfigDataLocationResolverContext context, ConfigDataLocation location,
			Profiles profiles) {
		if (location == null) {
			return Collections.emptyList();
		}
		for (ConfigDataLocationResolver<?> resolver : getResolvers()) {
			if (resolver.isResolvable(context, location)) {
				return resolve(resolver, context, location, profiles);
			}
		}
		throw new UnsupportedConfigDataLocationException(location);
	}

	private List<ConfigDataResolutionResult> resolve(ConfigDataLocationResolver<?> resolver,
			ConfigDataLocationResolverContext context, ConfigDataLocation location, Profiles profiles) {
		List<ConfigDataResolutionResult> resolved = resolve(location, () -> resolver.resolve(context, location));
		if (profiles == null) {
			return resolved;
		}
		List<ConfigDataResolutionResult> profileSpecific = resolve(location,
				() -> resolver.resolveProfileSpecific(context, location, profiles));
		return merge(resolved, profileSpecific);
	}

	private List<ConfigDataResolutionResult> resolve(ConfigDataLocation location,
			Supplier<List<? extends ConfigDataResource>> resolveAction) {
		List<ConfigDataResource> resources = nonNullList(resolveAction.get());
		List<ConfigDataResolutionResult> resolved = new ArrayList<>(resources.size());
		for (ConfigDataResource resource : resources) {
			resolved.add(new ConfigDataResolutionResult(location, resource));
		}
		return resolved;
	}

	@SuppressWarnings("unchecked")
	private <T> List<T> nonNullList(List<? extends T> list) {
		return (list != null) ? (List<T>) list : Collections.emptyList();
	}

	private <T> List<T> merge(List<T> list1, List<T> list2) {
		List<T> merged = new ArrayList<>(list1.size() + list2.size());
		merged.addAll(list1);
		merged.addAll(list2);
		return merged;
	}

	/**
	 * Return the resolvers managed by this object.
	 * @return the resolvers
	 */
	List<ConfigDataLocationResolver<?>> getResolvers() {
		return this.resolvers;
	}

}
