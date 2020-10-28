package org.springframework.boot.autoconfigure;

import org.junit.jupiter.api.Test;

import org.springframework.boot.context.annotation.Configurations;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link AutoConfigurations}.
 *

 */
class AutoConfigurationsTests {

	@Test
	void ofShouldCreateOrderedConfigurations() {
		Configurations configurations = AutoConfigurations.of(AutoConfigureA.class, AutoConfigureB.class);
		assertThat(Configurations.getClasses(configurations)).containsExactly(AutoConfigureB.class,
				AutoConfigureA.class);
	}

	@AutoConfigureAfter(AutoConfigureB.class)
	static class AutoConfigureA {

	}

	static class AutoConfigureB {

	}

}
