package org.springframework.boot.test.autoconfigure.data.neo4j;

import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.filter.StandardAnnotationCustomizableTypeExcludeFilter;

/**
 * {@link TypeExcludeFilter} for {@link DataNeo4jTest @DataNeo4jTest}.
 *

 * @since 2.2.1
 */
public final class DataNeo4jTypeExcludeFilter extends StandardAnnotationCustomizableTypeExcludeFilter<DataNeo4jTest> {

	DataNeo4jTypeExcludeFilter(Class<?> testClass) {
		super(testClass);
	}

}
