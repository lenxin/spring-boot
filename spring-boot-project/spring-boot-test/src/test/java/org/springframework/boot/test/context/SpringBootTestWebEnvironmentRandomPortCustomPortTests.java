package org.springframework.boot.test.context;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.AbstractSpringBootTestWebServerWebEnvironmentTests.AbstractConfig;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for {@link SpringBootTest @SpringBootTest} with a custom inline server.port in an
 * embedded web environment.
 *

 */
@DirtiesContext
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = { "server.port=12345" })
class SpringBootTestWebEnvironmentRandomPortCustomPortTests {

	@Autowired
	private Environment environment;

	@Test
	void validatePortIsNotOverwritten() {
		String port = this.environment.getProperty("server.port");
		assertThat(port).isEqualTo("0");
	}

	@Configuration(proxyBeanMethods = false)
	@EnableWebMvc
	static class Config extends AbstractConfig {

	}

}
