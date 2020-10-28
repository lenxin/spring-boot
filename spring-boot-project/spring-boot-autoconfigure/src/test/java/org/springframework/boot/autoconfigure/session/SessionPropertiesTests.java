package org.springframework.boot.autoconfigure.session;

import java.time.Duration;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

/**
 * Tests for {@link SessionProperties}.
 *

 */
class SessionPropertiesTests {

	@Test
	@SuppressWarnings("unchecked")
	void determineTimeoutWithTimeoutIgnoreFallback() {
		SessionProperties properties = new SessionProperties();
		properties.setTimeout(Duration.ofMinutes(1));
		Supplier<Duration> fallback = mock(Supplier.class);
		assertThat(properties.determineTimeout(fallback)).isEqualTo(Duration.ofMinutes(1));
		verifyNoInteractions(fallback);
	}

	@Test
	void determineTimeoutWithNoTimeoutUseFallback() {
		SessionProperties properties = new SessionProperties();
		properties.setTimeout(null);
		Duration fallback = Duration.ofMinutes(2);
		assertThat(properties.determineTimeout(() -> fallback)).isSameAs(fallback);
	}

}
