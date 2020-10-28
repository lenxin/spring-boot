package org.springframework.boot.test.context;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.annotation.MergedAnnotations.SearchStrategy;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.ContextCustomizerFactory;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

/**
 * {@link ContextCustomizerFactory} to allow {@code @Import} annotations to be used
 * directly on test classes.
 *

 * @see ImportsContextCustomizer
 */
class ImportsContextCustomizerFactory implements ContextCustomizerFactory {

	@Override
	public ContextCustomizer createContextCustomizer(Class<?> testClass,
			List<ContextConfigurationAttributes> configAttributes) {
		if (MergedAnnotations.from(testClass, SearchStrategy.TYPE_HIERARCHY).isPresent(Import.class)) {
			assertHasNoBeanMethods(testClass);
			return new ImportsContextCustomizer(testClass);
		}
		return null;
	}

	private void assertHasNoBeanMethods(Class<?> testClass) {
		ReflectionUtils.doWithMethods(testClass, this::assertHasNoBeanMethods);
	}

	private void assertHasNoBeanMethods(Method method) {
		Assert.state(!MergedAnnotations.from(method).isPresent(Bean.class),
				"Test classes cannot include @Bean methods");
	}

}
