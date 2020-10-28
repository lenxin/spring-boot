package org.springframework.boot.web.reactive.result.view;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.context.support.GenericApplicationContext;

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
		this.resolver.setPrefix(this.prefix);
		this.resolver.setSuffix(".html");
	}

	@Test
	void resolveNonExistent() {
		assertThat(this.resolver.resolveViewName("bar", null).block(Duration.ofSeconds(30))).isNull();
	}

	@Test
	void resolveExisting() {
		assertThat(this.resolver.resolveViewName("template", null).block(Duration.ofSeconds(30))).isNotNull();
	}

}
