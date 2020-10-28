package org.springframework.boot.test.context;

import java.util.Arrays;
import java.util.Set;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.MergedContextConfiguration;

/**
 * {@link ContextCustomizer} to track application arguments that are used in a
 * {@link SpringBootTest}. The application arguments are taken into account when
 * evaluating a {@link MergedContextConfiguration} to determine if a context can be shared
 * between tests.
 *

 */
class SpringBootTestArgs implements ContextCustomizer {

	private static final String[] NO_ARGS = new String[0];

	private final String[] args;

	SpringBootTestArgs(Class<?> testClass) {
		this.args = MergedAnnotations.from(testClass, MergedAnnotations.SearchStrategy.TYPE_HIERARCHY)
				.get(SpringBootTest.class).getValue("args", String[].class).orElse(NO_ARGS);
	}

	@Override
	public void customizeContext(ConfigurableApplicationContext context, MergedContextConfiguration mergedConfig) {
	}

	String[] getArgs() {
		return this.args;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj != null) && (getClass() == obj.getClass())
				&& Arrays.equals(this.args, ((SpringBootTestArgs) obj).args);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(this.args);
	}

	/**
	 * Return the application arguments from the given customizers.
	 * @param customizers the customizers to check
	 * @return the application args or an empty array
	 */
	static String[] get(Set<ContextCustomizer> customizers) {
		for (ContextCustomizer customizer : customizers) {
			if (customizer instanceof SpringBootTestArgs) {
				return ((SpringBootTestArgs) customizer).args;
			}
		}
		return NO_ARGS;
	}

}
