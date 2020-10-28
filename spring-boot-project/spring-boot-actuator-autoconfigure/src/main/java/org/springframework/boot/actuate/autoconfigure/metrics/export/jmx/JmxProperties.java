package org.springframework.boot.actuate.autoconfigure.metrics.export.jmx;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link ConfigurationProperties @ConfigurationProperties} for configuring JMX metrics
 * export.
 *


 * @since 2.0.0
 */
@ConfigurationProperties(prefix = "management.metrics.export.jmx")
public class JmxProperties {

	/**
	 * Metrics JMX domain name.
	 */
	private String domain = "metrics";

	/**
	 * Step size (i.e. reporting frequency) to use.
	 */
	private Duration step = Duration.ofMinutes(1);

	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Duration getStep() {
		return this.step;
	}

	public void setStep(Duration step) {
		this.step = step;
	}

}
