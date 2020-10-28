package smoketest.jetty.jsp;

import org.eclipse.jetty.server.handler.ContextHandler;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Basic integration tests for JSP application.
 *

 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT,
		classes = { SampleWebJspApplicationTests.JettyCustomizerConfig.class, SampleJettyJspApplication.class })
class SampleWebJspApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void testJspWithEl() {
		ResponseEntity<String> entity = this.restTemplate.getForEntity("/", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("/resources/text.txt");
	}

	@Configuration(proxyBeanMethods = false)
	static class JettyCustomizerConfig {

		// To allow aliased resources on Concourse Windows CI (See gh-15553) to be served
		// as static resources.
		@Bean
		JettyServerCustomizer jettyServerCustomizer() {
			return (server) -> {
				ContextHandler handler = (ContextHandler) server.getHandler();
				handler.addAliasCheck(new ContextHandler.ApproveAliases());
			};
		}

	}

}
