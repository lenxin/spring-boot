package org.springframework.boot.test.autoconfigure.web.reactive;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

/**
 * {@link ImportAutoConfiguration Auto-configuration imports} for typical Spring WebFlux
 * tests. Most tests should consider using {@link WebFluxTest @WebFluxTest} rather than
 * using this annotation directly.
 *

 * @since 2.0.0
 * @see WebFluxTest
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ImportAutoConfiguration
public @interface AutoConfigureWebFlux {

}
