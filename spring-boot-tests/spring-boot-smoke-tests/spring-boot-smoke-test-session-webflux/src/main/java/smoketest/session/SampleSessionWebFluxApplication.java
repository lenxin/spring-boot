package smoketest.session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;

@SpringBootApplication
public class SampleSessionWebFluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleSessionWebFluxApplication.class);
	}

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		// @formatter:off
		return http
			.authorizeExchange()
				.anyExchange().authenticated()
				.and()
			.httpBasic().securityContextRepository(new WebSessionServerSecurityContextRepository())
				.and()
			.formLogin()
				.and()
			.build();
		// @formatter:on
	}

}
