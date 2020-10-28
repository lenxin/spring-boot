package org.springframework.boot.test.autoconfigure.data.ldap;

import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.filter.StandardAnnotationCustomizableTypeExcludeFilter;

/**
 * {@link TypeExcludeFilter} for {@link DataLdapTest @DataLdapTest}.
 *

 * @since 2.2.1
 */
public final class DataLdapTypeExcludeFilter extends StandardAnnotationCustomizableTypeExcludeFilter<DataLdapTest> {

	DataLdapTypeExcludeFilter(Class<?> testClass) {
		super(testClass);
	}

}
