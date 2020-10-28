package org.springframework.boot.test.mock.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.example.ExampleService;
import org.springframework.boot.test.mock.mockito.example.ExampleServiceCaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Test {@link MockBean @MockBean} on a test class field can be used to replace existing
 * beans when the context is cached. This test is identical to
 * {@link MockBeanOnTestFieldForExistingBeanIntegrationTests} so one of them should
 * trigger application context caching.
 *

 * @see MockBeanOnTestFieldForExistingBeanIntegrationTests
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MockBeanOnTestFieldForExistingBeanConfig.class)
class MockBeanOnTestFieldForExistingBeanCacheIntegrationTests {

	@MockBean
	private ExampleService exampleService;

	@Autowired
	private ExampleServiceCaller caller;

	@Test
	void testMocking() {
		given(this.exampleService.greeting()).willReturn("Boot");
		assertThat(this.caller.sayGreeting()).isEqualTo("I say Boot");
	}

}
