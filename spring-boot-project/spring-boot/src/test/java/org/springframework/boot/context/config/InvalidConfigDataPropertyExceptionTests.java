package org.springframework.boot.context.config;

import org.apache.commons.logging.Log;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.springframework.boot.context.properties.source.ConfigurationProperty;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.origin.MockOrigin;
import org.springframework.mock.env.MockPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link InvalidConfigDataPropertyException}.
 *


 */
class InvalidConfigDataPropertyExceptionTests {

	private ConfigDataResource resource = new TestConfigDataResource();

	private ConfigurationPropertyName replacement = ConfigurationPropertyName.of("replacement");

	private ConfigurationPropertyName invalid = ConfigurationPropertyName.of("invalid");

	private ConfigurationProperty property = new ConfigurationProperty(this.invalid, "bad", MockOrigin.of("origin"));

	private Log logger = mock(Log.class);

	@Test
	void createHasCorrectMessage() {
		assertThat(new InvalidConfigDataPropertyException(this.property, this.replacement, this.resource)).hasMessage(
				"Property 'invalid' imported from location 'test' is invalid and should be replaced with 'replacement' [origin: origin]");
	}

	@Test
	void createWhenNoLocationHasCorrectMessage() {
		assertThat(new InvalidConfigDataPropertyException(this.property, this.replacement, null))
				.hasMessage("Property 'invalid' is invalid and should be replaced with 'replacement' [origin: origin]");
	}

	@Test
	void createWhenNoReplacementHasCorrectMessage() {
		assertThat(new InvalidConfigDataPropertyException(this.property, null, this.resource))
				.hasMessage("Property 'invalid' imported from location 'test' is invalid [origin: origin]");
	}

	@Test
	void createWhenNoOriginHasCorrectMessage() {
		ConfigurationProperty property = new ConfigurationProperty(this.invalid, "bad", null);
		assertThat(new InvalidConfigDataPropertyException(property, this.replacement, this.resource)).hasMessage(
				"Property 'invalid' imported from location 'test' is invalid and should be replaced with 'replacement'");
	}

	@Test
	void getPropertyReturnsProperty() {
		InvalidConfigDataPropertyException exception = new InvalidConfigDataPropertyException(this.property,
				this.replacement, this.resource);
		assertThat(exception.getProperty()).isEqualTo(this.property);
	}

	@Test
	void getLocationReturnsLocation() {
		InvalidConfigDataPropertyException exception = new InvalidConfigDataPropertyException(this.property,
				this.replacement, this.resource);
		assertThat(exception.getLocation()).isEqualTo(this.resource);
	}

	@Test
	void getReplacementReturnsReplacement() {
		InvalidConfigDataPropertyException exception = new InvalidConfigDataPropertyException(this.property,
				this.replacement, this.resource);
		assertThat(exception.getReplacement()).isEqualTo(this.replacement);
	}

	@Test
	@Disabled("Disabled until spring.profiles support is dropped")
	void throwOrWarnWhenHasInvalidPropertyThrowsException() {
		MockPropertySource propertySource = new MockPropertySource();
		propertySource.setProperty("spring.profiles", "a");
		ConfigDataEnvironmentContributor contributor = ConfigDataEnvironmentContributor.ofExisting(propertySource);
		assertThatExceptionOfType(InvalidConfigDataPropertyException.class)
				.isThrownBy(() -> InvalidConfigDataPropertyException.throwOrWarn(this.logger, contributor))
				.withMessageStartingWith("Property 'spring.profiles' is invalid and should be replaced with "
						+ "'spring.config.activate.on-profile'");
	}

	@Test
	void throwOrWarnWhenHasNoInvalidPropertyDoesNothing() {
		ConfigDataEnvironmentContributor contributor = ConfigDataEnvironmentContributor
				.ofExisting(new MockPropertySource());
		InvalidConfigDataPropertyException.throwOrWarn(this.logger, contributor);
	}

	@Test
	void throwOrWarnWhenHasWarningPropertyLogsWarning() {
		MockPropertySource propertySource = new MockPropertySource();
		propertySource.setProperty("spring.profiles", "a");
		ConfigDataEnvironmentContributor contributor = ConfigDataEnvironmentContributor.ofExisting(propertySource);
		InvalidConfigDataPropertyException.throwOrWarn(this.logger, contributor);
		verify(this.logger).warn("Property 'spring.profiles' is invalid and should be replaced with "
				+ "'spring.config.activate.on-profile' [origin: \"spring.profiles\" from property source \"mockProperties\"]");
	}

	private static class TestConfigDataResource extends ConfigDataResource {

		@Override
		public String toString() {
			return "test";
		}

	}

}
