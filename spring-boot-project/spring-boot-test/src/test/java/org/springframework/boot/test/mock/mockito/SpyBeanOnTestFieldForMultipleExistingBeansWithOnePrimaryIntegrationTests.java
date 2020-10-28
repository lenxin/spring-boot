package org.springframework.boot.test.mock.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.example.ExampleGenericStringServiceCaller;
import org.springframework.boot.test.mock.mockito.example.SimpleExampleStringGenericService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

/**
 * Test {@link SpyBean @SpyBean} on a test class field can be used to inject a spy
 * instance when there are multiple candidates and one is primary.
 *

 */
@ExtendWith(SpringExtension.class)
class SpyBeanOnTestFieldForMultipleExistingBeansWithOnePrimaryIntegrationTests {

	@SpyBean
	private SimpleExampleStringGenericService spy;

	@Autowired
	private ExampleGenericStringServiceCaller caller;

	@Test
	void testSpying() {
		assertThat(this.caller.sayGreeting()).isEqualTo("I say two");
		assertThat(Mockito.mockingDetails(this.spy).getMockCreationSettings().getMockName().toString())
				.isEqualTo("two");
		verify(this.spy).greeting();
	}

	@Configuration(proxyBeanMethods = false)
	@Import(ExampleGenericStringServiceCaller.class)
	static class Config {

		@Bean
		SimpleExampleStringGenericService one() {
			return new SimpleExampleStringGenericService("one");
		}

		@Bean
		@Primary
		SimpleExampleStringGenericService two() {
			return new SimpleExampleStringGenericService("two");
		}

	}

}
