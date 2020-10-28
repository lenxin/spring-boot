package smoketest.hateoas;

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

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SampleHateoasApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void hasHalLinks() {
		ResponseEntity<String> entity = this.restTemplate.getForEntity("/customers/1", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).startsWith("{\"id\":1,\"firstName\":\"Oliver\",\"lastName\":\"Gierke\"");
		assertThat(entity.getBody()).contains("_links\":{\"self\":{\"href\"");
	}

	@Test
	void producesJsonWhenXmlIsPreferred() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, "application/xml;q=0.9,application/json;q=0.8");
		HttpEntity<?> request = new HttpEntity<>(headers);
		ResponseEntity<String> response = this.restTemplate.exchange("/customers/1", HttpMethod.GET, request,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.parseMediaType("application/json"));
	}

}
