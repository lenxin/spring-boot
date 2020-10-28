package org.springframework.boot.test.web.client;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link TestRestTemplateContextCustomizer} to ensure
 * early-initialization of factory beans doesn't occur.
 *

 */
@SpringBootTest(classes = TestRestTemplateContextCustomizerWithFactoryBeanTests.TestClassWithFactoryBean.class,
		webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
class TestRestTemplateContextCustomizerWithFactoryBeanTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void test() {
		assertThat(this.restTemplate).isNotNull();
	}

	@Configuration(proxyBeanMethods = false)
	@ComponentScan("org.springframework.boot.test.web.client.scan")
	static class TestClassWithFactoryBean {

		@Bean
		TomcatServletWebServerFactory webServerFactory() {
			return new TomcatServletWebServerFactory(0);
		}

	}

}
