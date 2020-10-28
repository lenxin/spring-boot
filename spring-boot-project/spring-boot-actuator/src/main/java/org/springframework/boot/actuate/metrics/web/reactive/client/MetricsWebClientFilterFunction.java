package org.springframework.boot.actuate.metrics.web.reactive.client;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

import org.springframework.boot.actuate.metrics.AutoTimer;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;

/**
 * {@link ExchangeFilterFunction} applied via a {@link MetricsWebClientCustomizer} to
 * record metrics.
 *


 * @since 2.1.0
 */
public class MetricsWebClientFilterFunction implements ExchangeFilterFunction {

	private static final String METRICS_WEBCLIENT_START_TIME = MetricsWebClientFilterFunction.class.getName()
			+ ".START_TIME";

	private final MeterRegistry meterRegistry;

	private final WebClientExchangeTagsProvider tagProvider;

	private final String metricName;

	private final AutoTimer autoTimer;

	/**
	 * Create a new {@code MetricsWebClientFilterFunction}.
	 * @param meterRegistry the registry to which metrics are recorded
	 * @param tagProvider provider for metrics tags
	 * @param metricName name of the metric to record
	 * @param autoTimer the auto-timer configuration or {@code null} to disable
	 * @since 2.2.0
	 */
	public MetricsWebClientFilterFunction(MeterRegistry meterRegistry, WebClientExchangeTagsProvider tagProvider,
			String metricName, AutoTimer autoTimer) {
		this.meterRegistry = meterRegistry;
		this.tagProvider = tagProvider;
		this.metricName = metricName;
		this.autoTimer = (autoTimer != null) ? autoTimer : AutoTimer.DISABLED;
	}

	@Override
	public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
		if (!this.autoTimer.isEnabled()) {
			return next.exchange(request);
		}
		return next.exchange(request).as((responseMono) -> instrumentResponse(request, responseMono))
				.contextWrite(this::putStartTime);
	}

	private Mono<ClientResponse> instrumentResponse(ClientRequest request, Mono<ClientResponse> responseMono) {
		final AtomicBoolean responseReceived = new AtomicBoolean();
		return Mono.deferContextual((ctx) -> responseMono.doOnEach((signal) -> {
			if (signal.isOnNext() || signal.isOnError()) {
				responseReceived.set(true);
				Iterable<Tag> tags = this.tagProvider.tags(request, signal.get(), signal.getThrowable());
				recordTimer(tags, getStartTime(ctx));
			}
		}).doFinally((signalType) -> {
			if (!responseReceived.get() && SignalType.CANCEL.equals(signalType)) {
				Iterable<Tag> tags = this.tagProvider.tags(request, null, null);
				recordTimer(tags, getStartTime(ctx));
			}
		}));
	}

	private void recordTimer(Iterable<Tag> tags, Long startTime) {
		this.autoTimer.builder(this.metricName).tags(tags).description("Timer of WebClient operation")
				.register(this.meterRegistry).record(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
	}

	private Long getStartTime(ContextView context) {
		return context.get(METRICS_WEBCLIENT_START_TIME);
	}

	private Context putStartTime(Context context) {
		return context.put(METRICS_WEBCLIENT_START_TIME, System.nanoTime());
	}

}
