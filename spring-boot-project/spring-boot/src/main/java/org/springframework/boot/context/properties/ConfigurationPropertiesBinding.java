package org.springframework.boot.context.properties;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Qualifier for beans that are needed to configure the binding of
 * {@link ConfigurationProperties @ConfigurationProperties} (e.g. Converters).
 *

 * @since 1.3.0
 */
@Qualifier(ConfigurationPropertiesBinding.VALUE)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConfigurationPropertiesBinding {

	/**
	 * Concrete value for the {@link Qualifier @Qualifier}.
	 */
	String VALUE = "org.springframework.boot.context.properties.ConfigurationPropertiesBinding";

}
