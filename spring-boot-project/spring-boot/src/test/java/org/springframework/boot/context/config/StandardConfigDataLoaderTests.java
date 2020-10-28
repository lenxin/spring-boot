package org.springframework.boot.context.config;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link StandardConfigDataLoader}.
 *


 */
public class StandardConfigDataLoaderTests {

	private StandardConfigDataLoader loader = new StandardConfigDataLoader();

	private ConfigDataLoaderContext loaderContext = mock(ConfigDataLoaderContext.class);

	@Test
	void loadWhenLocationResultsInMultiplePropertySourcesAddsAllToConfigData() throws IOException {
		ClassPathResource resource = new ClassPathResource("configdata/yaml/application.yml");
		StandardConfigDataReference reference = new StandardConfigDataReference(
				ConfigDataLocation.of("classpath:configdata/yaml/application.yml"), null,
				"classpath:configdata/yaml/application", null, "yml", new YamlPropertySourceLoader());
		StandardConfigDataResource location = new StandardConfigDataResource(reference, resource);
		ConfigData configData = this.loader.load(this.loaderContext, location);
		assertThat(configData.getPropertySources().size()).isEqualTo(2);
		PropertySource<?> source1 = configData.getPropertySources().get(0);
		PropertySource<?> source2 = configData.getPropertySources().get(1);
		assertThat(source1.getName()).isEqualTo("Config resource 'classpath:configdata/yaml/application.yml' "
				+ "via location 'classpath:configdata/yaml/application.yml' (document #0)");
		assertThat(source1.getProperty("foo")).isEqualTo("bar");
		assertThat(source2.getName()).isEqualTo("Config resource 'classpath:configdata/yaml/application.yml' "
				+ "via location 'classpath:configdata/yaml/application.yml' (document #1)");
		assertThat(source2.getProperty("hello")).isEqualTo("world");
	}

	@Test
	void loadWhenPropertySourceIsEmptyAddsNothingToConfigData() throws IOException {
		ClassPathResource resource = new ClassPathResource("config/0-empty/testproperties.properties");
		StandardConfigDataReference reference = new StandardConfigDataReference(
				ConfigDataLocation.of("classpath:config/0-empty/testproperties.properties"), null,
				"config/0-empty/testproperties", null, "properties", new PropertiesPropertySourceLoader());
		StandardConfigDataResource location = new StandardConfigDataResource(reference, resource);
		ConfigData configData = this.loader.load(this.loaderContext, location);
		assertThat(configData.getPropertySources().size()).isEqualTo(0);
	}

}
