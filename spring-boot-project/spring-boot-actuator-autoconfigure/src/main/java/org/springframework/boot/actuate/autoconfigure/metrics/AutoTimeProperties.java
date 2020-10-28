package org.springframework.boot.actuate.autoconfigure.metrics;

import io.micrometer.core.instrument.Timer.Builder;

import org.springframework.boot.actuate.metrics.AutoTimer;

/**
 * Nested configuration properties for items that are automatically timed.
 *



 * @since 2.2.0
 */
public final class AutoTimeProperties implements AutoTimer {

	private boolean enabled = true;

	private boolean percentilesHistogram;

	private double[] percentiles;

	/**
	 * Create an instance that automatically time requests with no percentiles.
	 */
	public AutoTimeProperties() {
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isPercentilesHistogram() {
		return this.percentilesHistogram;
	}

	public void setPercentilesHistogram(boolean percentilesHistogram) {
		this.percentilesHistogram = percentilesHistogram;
	}

	public double[] getPercentiles() {
		return this.percentiles;
	}

	public void setPercentiles(double[] percentiles) {
		this.percentiles = percentiles;
	}

	@Override
	public void apply(Builder builder) {
		builder.publishPercentileHistogram(this.percentilesHistogram).publishPercentiles(this.percentiles);
	}

}
