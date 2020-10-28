package org.springframework.boot.test.autoconfigure.data.jdbc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

/**
 * {@link ImportAutoConfiguration Auto-configuration imports} for typical Data JDBC tests.
 * Most tests should consider using {@link DataJdbcTest @DataJdbcTest} rather than using
 * this annotation directly.
 *

 * @since 2.1.0
 * @see DataJdbcTest
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ImportAutoConfiguration
public @interface AutoConfigureDataJdbc {

}
