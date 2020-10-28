package org.springframework.boot.test.autoconfigure.web.reactive;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.web.reactive.server.MockServerConfigurer;

/**
 * Configuration for Spring Security's
 * {@link org.springframework.test.web.reactive.server.WebTestClient} integration.
 *

 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(SecurityMockServerConfigurers.class)
class WebTestClientSecurityConfiguration {

	@Bean
	MockServerConfigurer mockServerConfigurer() {
		return SecurityMockServerConfigurers.springSecurity();
	}

}
