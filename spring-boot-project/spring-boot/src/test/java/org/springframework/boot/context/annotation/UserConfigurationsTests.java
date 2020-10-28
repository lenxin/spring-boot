package org.springframework.boot.context.annotation;

import java.io.InputStream;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link UserConfigurations}.
 *

 */
class UserConfigurationsTests {

	@Test
	void ofShouldCreateUnorderedConfigurations() {
		UserConfigurations configurations = UserConfigurations.of(OutputStream.class, InputStream.class);
		assertThat(Configurations.getClasses(configurations)).containsExactly(OutputStream.class, InputStream.class);
	}

}
