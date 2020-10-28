package org.springframework.boot.loader.tools.sample;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Test annotation for a main application class.
 *

 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface SomeApplication {

}
