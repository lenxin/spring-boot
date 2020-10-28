package org.springframework.boot.test.mock.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.example.CustomQualifier;
import org.springframework.boot.test.mock.mockito.example.CustomQualifierExampleService;
import org.springframework.boot.test.mock.mockito.example.ExampleService;
import org.springframework.boot.test.mock.mockito.example.ExampleServiceCaller;
import org.springframework.boot.test.mock.mockito.example.RealExampleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

/**
 * Test {@link MockBean @MockBean} on a test class field can be used to replace existing
 * bean while preserving qualifiers.
 *


 */
@ExtendWith(SpringExtension.class)
class MockBeanOnTestFieldForExistingBeanWithQualifierIntegrationTests {

	@MockBean
	@CustomQualifier
	private ExampleService service;

	@Autowired
	private ExampleServiceCaller caller;

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void testMocking() {
		this.caller.sayGreeting();
		verify(this.service).greeting();
	}

	@Test
	void onlyQualifiedBeanIsReplaced() {
		assertThat(this.applicationContext.getBean("service")).isSameAs(this.service);
		ExampleService anotherService = this.applicationContext.getBean("anotherService", ExampleService.class);
		assertThat(anotherService.greeting()).isEqualTo("Another");
	}

	@Configuration(proxyBeanMethods = false)
	static class TestConfig {

		@Bean
		CustomQualifierExampleService service() {
			return new CustomQualifierExampleService();
		}

		@Bean
		ExampleService anotherService() {
			return new RealExampleService("Another");
		}

		@Bean
		ExampleServiceCaller controller(@CustomQualifier ExampleService service) {
			return new ExampleServiceCaller(service);
		}

	}

}
