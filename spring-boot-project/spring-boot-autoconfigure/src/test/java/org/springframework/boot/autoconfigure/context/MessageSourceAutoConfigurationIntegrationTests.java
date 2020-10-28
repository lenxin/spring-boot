package org.springframework.boot.autoconfigure.context;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MessageSourceAutoConfiguration}.
 *

 */
@SpringBootTest("spring.messages.basename:test/messages")
@ImportAutoConfiguration({ MessageSourceAutoConfiguration.class, PropertyPlaceholderAutoConfiguration.class })
@DirtiesContext
class MessageSourceAutoConfigurationIntegrationTests {

	@Autowired
	private ApplicationContext context;

	@Test
	void testMessageSourceFromPropertySourceAnnotation() {
		assertThat(this.context.getMessage("foo", null, "Foo message", Locale.UK)).isEqualTo("bar");
	}

	@Configuration(proxyBeanMethods = false)
	static class Config {

	}

}
