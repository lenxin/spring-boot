package org.springframework.boot.actuate.autoconfigure.metrics;

import java.util.List;
import java.util.stream.Collectors;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.util.LambdaSafe;

/**
 * Configurer to apply {@link MeterRegistryCustomizer customizers}, {@link MeterFilter
 * filters}, {@link MeterBinder binders} and {@link Metrics#addRegistry global
 * registration} to {@link MeterRegistry meter registries}.
 *


 */
class MeterRegistryConfigurer {

	private final ObjectProvider<MeterRegistryCustomizer<?>> customizers;

	private final ObjectProvider<MeterFilter> filters;

	private final ObjectProvider<MeterBinder> binders;

	private final boolean addToGlobalRegistry;

	private final boolean hasCompositeMeterRegistry;

	MeterRegistryConfigurer(ObjectProvider<MeterRegistryCustomizer<?>> customizers, ObjectProvider<MeterFilter> filters,
			ObjectProvider<MeterBinder> binders, boolean addToGlobalRegistry, boolean hasCompositeMeterRegistry) {
		this.customizers = customizers;
		this.filters = filters;
		this.binders = binders;
		this.addToGlobalRegistry = addToGlobalRegistry;
		this.hasCompositeMeterRegistry = hasCompositeMeterRegistry;
	}

	void configure(MeterRegistry registry) {
		// Customizers must be applied before binders, as they may add custom
		// tags or alter timer or summary configuration.
		customize(registry);
		if (!(registry instanceof AutoConfiguredCompositeMeterRegistry)) {
			addFilters(registry);
		}
		if (!this.hasCompositeMeterRegistry || registry instanceof CompositeMeterRegistry) {
			addBinders(registry);
		}
		if (this.addToGlobalRegistry && registry != Metrics.globalRegistry) {
			Metrics.addRegistry(registry);
		}
	}

	@SuppressWarnings("unchecked")
	private void customize(MeterRegistry registry) {
		LambdaSafe.callbacks(MeterRegistryCustomizer.class, asOrderedList(this.customizers), registry)
				.withLogger(MeterRegistryConfigurer.class).invoke((customizer) -> customizer.customize(registry));
	}

	private void addFilters(MeterRegistry registry) {
		this.filters.orderedStream().forEach(registry.config()::meterFilter);
	}

	private void addBinders(MeterRegistry registry) {
		this.binders.orderedStream().forEach((binder) -> binder.bindTo(registry));
	}

	private <T> List<T> asOrderedList(ObjectProvider<T> provider) {
		return provider.orderedStream().collect(Collectors.toList());
	}

}
