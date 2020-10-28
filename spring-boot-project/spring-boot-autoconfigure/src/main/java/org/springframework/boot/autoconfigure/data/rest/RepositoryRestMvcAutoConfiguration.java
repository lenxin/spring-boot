package org.springframework.boot.autoconfigure.data.rest;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Data Rest's MVC
 * integration.
 * <p>
 * Activates when the application is a web application and no
 * {@link RepositoryRestMvcConfiguration} is found.
 * <p>
 * Once in effect, the auto-configuration allows to configure any property of
 * {@link RepositoryRestConfiguration} using the {@code spring.data.rest} prefix.
 *



 * @since 1.1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnMissingBean(RepositoryRestMvcConfiguration.class)
@ConditionalOnClass(RepositoryRestMvcConfiguration.class)
@AutoConfigureAfter({ HttpMessageConvertersAutoConfiguration.class, JacksonAutoConfiguration.class })
@EnableConfigurationProperties(RepositoryRestProperties.class)
@Import(RepositoryRestMvcConfiguration.class)
public class RepositoryRestMvcAutoConfiguration {

	@Bean
	public SpringBootRepositoryRestConfigurer springBootRepositoryRestConfigurer() {
		return new SpringBootRepositoryRestConfigurer();
	}

}
