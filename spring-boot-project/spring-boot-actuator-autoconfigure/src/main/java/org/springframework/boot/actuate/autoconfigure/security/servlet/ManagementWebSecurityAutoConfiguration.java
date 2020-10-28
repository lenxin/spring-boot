package org.springframework.boot.actuate.autoconfigure.security.servlet;

import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.HealthEndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.info.InfoEndpointAutoConfiguration;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Security when actuator is
 * on the classpath. It allows unauthenticated access to the {@link HealthEndpoint} and
 * {@link InfoEndpoint}. If the user specifies their own
 * {@link org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
 * WebSecurityConfigurerAdapter} or {@link SecurityFilterChain} bean, this will back-off
 * completely and the user should specify all the bits that they want to configure as part
 * of the custom security configuration.
 *

 * @since 2.1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnDefaultWebSecurity
@AutoConfigureBefore(SecurityAutoConfiguration.class)
@AutoConfigureAfter({ HealthEndpointAutoConfiguration.class, InfoEndpointAutoConfiguration.class,
		WebEndpointAutoConfiguration.class, OAuth2ClientAutoConfiguration.class,
		OAuth2ResourceServerAutoConfiguration.class, Saml2RelyingPartyAutoConfiguration.class })
public class ManagementWebSecurityAutoConfiguration {

	@Bean
	SecurityFilterChain managementSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests((requests) -> {
			requests.requestMatchers(EndpointRequest.to(HealthEndpoint.class, InfoEndpoint.class)).permitAll();
			requests.anyRequest().authenticated();
		});
		http.formLogin(Customizer.withDefaults());
		http.httpBasic(Customizer.withDefaults());
		return http.build();
	}

}
