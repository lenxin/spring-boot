package org.springframework.boot.test.context;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.MergedContextConfiguration;

/**
 * {@link ContextCustomizer} to track the web environment that is used in a
 * {@link SpringBootTest}. The web environment is taken into account when evaluating a
 * {@link MergedContextConfiguration} to determine if a context can be shared between
 * tests.
 *

 */
class SpringBootTestWebEnvironment implements ContextCustomizer {

	private final WebEnvironment webEnvironment;

	SpringBootTestWebEnvironment(Class<?> testClass) {
		this.webEnvironment = MergedAnnotations.from(testClass, MergedAnnotations.SearchStrategy.TYPE_HIERARCHY)
				.get(SpringBootTest.class).getValue("webEnvironment", WebEnvironment.class).orElse(null);
	}

	@Override
	public void customizeContext(ConfigurableApplicationContext context, MergedContextConfiguration mergedConfig) {
	}

	@Override
	public boolean equals(Object obj) {
		return (obj != null) && (getClass() == obj.getClass())
				&& this.webEnvironment == ((SpringBootTestWebEnvironment) obj).webEnvironment;
	}

	@Override
	public int hashCode() {
		return (this.webEnvironment != null) ? this.webEnvironment.hashCode() : 0;
	}

}
