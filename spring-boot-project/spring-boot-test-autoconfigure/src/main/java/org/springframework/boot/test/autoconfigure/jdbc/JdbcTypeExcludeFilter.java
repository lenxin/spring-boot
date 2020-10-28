package org.springframework.boot.test.autoconfigure.jdbc;

import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.filter.StandardAnnotationCustomizableTypeExcludeFilter;

/**
 * {@link TypeExcludeFilter} for {@link JdbcTest @JdbcTest}.
 *

 * @since 2.2.1
 */
public final class JdbcTypeExcludeFilter extends StandardAnnotationCustomizableTypeExcludeFilter<JdbcTest> {

	JdbcTypeExcludeFilter(Class<?> testClass) {
		super(testClass);
	}

}
