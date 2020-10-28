package org.springframework.boot.autoconfigure.security.reactive;

import reactor.core.publisher.Flux;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.web.server.WebFilterChainProxy;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Security in a reactive
 * application. Switches on {@link EnableWebFluxSecurity @EnableWebFluxSecurity} for a
 * reactive web application if this annotation has not been added by the user. It
 * delegates to Spring Security's content-negotiation mechanism for authentication. This
 * configuration also backs off if a bean of type {@link WebFilterChainProxy} has been
 * configured in any other way.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SecurityProperties.class)
@ConditionalOnClass({ Flux.class, EnableWebFluxSecurity.class, WebFilterChainProxy.class, WebFluxConfigurer.class })
public class ReactiveSecurityAutoConfiguration {

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnMissingBean(WebFilterChainProxy.class)
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
	@EnableWebFluxSecurity
	static class EnableWebFluxSecurityConfiguration {

	}

}
