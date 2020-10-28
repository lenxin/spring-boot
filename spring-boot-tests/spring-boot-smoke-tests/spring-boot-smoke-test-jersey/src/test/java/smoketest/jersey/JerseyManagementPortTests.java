package smoketest.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort;
import org.springframework.boot.autoconfigure.jersey.ResourceConfigCustomizer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for separate management and main service ports.
 *

 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "management.server.port=0")
class JerseyManagementPortTests {

	@LocalServerPort
	private int port;

	@LocalManagementPort
	private int managementPort;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	void resourceShouldBeAvailableOnMainPort() {
		ResponseEntity<String> entity = this.testRestTemplate.getForEntity("http://localhost:" + this.port + "/test",
				String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("test");
	}

	@Test
	void resourceShouldNotBeAvailableOnManagementPort() {
		ResponseEntity<String> entity = this.testRestTemplate
				.getForEntity("http://localhost:" + this.managementPort + "/test", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@TestConfiguration
	static class ResourceConfigConfiguration {

		@Bean
		ResourceConfigCustomizer customizer() {
			return new ResourceConfigCustomizer() {
				@Override
				public void customize(ResourceConfig config) {
					config.register(TestEndpoint.class);
				}
			};
		}

		@Path("/test")
		public static class TestEndpoint {

			@GET
			public String test() {
				return "test";
			}

		}

	}

}
