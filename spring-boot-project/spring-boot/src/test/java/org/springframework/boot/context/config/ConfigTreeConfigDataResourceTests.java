package org.springframework.boot.context.config;

import java.io.File;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link ConfigTreeConfigDataResource}.
 *


 */
public class ConfigTreeConfigDataResourceTests {

	@Test
	void constructorWhenPathIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new ConfigTreeConfigDataResource(null))
				.withMessage("Path must not be null");
	}

	@Test
	void equalsWhenPathIsTheSameReturnsTrue() {
		ConfigTreeConfigDataResource location = new ConfigTreeConfigDataResource("/etc/config");
		ConfigTreeConfigDataResource other = new ConfigTreeConfigDataResource("/etc/config");
		assertThat(location).isEqualTo(other);
	}

	@Test
	void equalsWhenPathIsDifferentReturnsFalse() {
		ConfigTreeConfigDataResource location = new ConfigTreeConfigDataResource("/etc/config");
		ConfigTreeConfigDataResource other = new ConfigTreeConfigDataResource("other-location");
		assertThat(location).isNotEqualTo(other);
	}

	@Test
	void toStringReturnsDescriptiveString() {
		ConfigTreeConfigDataResource location = new ConfigTreeConfigDataResource("/etc/config");
		assertThat(location.toString()).isEqualTo("config tree [" + new File("/etc/config").getAbsolutePath() + "]");
	}

}
