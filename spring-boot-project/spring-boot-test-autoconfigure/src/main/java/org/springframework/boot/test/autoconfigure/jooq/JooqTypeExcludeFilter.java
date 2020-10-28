package org.springframework.boot.test.autoconfigure.jooq;

import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.filter.StandardAnnotationCustomizableTypeExcludeFilter;

/**
 * {@link TypeExcludeFilter} for {@link JooqTest @JooqTest}.
 *

 * @since 2.2.1
 */
public final class JooqTypeExcludeFilter extends StandardAnnotationCustomizableTypeExcludeFilter<JooqTest> {

	JooqTypeExcludeFilter(Class<?> testClass) {
		super(testClass);
	}

}
