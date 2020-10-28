package org.springframework.boot.autoconfigure.security.oauth2.resource.reactive;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector;

/**
 * Configuration classes for OAuth2 Resource Server These should be {@code @Import} in a
 * regular auto-configuration class to guarantee their order of execution.
 *

 */
class ReactiveOAuth2ResourceServerConfiguration {

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass({ BearerTokenAuthenticationToken.class, ReactiveJwtDecoder.class })
	@Import({ ReactiveOAuth2ResourceServerJwkConfiguration.JwtConfiguration.class,
			ReactiveOAuth2ResourceServerJwkConfiguration.WebSecurityConfiguration.class })
	static class JwtConfiguration {

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass({ BearerTokenAuthenticationToken.class, ReactiveOpaqueTokenIntrospector.class })
	@Import({ ReactiveOAuth2ResourceServerOpaqueTokenConfiguration.OpaqueTokenIntrospectionClientConfiguration.class,
			ReactiveOAuth2ResourceServerOpaqueTokenConfiguration.WebSecurityConfiguration.class })
	static class OpaqueTokenConfiguration {

	}

}
