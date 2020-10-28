package org.springframework.boot.test.mock.mockito;

import java.util.List;

import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.ContextCustomizerFactory;

/**
 * A {@link ContextCustomizerFactory} to add Mockito support.
 *

 */
class MockitoContextCustomizerFactory implements ContextCustomizerFactory {

	@Override
	public ContextCustomizer createContextCustomizer(Class<?> testClass,
			List<ContextConfigurationAttributes> configAttributes) {
		// We gather the explicit mock definitions here since they form part of the
		// MergedContextConfiguration key. Different mocks need to have a different key.
		DefinitionsParser parser = new DefinitionsParser();
		parser.parse(testClass);
		return new MockitoContextCustomizer(parser.getDefinitions());
	}

}
