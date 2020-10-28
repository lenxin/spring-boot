package org.springframework.boot.context.config;

import org.junit.jupiter.api.Test;

import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.MockConfigurationPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Tests for {@link UseLegacyConfigProcessingException}.
 *


 */
class UseLegacyConfigProcessingExceptionTests {

	@Test
	void throwIfRequestedWhenMissingDoesNothing() {
		MockConfigurationPropertySource source = new MockConfigurationPropertySource();
		Binder binder = new Binder(source);
		UseLegacyConfigProcessingException.throwIfRequested(binder);
	}

	@Test
	void throwIfRequestedWhenFalseDoesNothing() {
		MockConfigurationPropertySource source = new MockConfigurationPropertySource();
		source.put("spring.config.use-legacy-processing", "false");
		Binder binder = new Binder(source);
		UseLegacyConfigProcessingException.throwIfRequested(binder);
	}

	@Test
	void throwIfRequestedWhenTrueThrowsException() {
		MockConfigurationPropertySource source = new MockConfigurationPropertySource();
		source.put("spring.config.use-legacy-processing", "true");
		Binder binder = new Binder(source);
		assertThatExceptionOfType(UseLegacyConfigProcessingException.class)
				.isThrownBy(() -> UseLegacyConfigProcessingException.throwIfRequested(binder))
				.satisfies((ex) -> assertThat(ex.getConfigurationProperty().getName())
						.hasToString("spring.config.use-legacy-processing"));
	}

}
