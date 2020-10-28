package org.springframework.boot.autoconfigure.jersey;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link JerseyAutoConfiguration} when using custom ObjectMapper.
 *

 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT,
		properties = "spring.jackson.default-property-inclusion=non_null")
@DirtiesContext
class JerseyAutoConfigurationCustomObjectMapperProviderTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
		ResponseEntity<String> response = this.restTemplate.getForEntity("/rest/message", String.class);
		assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
		assertThat("{\"subject\":\"Jersey\"}").isEqualTo(response.getBody());
	}

	@MinimalWebConfiguration
	@ApplicationPath("/rest")
	@Path("/message")
	public static class Application extends ResourceConfig {

		Application() {
			register(Application.class);
		}

		@GET
		public Message message() {
			return new Message("Jersey", null);
		}

		static void main(String[] args) {
			SpringApplication.run(Application.class, args);
		}

	}

	public static class Message {

		private String subject;

		private String body;

		Message(String subject, String body) {
			this.subject = subject;
			this.body = body;
		}

		public String getSubject() {
			return this.subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getBody() {
			return this.body;
		}

		public void setBody(String body) {
			this.body = body;
		}

	}

	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@Configuration
	@Import({ ServletWebServerFactoryAutoConfiguration.class, JacksonAutoConfiguration.class,
			JerseyAutoConfiguration.class, PropertyPlaceholderAutoConfiguration.class })
	protected @interface MinimalWebConfiguration {

	}

}
