package org.springframework.boot.actuate.metrics.web.reactive.client;

import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link MetricsWebClientCustomizer}
 *

 */
class MetricsWebClientCustomizerTests {

	private MetricsWebClientCustomizer customizer;

	private WebClient.Builder clientBuilder;

	@BeforeEach
	void setup() {
		this.customizer = new MetricsWebClientCustomizer(mock(MeterRegistry.class),
				mock(WebClientExchangeTagsProvider.class), "test", null);
		this.clientBuilder = WebClient.builder();
	}

	@Test
	void customizeShouldAddFilterFunction() {
		this.clientBuilder.filter(mock(ExchangeFilterFunction.class));
		this.customizer.customize(this.clientBuilder);
		this.clientBuilder.filters(
				(filters) -> assertThat(filters).hasSize(2).first().isInstanceOf(MetricsWebClientFilterFunction.class));
	}

	@Test
	void customizeShouldNotAddDuplicateFilterFunction() {
		this.customizer.customize(this.clientBuilder);
		this.clientBuilder.filters((filters) -> assertThat(filters).hasSize(1));
		this.customizer.customize(this.clientBuilder);
		this.clientBuilder.filters(
				(filters) -> assertThat(filters).hasSize(1).first().isInstanceOf(MetricsWebClientFilterFunction.class));
	}

}
