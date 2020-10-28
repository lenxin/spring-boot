package smoketest.actuator.customsecurity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.web.mappings.MappingsEndpoint;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration(proxyBeanMethods = false)
public class SecurityConfiguration {

	@Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		List<UserDetails> userDetails = new ArrayList<>();
		userDetails.add(createUserDetails("user", "password", "ROLE_USER"));
		userDetails.add(createUserDetails("beans", "beans", "ROLE_BEANS"));
		userDetails.add(createUserDetails("admin", "admin", "ROLE_ACTUATOR", "ROLE_USER"));
		return new InMemoryUserDetailsManager(userDetails);
	}

	@SuppressWarnings("deprecation")
	private UserDetails createUserDetails(String username, String password, String... authorities) {
		UserBuilder builder = User.withDefaultPasswordEncoder();
		builder.username(username);
		builder.password(password);
		builder.authorities(authorities);
		return builder.build();
	}

	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.authorizeRequests((requests) -> {
			requests.mvcMatchers("/actuator/beans").hasRole("BEANS");
			requests.requestMatchers(EndpointRequest.to("health", "info")).permitAll();
			requests.requestMatchers(EndpointRequest.toAnyEndpoint().excluding(MappingsEndpoint.class))
					.hasRole("ACTUATOR");
			requests.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();
			requests.antMatchers("/foo").permitAll();
			requests.antMatchers("/**").hasRole("USER");
		});
		http.cors(Customizer.withDefaults());
		http.httpBasic();
		return http.build();
	}

}
