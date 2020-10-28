package org.springframework.boot.testsupport.classpath;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Annotation used to fork the classpath. This can be helpful where neither
 * {@link ClassPathExclusions} or {@link ClassPathOverrides} are needed, but just a copy
 * of the classpath.
 *

 * @since 2.4.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@ExtendWith(ModifiedClassPathExtension.class)
public @interface ForkedClassPath {

}
