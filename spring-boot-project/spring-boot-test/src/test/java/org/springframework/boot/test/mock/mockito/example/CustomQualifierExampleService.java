package org.springframework.boot.test.mock.mockito.example;

/**
 * An {@link ExampleService} that uses a custom qualifier.
 *

 */
@CustomQualifier
public class CustomQualifierExampleService implements ExampleService {

	@Override
	public String greeting() {
		return "CustomQualifier";
	}

}
