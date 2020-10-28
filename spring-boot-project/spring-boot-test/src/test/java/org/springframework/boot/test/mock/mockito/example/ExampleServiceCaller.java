package org.springframework.boot.test.mock.mockito.example;

/**
 * Example bean for mocking tests that calls {@link ExampleService}.
 *

 */
public class ExampleServiceCaller {

	private final ExampleService service;

	public ExampleServiceCaller(ExampleService service) {
		this.service = service;
	}

	public ExampleService getService() {
		return this.service;
	}

	public String sayGreeting() {
		return "I say " + this.service.greeting();
	}

}
