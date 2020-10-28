package org.springframework.boot.test.mock.mockito.example;

/**
 * Example service implementation for spy tests.
 *

 */
public class RealExampleService implements ExampleService {

	private final String greeting;

	public RealExampleService(String greeting) {
		this.greeting = greeting;
	}

	@Override
	public String greeting() {
		return this.greeting;
	}

}
