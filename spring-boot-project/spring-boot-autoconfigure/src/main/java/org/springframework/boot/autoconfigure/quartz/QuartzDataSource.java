package org.springframework.boot.autoconfigure.quartz;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Qualifier annotation for a DataSource to be injected into Quartz auto-configuration.
 * Can be used on a secondary data source, if there is another one marked as
 * {@code @Primary}.
 *

 * @since 2.0.2
 */
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier
public @interface QuartzDataSource {

}
