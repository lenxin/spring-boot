package org.springframework.boot.test.autoconfigure.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.boot.test.autoconfigure.properties.PropertyMapping;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;

/**
 * Annotation that can be applied to a test class to configure a test {@link CacheManager}
 * if none has been defined yet. By default this annotation installs a
 * {@link NoOpCacheManager}.
 *

 * @since 1.4.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ImportAutoConfiguration
public @interface AutoConfigureCache {

	@PropertyMapping("spring.cache.type")
	CacheType cacheProvider() default CacheType.NONE;

}
