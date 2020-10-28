package org.springframework.boot.docs.actuate.metrics;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;

/**
 * Example to show injection and use of a {@link MeterRegistry}.
 *

 */
public class MetricsMeterRegistryInjectionExample {

	// tag::component[]
	class Dictionary {

		private final List<String> words = new CopyOnWriteArrayList<>();

		Dictionary(MeterRegistry registry) {
			registry.gaugeCollectionSize("dictionary.size", Tags.empty(), this.words);
		}

		// …

	}
	// end::component[]

}
