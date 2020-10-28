package org.springframework.boot.autoconfigure.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

/**
 * Automatically adds Spring Security's integration with Spring Data.
 *

 * @since 1.3.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(SecurityEvaluationContextExtension.class)
public class SecurityDataConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
		return new SecurityEvaluationContextExtension();
	}

}
