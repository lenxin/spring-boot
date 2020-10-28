package org.springframework.boot.logging.log4j2;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SpringBootConfigurationFactory}.
 *

 */
class SpringBootConfigurationFactoryTests {

	@Test
	void producesConfigurationWithShutdownHookDisabled() throws IOException {
		ConfigurationSource source = new ConfigurationSource(new ByteArrayInputStream(new byte[0]));
		assertThat(new SpringBootConfigurationFactory().getConfiguration(new LoggerContext(""), source)
				.isShutdownHookEnabled()).isFalse();
	}

}
