package org.springframework.boot.test.autoconfigure.data.mongo;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

/**
 * {@link ImportAutoConfiguration Auto-configuration imports} for typical Data MongoDB
 * tests. Most tests should consider using {@link DataMongoTest @DataMongoTest} rather
 * than using this annotation directly.
 *

 * @since 1.5.0
 * @see DataMongoTest
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ImportAutoConfiguration
public @interface AutoConfigureDataMongo {

}
