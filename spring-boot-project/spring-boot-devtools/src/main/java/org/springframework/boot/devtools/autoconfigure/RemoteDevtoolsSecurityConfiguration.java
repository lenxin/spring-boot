package org.springframework.boot.devtools.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Spring Security configuration that allows anonymous access to the remote devtools
 * endpoint.
 *

 */
@ConditionalOnClass({ SecurityFilterChain.class, HttpSecurity.class })
@Configuration(proxyBeanMethods = false)
class RemoteDevtoolsSecurityConfiguration {

	@Configuration
	static class SecurityConfiguration {

		private final String url;

		SecurityConfiguration(DevToolsProperties devToolsProperties, ServerProperties serverProperties) {
			ServerProperties.Servlet servlet = serverProperties.getServlet();
			String servletContextPath = (servlet.getContextPath() != null) ? servlet.getContextPath() : "";
			this.url = servletContextPath + devToolsProperties.getRemote().getContextPath() + "/restart";
		}

		@Bean
		@Order(SecurityProperties.BASIC_AUTH_ORDER - 1)
		SecurityFilterChain configure(HttpSecurity http) throws Exception {
			http.requestMatcher(new AntPathRequestMatcher(this.url)).authorizeRequests().anyRequest().anonymous().and()
					.csrf().disable();
			return http.build();
		}

	}

}
