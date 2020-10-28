package smoketest.security.method;

import java.util.Date;
import java.util.Map;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SampleMethodSecurityApplication implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/access").setViewName("access");
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(SampleMethodSecurityApplication.class).run(args);
	}

	@Order(Ordered.HIGHEST_PRECEDENCE)
	@Configuration(proxyBeanMethods = false)
	protected static class AuthenticationSecurity {

		@SuppressWarnings("deprecation")
		@Bean
		public InMemoryUserDetailsManager inMemoryUserDetailsManager() throws Exception {
			return new InMemoryUserDetailsManager(
					User.withDefaultPasswordEncoder().username("admin").password("admin")
							.roles("ADMIN", "USER", "ACTUATOR").build(),
					User.withDefaultPasswordEncoder().username("user").password("user").roles("USER").build());
		}

	}

	@Configuration(proxyBeanMethods = false)
	protected static class ApplicationSecurity {

		@Bean
		SecurityFilterChain appSecurity(HttpSecurity http) throws Exception {
			http.authorizeRequests((requests) -> {
				requests.antMatchers("/login").permitAll();
				requests.anyRequest().fullyAuthenticated();
			});
			http.formLogin((form) -> {
				form.loginPage("/login");
				form.failureUrl("/login?error");
			});
			http.logout((logout) -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")));
			http.exceptionHandling((exceptions) -> exceptions.accessDeniedPage("/access?error"));
			return http.build();
		}

	}

	@Configuration(proxyBeanMethods = false)
	@Order(1)
	protected static class ActuatorSecurity {

		@Bean
		SecurityFilterChain actuatorSecurity(HttpSecurity http) throws Exception {
			http.requestMatcher(EndpointRequest.toAnyEndpoint());
			http.authorizeRequests((requests) -> requests.anyRequest().authenticated());
			http.httpBasic();
			return http.build();
		}

	}

	@Controller
	protected static class HomeController {

		@GetMapping("/")
		@Secured("ROLE_ADMIN")
		public String home(Map<String, Object> model) {
			model.put("message", "Hello World");
			model.put("title", "Hello Home");
			model.put("date", new Date());
			return "home";
		}

	}

}
