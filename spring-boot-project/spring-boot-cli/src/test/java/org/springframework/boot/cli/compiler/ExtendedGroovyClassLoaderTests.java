package org.springframework.boot.cli.compiler;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Tests for {@link ExtendedGroovyClassLoader}.
 *

 */
class ExtendedGroovyClassLoaderTests {

	private final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

	private final ExtendedGroovyClassLoader defaultScopeGroovyClassLoader = new ExtendedGroovyClassLoader(
			GroovyCompilerScope.DEFAULT);

	@Test
	void loadsGroovyFromSameClassLoader() throws Exception {
		Class<?> c1 = Class.forName("groovy.lang.Script", false, this.contextClassLoader);
		Class<?> c2 = Class.forName("groovy.lang.Script", false, this.defaultScopeGroovyClassLoader);
		assertThat(c1.getClassLoader()).isSameAs(c2.getClassLoader());
	}

	@Test
	void filtersNonGroovy() throws Exception {
		Class.forName("org.springframework.util.StringUtils", false, this.contextClassLoader);
		assertThatExceptionOfType(ClassNotFoundException.class).isThrownBy(
				() -> Class.forName("org.springframework.util.StringUtils", false, this.defaultScopeGroovyClassLoader));
	}

	@Test
	void loadsJavaTypes() throws Exception {
		Class.forName("java.lang.Boolean", false, this.defaultScopeGroovyClassLoader);
	}

	@Test
	void loadsSqlTypes() throws Exception {
		Class.forName("java.sql.SQLException", false, this.contextClassLoader);
		Class.forName("java.sql.SQLException", false, this.defaultScopeGroovyClassLoader);
	}

}
