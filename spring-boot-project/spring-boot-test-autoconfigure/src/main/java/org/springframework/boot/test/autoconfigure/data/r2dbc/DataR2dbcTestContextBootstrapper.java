package org.springframework.boot.test.autoconfigure.data.r2dbc;

import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.annotation.MergedAnnotations.SearchStrategy;
import org.springframework.test.context.TestContextBootstrapper;

/**
 * {@link TestContextBootstrapper} for {@link DataR2dbcTest @DataR2dbcTest} support.
 *

 */
class DataR2dbcTestContextBootstrapper extends SpringBootTestContextBootstrapper {

	@Override
	protected String[] getProperties(Class<?> testClass) {
		return MergedAnnotations.from(testClass, SearchStrategy.INHERITED_ANNOTATIONS).get(DataR2dbcTest.class)
				.getValue("properties", String[].class).orElse(null);
	}

}
