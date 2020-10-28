package org.springframework.boot.devtools.tunnel.server;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link StaticPortProvider}.
 *

 */
class StaticPortProviderTests {

	@Test
	void portMustBePositive() {
		assertThatIllegalArgumentException().isThrownBy(() -> new StaticPortProvider(0))
				.withMessageContaining("Port must be positive");
	}

	@Test
	void getPort() {
		StaticPortProvider provider = new StaticPortProvider(123);
		assertThat(provider.getPort()).isEqualTo(123);
	}

}
