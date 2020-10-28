package org.springframework.boot.actuate.metrics.web.tomcat;

import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

/**
 * Tests for {@link TomcatMetricsBinder}.
 *

 */
class TomcatMetricsBinderTests {

	private final MeterRegistry meterRegistry = mock(MeterRegistry.class);

	@Test
	void destroySucceedsWhenCalledBeforeApplicationHasStarted() {
		new TomcatMetricsBinder(this.meterRegistry).destroy();
	}

}
