package org.springframework.boot.test.mock.mockito.example;

import org.springframework.stereotype.Service;

/**
 * An {@link ExampleService} that always throws an exception.
 *

 */
@Service
public class FailingExampleService implements ExampleService {

	@Override
	public String greeting() {
		throw new IllegalStateException("Failed");
	}

}
