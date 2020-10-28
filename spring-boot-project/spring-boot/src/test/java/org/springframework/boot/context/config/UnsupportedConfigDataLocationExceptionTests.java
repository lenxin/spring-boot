package org.springframework.boot.context.config;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link UnsupportedConfigDataLocationException}.
 *


 */
class UnsupportedConfigDataLocationExceptionTests {

	@Test
	void createSetsMessage() {
		UnsupportedConfigDataLocationException exception = new UnsupportedConfigDataLocationException(
				ConfigDataLocation.of("test"));
		assertThat(exception).hasMessage("Unsupported config data location 'test'");
	}

	@Test
	void getLocationReturnsLocation() {
		ConfigDataLocation location = ConfigDataLocation.of("test");
		UnsupportedConfigDataLocationException exception = new UnsupportedConfigDataLocationException(location);
		assertThat(exception.getLocation()).isEqualTo(location);
	}

}
