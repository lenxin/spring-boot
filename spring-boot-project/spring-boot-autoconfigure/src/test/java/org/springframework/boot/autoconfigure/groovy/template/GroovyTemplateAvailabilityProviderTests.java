package org.springframework.boot.autoconfigure.groovy.template;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link GroovyTemplateAvailabilityProvider}.
 *

 */
class GroovyTemplateAvailabilityProviderTests {

	private TemplateAvailabilityProvider provider = new GroovyTemplateAvailabilityProvider();

	private ResourceLoader resourceLoader = new DefaultResourceLoader();

	private MockEnvironment environment = new MockEnvironment();

	@Test
	void availabilityOfTemplateInDefaultLocation() {
		assertThat(this.provider.isTemplateAvailable("home", this.environment, getClass().getClassLoader(),
				this.resourceLoader)).isTrue();
	}

	@Test
	void availabilityOfTemplateThatDoesNotExist() {
		assertThat(this.provider.isTemplateAvailable("whatever", this.environment, getClass().getClassLoader(),
				this.resourceLoader)).isFalse();
	}

	@Test
	void availabilityOfTemplateWithCustomLoaderPath() {
		this.environment.setProperty("spring.groovy.template.resource-loader-path", "classpath:/custom-templates/");
		assertThat(this.provider.isTemplateAvailable("custom", this.environment, getClass().getClassLoader(),
				this.resourceLoader)).isTrue();
	}

	@Test
	void availabilityOfTemplateWithCustomLoaderPathConfiguredAsAList() {
		this.environment.setProperty("spring.groovy.template.resource-loader-path[0]", "classpath:/custom-templates/");
		assertThat(this.provider.isTemplateAvailable("custom", this.environment, getClass().getClassLoader(),
				this.resourceLoader)).isTrue();
	}

	@Test
	void availabilityOfTemplateWithCustomPrefix() {
		this.environment.setProperty("spring.groovy.template.prefix", "prefix/");
		assertThat(this.provider.isTemplateAvailable("prefixed", this.environment, getClass().getClassLoader(),
				this.resourceLoader)).isTrue();
	}

	@Test
	void availabilityOfTemplateWithCustomSuffix() {
		this.environment.setProperty("spring.groovy.template.suffix", ".groovytemplate");
		assertThat(this.provider.isTemplateAvailable("suffixed", this.environment, getClass().getClassLoader(),
				this.resourceLoader)).isTrue();
	}

}
