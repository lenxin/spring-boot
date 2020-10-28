package org.springframework.boot.test.autoconfigure.data.r2dbc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

/**
 * {@link ImportAutoConfiguration Auto-configuration imports} for typical Data R2DBC
 * tests. Most tests should consider using {@link DataR2dbcTest @DataR2dbcTest} rather
 * than using this annotation directly.
 *

 * @see DataR2dbcTest
 * @since 2.3.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ImportAutoConfiguration
public @interface AutoConfigureDataR2dbc {

}
