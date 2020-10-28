package org.springframework.boot.test.autoconfigure.actuate.metrics;

import java.util.List;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.annotation.MergedAnnotations.SearchStrategy;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.ContextCustomizerFactory;
import org.springframework.test.context.MergedContextConfiguration;

/**
 * {@link ContextCustomizerFactory} that globally disables metrics export unless
 * {@link AutoConfigureMetrics} is set on the test class.
 *

 */
class MetricsExportContextCustomizerFactory implements ContextCustomizerFactory {

	@Override
	public ContextCustomizer createContextCustomizer(Class<?> testClass,
			List<ContextConfigurationAttributes> configAttributes) {
		boolean disableMetricsExport = !MergedAnnotations.from(testClass, SearchStrategy.TYPE_HIERARCHY)
				.get(AutoConfigureMetrics.class).isPresent();
		return disableMetricsExport ? new DisableMetricExportContextCustomizer() : null;
	}

	static class DisableMetricExportContextCustomizer implements ContextCustomizer {

		@Override
		public void customizeContext(ConfigurableApplicationContext context,
				MergedContextConfiguration mergedContextConfiguration) {
			TestPropertyValues.of("management.metrics.export.defaults.enabled=false",
					"management.metrics.export.simple.enabled=true").applyTo(context);
		}

		@Override
		public boolean equals(Object obj) {
			return (obj != null) && (getClass() == obj.getClass());
		}

		@Override
		public int hashCode() {
			return getClass().hashCode();
		}

	}

}
