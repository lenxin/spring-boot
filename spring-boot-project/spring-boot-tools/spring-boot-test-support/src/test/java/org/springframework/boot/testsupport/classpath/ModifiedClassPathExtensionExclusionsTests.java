package org.springframework.boot.testsupport.classpath;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.isA;

/**
 * Tests for {@link ModifiedClassPathExtension} excluding entries from the class path.
 *

 */
@ClassPathExclusions("hibernate-validator-*.jar")
class ModifiedClassPathExtensionExclusionsTests {

	private static final String EXCLUDED_RESOURCE = "META-INF/services/javax.validation.spi.ValidationProvider";

	@Test
	void entriesAreFilteredFromTestClassClassLoader() {
		assertThat(getClass().getClassLoader().getResource(EXCLUDED_RESOURCE)).isNull();
	}

	@Test
	void entriesAreFilteredFromThreadContextClassLoader() {
		assertThat(Thread.currentThread().getContextClassLoader().getResource(EXCLUDED_RESOURCE)).isNull();
	}

	@Test
	void testsThatUseHamcrestWorkCorrectly() {
		Matcher<IllegalStateException> matcher = isA(IllegalStateException.class);
		assertThat(matcher.matches(new IllegalStateException())).isTrue();
	}

}
