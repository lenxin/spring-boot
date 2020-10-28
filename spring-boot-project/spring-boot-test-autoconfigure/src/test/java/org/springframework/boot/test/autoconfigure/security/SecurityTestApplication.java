package org.springframework.boot.test.autoconfigure.security;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Tests application for MockMvc Security.
 *

 */
@SpringBootApplication
public class SecurityTestApplication {

	@RestController
	static class MyController {

		@RequestMapping("/")
		@Secured("ROLE_USER")
		String index() {
			return "Hello";
		}

	}

}
