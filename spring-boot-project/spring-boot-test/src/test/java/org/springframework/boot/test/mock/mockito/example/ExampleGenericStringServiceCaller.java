package org.springframework.boot.test.mock.mockito.example;

/**
 * Example bean for mocking tests that calls {@link ExampleGenericService}.
 *

 */
public class ExampleGenericStringServiceCaller {

	private final ExampleGenericService<String> stringService;

	public ExampleGenericStringServiceCaller(ExampleGenericService<String> stringService) {
		this.stringService = stringService;
	}

	public ExampleGenericService<String> getStringService() {
		return this.stringService;
	}

	public String sayGreeting() {
		return "I say " + this.stringService.greeting();
	}

}
