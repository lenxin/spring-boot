package org.springframework.boot.docs.web.security;

import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Example configuration for customizing security rules for a WebFlux application.
 *

 */
@Configuration(proxyBeanMethods = false)
public class CustomWebFluxSecurityExample {

	// @formatter:off
	// tag::configuration[]
	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		return http
			.authorizeExchange()
				.matchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				.pathMatchers("/foo", "/bar")
					.authenticated().and()
				.formLogin().and()
			.build();
	}
	// end::configuration[]
	// @formatter:on

}
