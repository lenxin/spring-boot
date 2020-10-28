package org.springframework.boot.test.autoconfigure.data.cassandra;

import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.filter.StandardAnnotationCustomizableTypeExcludeFilter;

/**
 * {@link TypeExcludeFilter} for {@link DataCassandraTest @DataCassandraTest}.
 *

 */
class DataCassandraTypeExcludeFilter extends StandardAnnotationCustomizableTypeExcludeFilter<DataCassandraTest> {

	protected DataCassandraTypeExcludeFilter(Class<?> testClass) {
		super(testClass);
	}

}
