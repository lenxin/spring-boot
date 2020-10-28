package org.springframework.boot.autoconfigure.hateoas;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.testsupport.classpath.ClassPathExclusions;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebApplicationContext;
import org.springframework.mock.web.MockServletContext;

/**
 * Tests for {@link HypermediaAutoConfiguration} when Jackson is not on the classpath.
 *

 */
@ClassPathExclusions("jackson-*.jar")
class HypermediaAutoConfigurationWithoutJacksonTests {

	private AnnotationConfigServletWebApplicationContext context;

	@Test
	void jacksonRelatedConfigurationBacksOff() {
		this.context = new AnnotationConfigServletWebApplicationContext();
		this.context.register(BaseConfig.class);
		this.context.setServletContext(new MockServletContext());
		this.context.refresh();
	}

	@ImportAutoConfiguration({ HttpMessageConvertersAutoConfiguration.class, WebMvcAutoConfiguration.class,
			HypermediaAutoConfiguration.class })
	static class BaseConfig {

	}

}
