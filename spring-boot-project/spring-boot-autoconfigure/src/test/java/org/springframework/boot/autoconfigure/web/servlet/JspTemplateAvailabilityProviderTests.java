package org.springframework.boot.autoconfigure.web.servlet;

import org.junit.jupiter.api.Test;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link JspTemplateAvailabilityProvider}.
 *

 */
class JspTemplateAvailabilityProviderTests {

	private final JspTemplateAvailabilityProvider provider = new JspTemplateAvailabilityProvider();

	private final ResourceLoader resourceLoader = new DefaultResourceLoader();

	private final MockEnvironment environment = new MockEnvironment();

	@Test
	void availabilityOfTemplateThatDoesNotExist() {
		assertThat(isTemplateAvailable("whatever")).isFalse();
	}

	@Test
	void availabilityOfTemplateWithCustomPrefix() {
		this.environment.setProperty("spring.mvc.view.prefix", "classpath:/custom-templates/");
		assertThat(isTemplateAvailable("custom.jsp")).isTrue();
	}

	@Test
	void availabilityOfTemplateWithCustomSuffix() {
		this.environment.setProperty("spring.mvc.view.prefix", "classpath:/custom-templates/");
		this.environment.setProperty("spring.mvc.view.suffix", ".jsp");
		assertThat(isTemplateAvailable("suffixed")).isTrue();
	}

	private boolean isTemplateAvailable(String view) {
		return this.provider.isTemplateAvailable(view, this.environment, getClass().getClassLoader(),
				this.resourceLoader);
	}

}
