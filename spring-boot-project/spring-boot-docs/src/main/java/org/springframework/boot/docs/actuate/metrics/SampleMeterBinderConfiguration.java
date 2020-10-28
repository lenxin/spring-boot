package org.springframework.boot.docs.actuate.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.MeterBinder;

import org.springframework.context.annotation.Bean;

/**
 * Example to show configuration of a custom {@link MeterBinder}.
 *

 */
public class SampleMeterBinderConfiguration {

	// tag::example[]
	@Bean
	MeterBinder queueSize(Queue queue) {
		return (registry) -> Gauge.builder("queueSize", queue::size).register(registry);
	}
	// end::example[]

	static class Queue {

		int size() {
			return 5;
		}

	}

}
