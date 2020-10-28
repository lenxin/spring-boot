package org.springframework.boot.test.mock.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.example.ExampleGenericService;
import org.springframework.boot.test.mock.mockito.example.ExampleGenericServiceCaller;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Test {@link MockBean @MockBean} on a test class field can be used to inject new mock
 * instances.
 *

 */
@ExtendWith(SpringExtension.class)
class MockBeanWithGenericsOnTestFieldForNewBeanIntegrationTests {

	@MockBean
	private ExampleGenericService<Integer> exampleIntegerService;

	@MockBean
	private ExampleGenericService<String> exampleStringService;

	@Autowired
	private ExampleGenericServiceCaller caller;

	@Test
	void testMocking() {
		given(this.exampleIntegerService.greeting()).willReturn(200);
		given(this.exampleStringService.greeting()).willReturn("Boot");
		assertThat(this.caller.sayGreeting()).isEqualTo("I say 200 Boot");
	}

	@Configuration(proxyBeanMethods = false)
	@Import(ExampleGenericServiceCaller.class)
	static class Config {

	}

}
