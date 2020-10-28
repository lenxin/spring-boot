package org.springframework.boot.test.autoconfigure.properties;

import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.ContextCustomizerFactory;

/**
 * {@link ContextCustomizerFactory} to map annotation attributes to {@link Environment}
 * properties.
 *

 */
class PropertyMappingContextCustomizerFactory implements ContextCustomizerFactory {

	@Override
	public ContextCustomizer createContextCustomizer(Class<?> testClass,
			List<ContextConfigurationAttributes> configurationAttributes) {
		AnnotationsPropertySource propertySource = new AnnotationsPropertySource(testClass);
		return new PropertyMappingContextCustomizer(propertySource);
	}

}
