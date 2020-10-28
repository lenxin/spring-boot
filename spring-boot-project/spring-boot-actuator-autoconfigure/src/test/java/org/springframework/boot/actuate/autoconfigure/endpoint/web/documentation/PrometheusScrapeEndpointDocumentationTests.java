package org.springframework.boot.actuate.autoconfigure.endpoint.web.documentation;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.CollectorRegistry;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.metrics.export.prometheus.PrometheusScrapeEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for generating documentation describing the {@link PrometheusScrapeEndpoint}.
 *


 */
class PrometheusScrapeEndpointDocumentationTests extends MockMvcEndpointDocumentationTests {

	@Test
	void prometheus() throws Exception {
		this.mockMvc.perform(get("/actuator/prometheus")).andExpect(status().isOk()).andDo(document("prometheus/all"));
	}

	@Test
	void filteredPrometheus() throws Exception {
		this.mockMvc
				.perform(get("/actuator/prometheus").param("includedNames",
						"jvm_memory_used_bytes,jvm_memory_committed_bytes"))
				.andExpect(status().isOk())
				.andDo(document("prometheus/names", requestParameters(parameterWithName("includedNames")
						.description("Restricts the samples to those that match the names. Optional.").optional())));
	}

	@Configuration(proxyBeanMethods = false)
	@Import(BaseDocumentationConfiguration.class)
	static class TestConfiguration {

		@Bean
		PrometheusScrapeEndpoint endpoint() {
			CollectorRegistry collectorRegistry = new CollectorRegistry(true);
			PrometheusMeterRegistry meterRegistry = new PrometheusMeterRegistry((key) -> null, collectorRegistry,
					Clock.SYSTEM);
			new JvmMemoryMetrics().bindTo(meterRegistry);
			return new PrometheusScrapeEndpoint(collectorRegistry);
		}

	}

}
