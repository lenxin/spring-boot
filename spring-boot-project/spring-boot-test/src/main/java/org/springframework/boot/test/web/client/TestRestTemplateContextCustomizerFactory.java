package org.springframework.boot.test.web.client;

import java.util.List;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.annotation.MergedAnnotations.SearchStrategy;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.ContextCustomizerFactory;

/**
 * {@link ContextCustomizerFactory} for {@link TestRestTemplate}.
 *

 * @see TestRestTemplateContextCustomizer
 */
class TestRestTemplateContextCustomizerFactory implements ContextCustomizerFactory {

	@Override
	public ContextCustomizer createContextCustomizer(Class<?> testClass,
			List<ContextConfigurationAttributes> configAttributes) {
		MergedAnnotations annotations = MergedAnnotations.from(testClass, SearchStrategy.INHERITED_ANNOTATIONS);
		if (annotations.isPresent(SpringBootTest.class)) {
			return new TestRestTemplateContextCustomizer();
		}
		return null;
	}

}
