package smoketest.servlet;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Basic integration tests for demo application.
 *

 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SampleServletApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void testHomeIsSecure() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		ResponseEntity<String> entity = this.restTemplate.exchange("/", HttpMethod.GET, new HttpEntity<Void>(headers),
				String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	void testHome() {
		ResponseEntity<String> entity = this.restTemplate.withBasicAuth("user", getPassword()).getForEntity("/",
				String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).isEqualTo("Hello World");
	}

	private String getPassword() {
		return "password";
	}

}
