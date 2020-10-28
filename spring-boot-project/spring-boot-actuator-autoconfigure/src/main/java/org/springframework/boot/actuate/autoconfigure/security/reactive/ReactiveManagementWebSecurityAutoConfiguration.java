package org.springframework.boot.actuate.autoconfigure.security.reactive;

import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.HealthEndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.info.InfoEndpointAutoConfiguration;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.reactive.ReactiveOAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.reactive.ReactiveOAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.WebFilterChainProxy;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Reactive Spring Security when
 * actuator is on the classpath. Specifically, it permits access to the health and info
 * endpoints while securing everything else.
 *

 * @since 2.1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ EnableWebFluxSecurity.class, WebFilterChainProxy.class })
@ConditionalOnMissingBean({ SecurityWebFilterChain.class, WebFilterChainProxy.class })
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@AutoConfigureBefore(ReactiveSecurityAutoConfiguration.class)
@AutoConfigureAfter({ HealthEndpointAutoConfiguration.class, InfoEndpointAutoConfiguration.class,
		WebEndpointAutoConfiguration.class, ReactiveOAuth2ClientAutoConfiguration.class,
		ReactiveOAuth2ResourceServerAutoConfiguration.class })
public class ReactiveManagementWebSecurityAutoConfiguration {

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws Exception {
		http.authorizeExchange((exchanges) -> {
			exchanges.matchers(EndpointRequest.to(HealthEndpoint.class, InfoEndpoint.class)).permitAll();
			exchanges.anyExchange().authenticated();
		});
		http.httpBasic(Customizer.withDefaults());
		http.formLogin(Customizer.withDefaults());
		return http.build();
	}

}
