package org.springframework.boot.autoconfigure.security.oauth2.resource.reactive;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.OAuth2ResourceServerSpec;
import org.springframework.security.oauth2.server.resource.introspection.NimbusReactiveOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Configures a {@link ReactiveOpaqueTokenIntrospector} when a token introspection
 * endpoint is available. Also configures a {@link SecurityWebFilterChain} if a
 * {@link ReactiveOpaqueTokenIntrospector} bean is found.
 *

 */
class ReactiveOAuth2ResourceServerOpaqueTokenConfiguration {

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnMissingBean(ReactiveOpaqueTokenIntrospector.class)
	static class OpaqueTokenIntrospectionClientConfiguration {

		@Bean
		@ConditionalOnProperty(name = "spring.security.oauth2.resourceserver.opaquetoken.introspection-uri")
		NimbusReactiveOpaqueTokenIntrospector opaqueTokenIntrospector(OAuth2ResourceServerProperties properties) {
			OAuth2ResourceServerProperties.Opaquetoken opaqueToken = properties.getOpaquetoken();
			return new NimbusReactiveOpaqueTokenIntrospector(opaqueToken.getIntrospectionUri(),
					opaqueToken.getClientId(), opaqueToken.getClientSecret());
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnMissingBean(SecurityWebFilterChain.class)
	static class WebSecurityConfiguration {

		@Bean
		@ConditionalOnBean(ReactiveOpaqueTokenIntrospector.class)
		SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
			http.authorizeExchange((exchanges) -> exchanges.anyExchange().authenticated());
			http.oauth2ResourceServer(OAuth2ResourceServerSpec::opaqueToken);
			return http.build();
		}

	}

}
