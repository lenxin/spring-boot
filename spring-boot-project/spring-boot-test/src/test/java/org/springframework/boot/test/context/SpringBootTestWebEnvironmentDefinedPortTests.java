package org.springframework.boot.test.context;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Tests for {@link SpringBootTest @SpringBootTest} configured with
 * {@link WebEnvironment#DEFINED_PORT}.
 *


 */
@DirtiesContext
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, properties = { "server.port=0", "value=123" })
public class SpringBootTestWebEnvironmentDefinedPortTests extends AbstractSpringBootTestWebServerWebEnvironmentTests {

	@Configuration(proxyBeanMethods = false)
	@EnableWebMvc
	@RestController
	static class Config extends AbstractConfig {

	}

}
