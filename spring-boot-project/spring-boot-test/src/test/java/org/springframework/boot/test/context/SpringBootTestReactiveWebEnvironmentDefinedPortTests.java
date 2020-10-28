package org.springframework.boot.test.context;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * Tests for {@link SpringBootTest @SpringBootTest} in a reactive environment configured
 * with {@link WebEnvironment#DEFINED_PORT}.
 *

 */
@DirtiesContext
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT,
		properties = { "spring.main.web-application-type=reactive", "server.port=0", "value=123" })
public class SpringBootTestReactiveWebEnvironmentDefinedPortTests
		extends AbstractSpringBootTestEmbeddedReactiveWebEnvironmentTests {

	@Configuration(proxyBeanMethods = false)
	@EnableWebFlux
	@RestController
	static class Config extends AbstractConfig {

	}

}
