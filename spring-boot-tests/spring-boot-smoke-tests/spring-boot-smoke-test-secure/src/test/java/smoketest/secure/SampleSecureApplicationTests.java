package smoketest.secure;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Basic integration tests for demo application.
 *

 */
@SpringBootTest(classes = { SampleSecureApplication.class })
class SampleSecureApplicationTests {

	@Autowired
	private SampleService service;

	private Authentication authentication;

	@BeforeEach
	void init() {
		this.authentication = new UsernamePasswordAuthenticationToken("user", "password");
	}

	@AfterEach
	void close() {
		SecurityContextHolder.clearContext();
	}

	@Test
	void secure() {
		assertThatExceptionOfType(AuthenticationException.class)
				.isThrownBy(() -> SampleSecureApplicationTests.this.service.secure());
	}

	@Test
	void authenticated() {
		SecurityContextHolder.getContext().setAuthentication(this.authentication);
		assertThat("Hello Security").isEqualTo(this.service.secure());
	}

	@Test
	void preauth() {
		SecurityContextHolder.getContext().setAuthentication(this.authentication);
		assertThat("Hello World").isEqualTo(this.service.authorized());
	}

	@Test
	void denied() {
		SecurityContextHolder.getContext().setAuthentication(this.authentication);
		assertThatExceptionOfType(AccessDeniedException.class)
				.isThrownBy(() -> SampleSecureApplicationTests.this.service.denied());
	}

}
