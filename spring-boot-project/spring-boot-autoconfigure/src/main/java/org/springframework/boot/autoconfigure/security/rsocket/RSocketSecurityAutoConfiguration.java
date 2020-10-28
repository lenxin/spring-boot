package org.springframework.boot.autoconfigure.security.rsocket;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.rsocket.server.RSocketServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.rsocket.EnableRSocketSecurity;
import org.springframework.security.rsocket.core.SecuritySocketAcceptorInterceptor;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Security for an RSocket
 * server.
 *


 * @since 2.2.0
 */
@Configuration(proxyBeanMethods = false)
@EnableRSocketSecurity
@ConditionalOnClass(SecuritySocketAcceptorInterceptor.class)
public class RSocketSecurityAutoConfiguration {

	@Bean
	RSocketServerCustomizer springSecurityRSocketSecurity(SecuritySocketAcceptorInterceptor interceptor) {
		return (server) -> server.interceptors((registry) -> registry.forSocketAcceptor(interceptor));
	}

}
