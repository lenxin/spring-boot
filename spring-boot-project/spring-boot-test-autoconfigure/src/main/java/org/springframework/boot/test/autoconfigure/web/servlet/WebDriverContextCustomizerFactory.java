package org.springframework.boot.test.autoconfigure.web.servlet;

import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.ContextCustomizerFactory;
import org.springframework.test.context.MergedContextConfiguration;

/**
 * {@link ContextCustomizerFactory} to register a {@link WebDriverScope} and configure
 * appropriate bean definitions to use it. Expects the scope to be reset with a
 * {@link WebDriverTestExecutionListener}.
 *

 * @see WebDriverTestExecutionListener
 * @see WebDriverScope
 */
class WebDriverContextCustomizerFactory implements ContextCustomizerFactory {

	@Override
	public ContextCustomizer createContextCustomizer(Class<?> testClass,
			List<ContextConfigurationAttributes> configAttributes) {
		return new Customizer();
	}

	private static class Customizer implements ContextCustomizer {

		@Override
		public void customizeContext(ConfigurableApplicationContext context, MergedContextConfiguration mergedConfig) {
			WebDriverScope.registerWith(context);
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (obj == null || obj.getClass() != getClass()) {
				return false;
			}
			return true;
		}

		@Override
		public int hashCode() {
			return getClass().hashCode();
		}

	}

}
