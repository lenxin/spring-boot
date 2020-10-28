package org.springframework.boot.actuate.autoconfigure.metrics;

import java.time.Duration;

import io.micrometer.core.instrument.Meter;

/**
 * A boundary for a service-level agreement (SLA) for use when configuring Micrometer. Can
 * be specified as either a {@link Long} (applicable to timers and distribution summaries)
 * or a {@link Duration} (applicable to only timers).
 *

 * @since 2.0.0
 * @deprecated as of 2.3.0 in favor of {@link ServiceLevelObjectiveBoundary}
 */
@Deprecated
public final class ServiceLevelAgreementBoundary {

	private final MeterValue value;

	ServiceLevelAgreementBoundary(MeterValue value) {
		this.value = value;
	}

	/**
	 * Return the underlying value of the SLA in form suitable to apply to the given meter
	 * type.
	 * @param meterType the meter type
	 * @return the value or {@code null} if the value cannot be applied
	 */
	public Long getValue(Meter.Type meterType) {
		Double value = this.value.getValue(meterType);
		return (value != null) ? value.longValue() : null;
	}

	/**
	 * Return a new {@link ServiceLevelAgreementBoundary} instance for the given long
	 * value.
	 * @param value the source value
	 * @return a {@link ServiceLevelAgreementBoundary} instance
	 */
	public static ServiceLevelAgreementBoundary valueOf(long value) {
		return new ServiceLevelAgreementBoundary(MeterValue.valueOf(value));
	}

	/**
	 * Return a new {@link ServiceLevelAgreementBoundary} instance for the given String
	 * value.
	 * @param value the source value
	 * @return a {@link ServiceLevelAgreementBoundary} instance
	 */
	public static ServiceLevelAgreementBoundary valueOf(String value) {
		return new ServiceLevelAgreementBoundary(MeterValue.valueOf(value));
	}

}
