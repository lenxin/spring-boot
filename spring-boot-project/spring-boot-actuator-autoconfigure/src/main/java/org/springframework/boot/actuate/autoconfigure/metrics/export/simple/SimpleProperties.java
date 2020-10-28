package org.springframework.boot.actuate.autoconfigure.metrics.export.simple;

import java.time.Duration;

import io.micrometer.core.instrument.simple.CountingMode;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link ConfigurationProperties @ConfigurationProperties} for configuring metrics export
 * to a {@link SimpleMeterRegistry}.
 *


 * @since 2.0.0
 */
@ConfigurationProperties(prefix = "management.metrics.export.simple")
public class SimpleProperties {

	/**
	 * Step size (i.e. reporting frequency) to use.
	 */
	private Duration step = Duration.ofMinutes(1);

	/**
	 * Counting mode.
	 */
	private CountingMode mode = CountingMode.CUMULATIVE;

	public Duration getStep() {
		return this.step;
	}

	public void setStep(Duration step) {
		this.step = step;
	}

	public CountingMode getMode() {
		return this.mode;
	}

	public void setMode(CountingMode mode) {
		this.mode = mode;
	}

}
