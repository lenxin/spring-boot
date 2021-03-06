package smoketest.web.secure.jdbc;

import java.util.Date;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@Controller
public class SampleWebSecureJdbcApplication implements WebMvcConfigurer {

	@GetMapping("/")
	public String home(Map<String, Object> model) {
		model.put("message", "Hello World");
		model.put("title", "Hello Home");
		model.put("date", new Date());
		return "home";
	}

	@RequestMapping("/foo")
	public String foo() {
		throw new RuntimeException("Expected exception in controller");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(SampleWebSecureJdbcApplication.class).run(args);
	}

	@Configuration(proxyBeanMethods = false)
	protected static class ApplicationSecurity {

		@Bean
		SecurityFilterChain configure(HttpSecurity http) throws Exception {
			http.authorizeRequests((requests) -> {
				requests.antMatchers("/css/**").permitAll();
				requests.anyRequest().fullyAuthenticated();
			});
			http.formLogin((form) -> {
				form.loginPage("/login");
				form.failureUrl("/login?error").permitAll();
			});
			http.logout(LogoutConfigurer::permitAll);
			return http.build();
		}

		@Bean
		public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
			JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
			jdbcUserDetailsManager.setDataSource(dataSource);
			return jdbcUserDetailsManager;
		}

	}

}
