package org.springframework.boot.test.autoconfigure.data.redis;

import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.annotation.MergedAnnotations.SearchStrategy;
import org.springframework.test.context.TestContextBootstrapper;

/**
 * {@link TestContextBootstrapper} for {@link DataRedisTest @DataRedisTest} support.
 *

 */
class DataRedisTestContextBootstrapper extends SpringBootTestContextBootstrapper {

	@Override
	protected String[] getProperties(Class<?> testClass) {
		return MergedAnnotations.from(testClass, SearchStrategy.INHERITED_ANNOTATIONS).get(DataRedisTest.class)
				.getValue("properties", String[].class).orElse(null);
	}

}
