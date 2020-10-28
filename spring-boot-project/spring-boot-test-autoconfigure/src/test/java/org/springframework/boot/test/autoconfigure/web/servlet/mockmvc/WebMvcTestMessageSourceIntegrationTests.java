package org.springframework.boot.test.autoconfigure.web.servlet.mockmvc;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link WebMvcTest @WebMvcTest} and {@link MessageSource}
 * auto-configuration.
 *

 */
@WebMvcTest
@WithMockUser
@TestPropertySource(properties = "spring.messages.basename=web-test-messages")
class WebMvcTestMessageSourceIntegrationTests {

	@Autowired
	private ApplicationContext context;

	@Test
	void messageSourceHasBeenAutoConfigured() {
		assertThat(this.context.getMessage("a", null, Locale.ENGLISH)).isEqualTo("alpha");
	}

}
