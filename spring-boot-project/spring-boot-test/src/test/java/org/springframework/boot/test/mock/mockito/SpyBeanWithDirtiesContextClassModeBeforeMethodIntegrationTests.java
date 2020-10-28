package org.springframework.boot.test.mock.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.example.ExampleServiceCaller;
import org.springframework.boot.test.mock.mockito.example.SimpleExampleService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.verify;

/**
 * Integration tests for using {@link SpyBean @SpyBean} with
 * {@link DirtiesContext @DirtiesContext} and {@link ClassMode#BEFORE_EACH_TEST_METHOD}.
 *

 */
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class SpyBeanWithDirtiesContextClassModeBeforeMethodIntegrationTests {

	@SpyBean
	private SimpleExampleService exampleService;

	@Autowired
	private ExampleServiceCaller caller;

	@Test
	void testSpying() throws Exception {
		this.caller.sayGreeting();
		verify(this.exampleService).greeting();
	}

	@Configuration(proxyBeanMethods = false)
	@Import(ExampleServiceCaller.class)
	static class Config {

	}

}
