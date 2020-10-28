package org.springframework.boot.autoconfigure;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

/**

 */
@DirtiesContext
@SpringBootTest
class SpringJUnitTests {

	@Autowired
	private ApplicationContext context;

	@Value("${foo:spam}")
	private String foo = "bar";

	@Test
	void testContextCreated() {
		assertThat(this.context).isNotNull();
	}

	@Test
	void testContextInitialized() {
		assertThat(this.foo).isEqualTo("bucket");
	}

	@Configuration(proxyBeanMethods = false)
	@Import({ PropertyPlaceholderAutoConfiguration.class })
	static class TestConfiguration {

	}

}
