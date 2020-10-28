package org.springframework.boot.test.autoconfigure.web.reactive.webclient;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link WebFluxTest @WebFluxTest} and {@link MessageSource}
 * auto-configuration.
 *

 */
@WebFluxTest
@TestPropertySource(properties = "spring.messages.basename=web-test-messages")
class WebFluxTestMessageSourceIntegrationTests {

	@Autowired
	private ApplicationContext context;

	@Test
	void messageSourceHasBeenAutoConfigured() {
		assertThat(this.context.getMessage("a", null, Locale.ENGLISH)).isEqualTo("alpha");
	}

}
