package smoketest.hazelcast3;

import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cache.CacheManager;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class SampleHazelcast4ApplicationTests {

	@Autowired
	private WebTestClient webClient;

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private CountryRepository countryRepository;

	@Test
	void cacheManagerIsUsingHazelcast() {
		assertThat(this.cacheManager).isInstanceOf(HazelcastCacheManager.class);
	}

	@Test
	void healthEndpointHasHazelcastContributor() {
		this.webClient.get().uri("/actuator/health/hazelcast").exchange().expectStatus().isOk().expectBody()
				.jsonPath("status").isEqualTo("UP").jsonPath("details.name").isNotEmpty().jsonPath("details.uuid")
				.isNotEmpty();
	}

	@Test
	void metricsEndpointHasCacheMetrics() {
		this.webClient.get().uri("/actuator/metrics/cache.entries").exchange().expectStatus().isOk();
	}

}
