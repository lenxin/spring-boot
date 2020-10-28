package org.springframework.boot.autoconfigure.security.saml2;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

/**
 * {@link WebSecurityConfigurerAdapter} configuration for Spring Security's relying party
 * SAML support.
 *

 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDefaultWebSecurity
@ConditionalOnBean(RelyingPartyRegistrationRepository.class)
class Saml2LoginConfiguration {

	@Bean
	SecurityFilterChain samlSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests((requests) -> requests.anyRequest().authenticated()).saml2Login();
		return http.build();
	}

}
