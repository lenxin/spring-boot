package org.springframework.boot.docs.context.properties.bind.constructor;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.boot.convert.DurationUnit;

/**
 * A {@link ConfigurationProperties @ConfigurationProperties} example that uses
 * {@link Duration}.
 *

 */
// tag::example[]
@ConfigurationProperties("app.system")
@ConstructorBinding
public class AppSystemProperties {

	private final Duration sessionTimeout;

	private final Duration readTimeout;

	public AppSystemProperties(@DurationUnit(ChronoUnit.SECONDS) @DefaultValue("30s") Duration sessionTimeout,
			@DefaultValue("1000ms") Duration readTimeout) {
		this.sessionTimeout = sessionTimeout;
		this.readTimeout = readTimeout;
	}

	public Duration getSessionTimeout() {
		return this.sessionTimeout;
	}

	public Duration getReadTimeout() {
		return this.readTimeout;
	}

}
// end::example[]
