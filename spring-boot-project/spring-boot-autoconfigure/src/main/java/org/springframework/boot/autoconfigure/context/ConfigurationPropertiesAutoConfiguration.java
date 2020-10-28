package org.springframework.boot.autoconfigure.context;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for
 * {@link ConfigurationProperties @ConfigurationProperties} beans. Automatically binds and
 * validates any bean annotated with {@code @ConfigurationProperties}.
 *

 * @since 1.3.0
 * @see EnableConfigurationProperties
 * @see ConfigurationProperties
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties
public class ConfigurationPropertiesAutoConfiguration {

}
