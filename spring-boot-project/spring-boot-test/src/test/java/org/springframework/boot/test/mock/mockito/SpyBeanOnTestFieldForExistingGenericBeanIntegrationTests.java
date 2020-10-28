package org.springframework.boot.test.mock.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.example.ExampleGenericService;
import org.springframework.boot.test.mock.mockito.example.ExampleGenericServiceCaller;
import org.springframework.boot.test.mock.mockito.example.SimpleExampleIntegerGenericService;
import org.springframework.boot.test.mock.mockito.example.SimpleExampleStringGenericService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

/**
 * Test {@link SpyBean @SpyBean} on a test class field can be used to replace existing
 * beans.
 *

 * @see SpyBeanOnTestFieldForExistingBeanCacheIntegrationTests
 */
@ExtendWith(SpringExtension.class)
class SpyBeanOnTestFieldForExistingGenericBeanIntegrationTests {

	// gh-7625

	@SpyBean
	private ExampleGenericService<String> exampleService;

	@Autowired
	private ExampleGenericServiceCaller caller;

	@Test
	void testSpying() {
		assertThat(this.caller.sayGreeting()).isEqualTo("I say 123 simple");
		verify(this.exampleService).greeting();
	}

	@Configuration(proxyBeanMethods = false)
	@Import({ ExampleGenericServiceCaller.class, SimpleExampleIntegerGenericService.class })
	static class SpyBeanOnTestFieldForExistingBeanConfig {

		@Bean
		ExampleGenericService<String> simpleExampleStringGenericService() {
			// In order to trigger issue we need a method signature that returns the
			// generic type not the actual implementation class
			return new SimpleExampleStringGenericService();
		}

	}

}
