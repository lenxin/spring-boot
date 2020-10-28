package org.springframework.boot.groovy;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Provides one or more additional sources of dependency management that is used when
 * resolving {@code @Grab} dependencies.
 *

 * @since 1.3.0
 */
@Target({ ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD,
		ElementType.PARAMETER, ElementType.TYPE })
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface DependencyManagementBom {

	/**
	 * One or more sets of colon-separated coordinates ({@code group:module:version}) of a
	 * Maven bom that contains dependency management that will add to and override the
	 * default dependency management.
	 * @return the BOM coordinates
	 */
	String[] value();

}
