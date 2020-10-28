package org.springframework.boot.test.autoconfigure.filter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.TypeExcludeFilter;

/**
 * Annotation that can be on tests to define a set of {@link TypeExcludeFilter} classes
 * that should be applied to {@link SpringBootApplication @SpringBootApplication}
 * component scanning.
 *

 * @since 1.4.0
 * @see TypeExcludeFilter
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TypeExcludeFilters {

	/**
	 * Specifies {@link TypeExcludeFilter} classes that should be applied to
	 * {@link SpringBootApplication @SpringBootApplication} component scanning. Classes
	 * specified here can either have a no-arg constructor or accept a single
	 * {@code Class<?>} argument if they need access to the {@code testClass}.
	 * @see TypeExcludeFilter
	 * @return the type exclude filters to apply
	 */
	Class<? extends TypeExcludeFilter>[] value();

}
