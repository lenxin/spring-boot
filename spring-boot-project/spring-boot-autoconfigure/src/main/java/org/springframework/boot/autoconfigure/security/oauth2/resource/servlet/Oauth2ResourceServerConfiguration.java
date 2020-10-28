package org.springframework.boot.autoconfigure.security.oauth2.resource.servlet;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.JwtDecoder;

/**
 * Configuration classes for OAuth2 Resource Server These should be {@code @Import} in a
 * regular auto-configuration class to guarantee their order of execution.
 *

 */
class Oauth2ResourceServerConfiguration {

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(JwtDecoder.class)
	@Import({ OAuth2ResourceServerJwtConfiguration.JwtDecoderConfiguration.class,
			OAuth2ResourceServerJwtConfiguration.OAuth2SecurityFilterChainConfiguration.class })
	static class JwtConfiguration {

	}

	@Configuration(proxyBeanMethods = false)
	@Import({ OAuth2ResourceServerOpaqueTokenConfiguration.OpaqueTokenIntrospectionClientConfiguration.class,
			OAuth2ResourceServerOpaqueTokenConfiguration.OAuth2SecurityFilterChainConfiguration.class })
	static class OpaqueTokenConfiguration {

	}

}
