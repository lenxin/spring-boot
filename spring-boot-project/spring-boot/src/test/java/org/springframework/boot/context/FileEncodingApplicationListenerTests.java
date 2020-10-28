package org.springframework.boot.context;

import org.junit.jupiter.api.Test;

import org.springframework.boot.DefaultBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.test.context.support.TestPropertySourceUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/**
 * Tests for {@link FileEncodingApplicationListener}.
 *

 */
class FileEncodingApplicationListenerTests {

	private final FileEncodingApplicationListener initializer = new FileEncodingApplicationListener();

	private final ConfigurableEnvironment environment = new StandardEnvironment();

	private final ApplicationEnvironmentPreparedEvent event = new ApplicationEnvironmentPreparedEvent(
			new DefaultBootstrapContext(), new SpringApplication(), new String[0], this.environment);

	@Test
	void testIllegalState() {
		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.environment,
				"spring.mandatory_file_encoding=FOO");
		ConfigurationPropertySources.attach(this.environment);
		assertThatIllegalStateException().isThrownBy(() -> this.initializer.onApplicationEvent(this.event));
	}

	@Test
	void testSunnyDayNothingMandated() {
		this.initializer.onApplicationEvent(this.event);
	}

	@Test
	void testSunnyDayMandated() {
		assertThat(System.getProperty("file.encoding")).isNotNull();
		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.environment,
				"spring.mandatory_file_encoding:" + System.getProperty("file.encoding"));
		ConfigurationPropertySources.attach(this.environment);
		this.initializer.onApplicationEvent(this.event);
	}

}
