package org.springframework.boot.context.properties.source;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ConfigurationPropertySourcesPropertySource}.
 *


 */
class ConfigurationPropertySourcesPropertySourceTests {

	private List<ConfigurationPropertySource> configurationSources = new ArrayList<>();

	private ConfigurationPropertySourcesPropertySource propertySource = new ConfigurationPropertySourcesPropertySource(
			"test", this.configurationSources);

	@Test
	void getPropertyShouldReturnValue() {
		this.configurationSources.add(new MockConfigurationPropertySource("foo.bar", "baz"));
		assertThat(this.propertySource.getProperty("foo.bar")).isEqualTo("baz");
	}

	@Test
	void getPropertyWhenNameIsNotValidShouldReturnNull() {
		this.configurationSources.add(new MockConfigurationPropertySource("foo.bar", "baz"));
		assertThat(this.propertySource.getProperty("FOO.B-A-R")).isNull();
		assertThat(this.propertySource.getProperty("FOO.B A R")).isNull();
		assertThat(this.propertySource.getProperty(".foo.bar")).isNull();
	}

	@Test
	void getPropertyWhenMultipleShouldReturnFirst() {
		this.configurationSources.add(new MockConfigurationPropertySource("foo.bar", "baz"));
		this.configurationSources.add(new MockConfigurationPropertySource("foo.bar", "bill"));
		assertThat(this.propertySource.getProperty("foo.bar")).isEqualTo("baz");
	}

	@Test
	void getPropertyWhenNoneShouldReturnFirst() {
		this.configurationSources.add(new MockConfigurationPropertySource("foo.bar", "baz"));
		assertThat(this.propertySource.getProperty("foo.foo")).isNull();
	}

	@Test
	void getPropertyOriginShouldReturnOrigin() {
		this.configurationSources.add(new MockConfigurationPropertySource("foo.bar", "baz", "line1"));
		assertThat(this.propertySource.getOrigin("foo.bar").toString()).isEqualTo("line1");
	}

	@Test
	void getPropertyOriginWhenMissingShouldReturnNull() {
		this.configurationSources.add(new MockConfigurationPropertySource("foo.bar", "baz", "line1"));
		assertThat(this.propertySource.getOrigin("foo.foo")).isNull();
	}

	@Test
	void getNameShouldReturnName() {
		assertThat(this.propertySource.getName()).isEqualTo("test");
	}

	@Test
	void getSourceShouldReturnSource() {
		assertThat(this.propertySource.getSource()).isSameAs(this.configurationSources);
	}

}
