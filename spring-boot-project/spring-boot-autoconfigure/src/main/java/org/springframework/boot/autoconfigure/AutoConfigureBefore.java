package org.springframework.boot.autoconfigure;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * Hint that an {@link EnableAutoConfiguration auto-configuration} should be applied
 * before other specified auto-configuration classes.
 * <p>
 * As with standard {@link Configuration @Configuration} classes, the order in which
 * auto-configuration classes are applied only affects the order in which their beans are
 * defined. The order in which those beans are subsequently created is unaffected and is
 * determined by each bean's dependencies and any {@link DependsOn @DependsOn}
 * relationships.
 *

 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Documented
public @interface AutoConfigureBefore {

	/**
	 * The auto-configure classes that should have not yet been applied.
	 * @return the classes
	 */
	Class<?>[] value() default {};

	/**
	 * The names of the auto-configure classes that should have not yet been applied.
	 * @return the class names
	 * @since 1.2.2
	 */
	String[] name() default {};

}
