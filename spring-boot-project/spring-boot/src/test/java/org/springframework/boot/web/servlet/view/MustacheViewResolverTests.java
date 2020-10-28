package org.springframework.boot.web.servlet.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.context.support.GenericApplicationContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.servlet.View;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MustacheViewResolver}.
 *


 */
class MustacheViewResolverTests {

	private final String prefix = "classpath:/" + getClass().getPackage().getName().replace(".", "/") + "/";

	private MustacheViewResolver resolver = new MustacheViewResolver();

	@BeforeEach
	void init() {
		GenericApplicationContext applicationContext = new GenericApplicationContext();
		applicationContext.refresh();
		this.resolver.setApplicationContext(applicationContext);
		this.resolver.setServletContext(new MockServletContext());
		this.resolver.setPrefix(this.prefix);
		this.resolver.setSuffix(".html");
	}

	@Test
	void resolveNonExistent() throws Exception {
		assertThat(this.resolver.resolveViewName("bar", null)).isNull();
	}

	@Test
	void resolveExisting() throws Exception {
		assertThat(this.resolver.resolveViewName("template", null)).isNotNull();
	}

	@Test
	void setsContentType() throws Exception {
		this.resolver.setContentType("application/octet-stream");
		View view = this.resolver.resolveViewName("template", null);
		assertThat(view.getContentType()).isEqualTo("application/octet-stream");

	}

}
