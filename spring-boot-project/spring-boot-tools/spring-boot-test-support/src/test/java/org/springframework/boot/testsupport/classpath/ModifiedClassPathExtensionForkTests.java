package org.springframework.boot.testsupport.classpath;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ForkedClassPath @ForkedClassPath}.
 *

 */
@ForkedClassPath
class ModifiedClassPathExtensionForkTests {

	@Test
	void modifiedClassLoaderIsUsed() {
		ClassLoader classLoader = getClass().getClassLoader();
		assertThat(classLoader.getClass().getName()).isEqualTo(ModifiedClassPathClassLoader.class.getName());
	}

}
