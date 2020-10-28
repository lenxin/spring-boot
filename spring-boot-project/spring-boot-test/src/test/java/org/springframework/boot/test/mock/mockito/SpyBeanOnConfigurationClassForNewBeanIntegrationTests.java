package org.springframework.boot.test.mock.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.example.ExampleServiceCaller;
import org.springframework.boot.test.mock.mockito.example.SimpleExampleService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

/**
 * Test {@link SpyBean @SpyBean} on a configuration class can be used to inject new spy
 * instances.
 *

 */
@ExtendWith(SpringExtension.class)
class SpyBeanOnConfigurationClassForNewBeanIntegrationTests {

	@Autowired
	private ExampleServiceCaller caller;

	@Test
	void testSpying() {
		assertThat(this.caller.sayGreeting()).isEqualTo("I say simple");
		verify(this.caller.getService()).greeting();
	}

	@Configuration(proxyBeanMethods = false)
	@SpyBean(SimpleExampleService.class)
	@Import(ExampleServiceCaller.class)
	static class Config {

	}

}
