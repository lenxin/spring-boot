package org.springframework.boot.test.autoconfigure.data.jdbc;

import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.filter.StandardAnnotationCustomizableTypeExcludeFilter;

/**
 * {@link TypeExcludeFilter} for {@link DataJdbcTest @DataJdbcTest}.
 *

 * @since 2.2.1
 */
public final class DataJdbcTypeExcludeFilter extends StandardAnnotationCustomizableTypeExcludeFilter<DataJdbcTest> {

	DataJdbcTypeExcludeFilter(Class<?> testClass) {
		super(testClass);
	}

}
