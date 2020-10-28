package org.springframework.boot.actuate.metrics.export.prometheus;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.common.TextFormat;

import org.springframework.boot.actuate.endpoint.web.test.WebEndpointTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link PrometheusScrapeEndpoint}.
 *


 */
class PrometheusScrapeEndpointIntegrationTests {

	@WebEndpointTest
	void scrapeHasContentTypeText004(WebTestClient client) {
		client.get().uri("/actuator/prometheus").exchange().expectStatus().isOk().expectHeader()
				.contentType(MediaType.parseMediaType(TextFormat.CONTENT_TYPE_004)).expectBody(String.class)
				.value((body) -> assertThat(body).contains("counter1_total").contains("counter2_total")
						.contains("counter3_total"));
	}

	@WebEndpointTest
	void scrapeWithIncludedNames(WebTestClient client) {
		client.get().uri("/actuator/prometheus?includedNames=counter1_total,counter2_total").exchange().expectStatus()
				.isOk().expectHeader().contentType(MediaType.parseMediaType(TextFormat.CONTENT_TYPE_004))
				.expectBody(String.class).value((body) -> assertThat(body).contains("counter1_total")
						.contains("counter2_total").doesNotContain("counter3_total"));
	}

	@Configuration(proxyBeanMethods = false)
	static class TestConfiguration {

		@Bean
		PrometheusScrapeEndpoint prometheusScrapeEndpoint(CollectorRegistry collectorRegistry) {
			return new PrometheusScrapeEndpoint(collectorRegistry);
		}

		@Bean
		CollectorRegistry collectorRegistry() {
			return new CollectorRegistry(true);
		}

		@Bean
		MeterRegistry registry(CollectorRegistry registry) {
			PrometheusMeterRegistry meterRegistry = new PrometheusMeterRegistry((k) -> null, registry, Clock.SYSTEM);
			Counter.builder("counter1").register(meterRegistry);
			Counter.builder("counter2").register(meterRegistry);
			Counter.builder("counter3").register(meterRegistry);
			return meterRegistry;
		}

	}

}
