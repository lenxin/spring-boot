package org.springframework.boot.actuate.metrics.web.reactive.client;

import io.micrometer.core.instrument.MeterRegistry;

import org.springframework.boot.actuate.metrics.AutoTimer;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * {@link WebClientCustomizer} that configures the {@link WebClient} to record request
 * metrics.
 *

 * @since 2.1.0
 */
public class MetricsWebClientCustomizer implements WebClientCustomizer {

	private final MetricsWebClientFilterFunction filterFunction;

	/**
	 * Create a new {@code MetricsWebClientFilterFunction} that will record metrics using
	 * the given {@code meterRegistry} with tags provided by the given
	 * {@code tagProvider}.
	 * @param meterRegistry the meter registry
	 * @param tagProvider the tag provider
	 * @param metricName the name of the recorded metric
	 * @param autoTimer the auto-timers to apply or {@code null} to disable auto-timing
	 * @since 2.2.0
	 */
	public MetricsWebClientCustomizer(MeterRegistry meterRegistry, WebClientExchangeTagsProvider tagProvider,
			String metricName, AutoTimer autoTimer) {
		this.filterFunction = new MetricsWebClientFilterFunction(meterRegistry, tagProvider, metricName, autoTimer);
	}

	@Override
	public void customize(WebClient.Builder webClientBuilder) {
		webClientBuilder.filters((filterFunctions) -> {
			if (!filterFunctions.contains(this.filterFunction)) {
				filterFunctions.add(0, this.filterFunction);
			}
		});
	}

}
