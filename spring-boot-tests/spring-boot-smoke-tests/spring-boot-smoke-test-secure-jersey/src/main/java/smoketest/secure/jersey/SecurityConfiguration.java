package smoketest.secure.jersey;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.web.mappings.MappingsEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

	@SuppressWarnings("deprecation")
	@Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		return new InMemoryUserDetailsManager(
				User.withDefaultPasswordEncoder().username("user").password("password").authorities("ROLE_USER")
						.build(),
				User.withDefaultPasswordEncoder().username("admin").password("admin")
						.authorities("ROLE_ACTUATOR", "ROLE_USER").build());
	}

	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.authorizeRequests()
				.requestMatchers(EndpointRequest.to("health", "info")).permitAll()
				.requestMatchers(EndpointRequest.toAnyEndpoint().excluding(MappingsEndpoint.class)).hasRole("ACTUATOR")
				.antMatchers("/**").hasRole("USER")
				.and()
			.httpBasic();
		return http.build();
		// @formatter:on
	}

}
