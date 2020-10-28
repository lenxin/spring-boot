package org.springframework.boot.actuate.autoconfigure.integrationtest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.autoconfigure.audit.AuditAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.beans.BeansEndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.endpoint.EndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.web.reactive.ReactiveManagementContextAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementContextAutoConfiguration;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpoint;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.test.context.TestSecurityContextHolder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Integration tests for the Actuator's WebFlux {@link ControllerEndpoint controller
 * endpoints}.
 *

 */
class ControllerEndpointWebFluxIntegrationTests {

	private AnnotationConfigReactiveWebApplicationContext context;

	@AfterEach
	void close() {
		TestSecurityContextHolder.clearContext();
		this.context.close();
	}

	@Test
	void endpointsCanBeAccessed() throws Exception {
		TestSecurityContextHolder.getContext()
				.setAuthentication(new TestingAuthenticationToken("user", "N/A", "ROLE_ACTUATOR"));
		this.context = new AnnotationConfigReactiveWebApplicationContext();
		this.context.register(DefaultConfiguration.class, ExampleController.class);
		TestPropertyValues.of("management.endpoints.web.exposure.include=*").applyTo(this.context);
		this.context.refresh();
		WebTestClient webClient = WebTestClient.bindToApplicationContext(this.context).build();
		webClient.get().uri("/actuator/example").exchange().expectStatus().isOk();
	}

	@ImportAutoConfiguration({ JacksonAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class,
			EndpointAutoConfiguration.class, WebEndpointAutoConfiguration.class,
			ReactiveManagementContextAutoConfiguration.class, AuditAutoConfiguration.class,
			PropertyPlaceholderAutoConfiguration.class, WebFluxAutoConfiguration.class,
			ManagementContextAutoConfiguration.class, BeansEndpointAutoConfiguration.class })
	static class DefaultConfiguration {

	}

	@RestControllerEndpoint(id = "example")
	static class ExampleController {

		@GetMapping("/")
		String example() {
			return "Example";
		}

	}

}
