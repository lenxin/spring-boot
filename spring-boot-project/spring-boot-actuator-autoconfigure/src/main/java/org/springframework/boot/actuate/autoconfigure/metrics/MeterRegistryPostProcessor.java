package org.springframework.boot.actuate.autoconfigure.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;

/**
 * {@link BeanPostProcessor} that delegates to a lazily created
 * {@link MeterRegistryConfigurer} to post-process {@link MeterRegistry} beans.
 *



 */
class MeterRegistryPostProcessor implements BeanPostProcessor {

	private final ObjectProvider<MeterBinder> meterBinders;

	private final ObjectProvider<MeterFilter> meterFilters;

	private final ObjectProvider<MeterRegistryCustomizer<?>> meterRegistryCustomizers;

	private final ObjectProvider<MetricsProperties> metricsProperties;

	private volatile MeterRegistryConfigurer configurer;

	private final ApplicationContext applicationContext;

	MeterRegistryPostProcessor(ObjectProvider<MeterBinder> meterBinders, ObjectProvider<MeterFilter> meterFilters,
			ObjectProvider<MeterRegistryCustomizer<?>> meterRegistryCustomizers,
			ObjectProvider<MetricsProperties> metricsProperties, ApplicationContext applicationContext) {
		this.meterBinders = meterBinders;
		this.meterFilters = meterFilters;
		this.meterRegistryCustomizers = meterRegistryCustomizers;
		this.metricsProperties = metricsProperties;
		this.applicationContext = applicationContext;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof MeterRegistry) {
			getConfigurer().configure((MeterRegistry) bean);
		}
		return bean;
	}

	private MeterRegistryConfigurer getConfigurer() {
		if (this.configurer == null) {
			boolean hasCompositeMeterRegistry = this.applicationContext
					.getBeanNamesForType(CompositeMeterRegistry.class, false, false).length != 0;
			this.configurer = new MeterRegistryConfigurer(this.meterRegistryCustomizers, this.meterFilters,
					this.meterBinders, this.metricsProperties.getObject().isUseGlobalRegistry(),
					hasCompositeMeterRegistry);
		}
		return this.configurer;
	}

}
