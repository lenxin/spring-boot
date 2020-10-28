package org.springframework.boot.test.mock.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.example.ExampleService;
import org.springframework.boot.test.mock.mockito.example.ExampleServiceCaller;
import org.springframework.test.context.ContextConfiguration;
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
@ContextConfiguration(classes = SpyBeanOnTestFieldForExistingBeanConfig.class)
class SpyBeanOnTestFieldForExistingBeanIntegrationTests {

	@SpyBean
	private ExampleService exampleService;

	@Autowired
	private ExampleServiceCaller caller;

	@Test
	void testSpying() {
		assertThat(this.caller.sayGreeting()).isEqualTo("I say simple");
		verify(this.caller.getService()).greeting();
	}

}
