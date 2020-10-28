package org.springframework.boot.docs.web.client;

import java.net.URI;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A sample {@link SpringBootConfiguration @ConfigurationProperties} with an example
 * controller.
 *

 */
@SpringBootConfiguration
@ImportAutoConfiguration({ ServletWebServerFactoryAutoConfiguration.class, DispatcherServletAutoConfiguration.class,
		JacksonAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class })
class SampleWebClientConfiguration {

	@RestController
	private static class ExampleController {

		@RequestMapping("/example")
		ResponseEntity<String> example() {
			return ResponseEntity.ok().location(URI.create("https://other.example.com/example")).body("test");
		}

	}

}
