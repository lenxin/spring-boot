package org.springframework.boot.test.autoconfigure.jooq;

import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.annotation.MergedAnnotations.SearchStrategy;
import org.springframework.test.context.TestContextBootstrapper;

/**
 * {@link TestContextBootstrapper} for {@link JooqTest @JooqTest} support.
 *

 */
class JooqTestContextBootstrapper extends SpringBootTestContextBootstrapper {

	@Override
	protected String[] getProperties(Class<?> testClass) {
		return MergedAnnotations.from(testClass, SearchStrategy.INHERITED_ANNOTATIONS).get(JooqTest.class)
				.getValue("properties", String[].class).orElse(null);
	}

}
