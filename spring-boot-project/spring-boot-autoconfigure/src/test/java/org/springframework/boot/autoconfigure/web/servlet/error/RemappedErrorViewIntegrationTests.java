package org.springframework.boot.autoconfigure.web.servlet.error;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for remapped error pages.
 *

 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = "spring.mvc.servlet.path:/spring/")
@DirtiesContext
class RemappedErrorViewIntegrationTests {

	@LocalServerPort
	private int port;

	private TestRestTemplate template = new TestRestTemplate();

	@Test
	void directAccessToErrorPage() {
		String content = this.template.getForObject("http://localhost:" + this.port + "/spring/error", String.class);
		assertThat(content).contains("error");
		assertThat(content).contains("999");
	}

	@Test
	void forwardToErrorPage() {
		String content = this.template.getForObject("http://localhost:" + this.port + "/spring/", String.class);
		assertThat(content).contains("error");
		assertThat(content).contains("500");
	}

	@Configuration(proxyBeanMethods = false)
	@Import({ PropertyPlaceholderAutoConfiguration.class, WebMvcAutoConfiguration.class,
			HttpMessageConvertersAutoConfiguration.class, ServletWebServerFactoryAutoConfiguration.class,
			DispatcherServletAutoConfiguration.class, ErrorMvcAutoConfiguration.class })
	@Controller
	static class TestConfiguration implements ErrorPageRegistrar {

		@RequestMapping("/")
		String home() {
			throw new RuntimeException("Planned!");
		}

		@Override
		public void registerErrorPages(ErrorPageRegistry errorPageRegistry) {
			errorPageRegistry.addErrorPages(new ErrorPage("/spring/error"));
		}

		// For manual testing
		static void main(String[] args) {
			new SpringApplicationBuilder(TestConfiguration.class).properties("spring.mvc.servlet.path:spring/*")
					.run(args);
		}

	}

}
