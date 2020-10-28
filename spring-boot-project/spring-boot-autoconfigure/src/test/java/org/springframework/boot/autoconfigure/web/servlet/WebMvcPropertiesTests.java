package org.springframework.boot.autoconfigure.web.servlet;

import java.util.Collections;
import java.util.Map;

import org.assertj.core.util.Throwables;
import org.junit.jupiter.api.Test;

import org.springframework.boot.context.properties.IncompatibleConfigurationException;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Tests for {@link WebMvcProperties}.
 *

 */
class WebMvcPropertiesTests {

	private final WebMvcProperties properties = new WebMvcProperties();

	@Test
	void servletPathWhenEndsWithSlashHasValidMappingAndPrefix() {
		bind("spring.mvc.servlet.path", "/foo/");
		assertThat(this.properties.getServlet().getServletMapping()).isEqualTo("/foo/*");
		assertThat(this.properties.getServlet().getServletPrefix()).isEqualTo("/foo");
	}

	@Test
	void servletPathWhenDoesNotEndWithSlashHasValidMappingAndPrefix() {
		bind("spring.mvc.servlet.path", "/foo");
		assertThat(this.properties.getServlet().getServletMapping()).isEqualTo("/foo/*");
		assertThat(this.properties.getServlet().getServletPrefix()).isEqualTo("/foo");
	}

	@Test
	void servletPathWhenHasWildcardThrowsException() {
		assertThatExceptionOfType(BindException.class).isThrownBy(() -> bind("spring.mvc.servlet.path", "/*"))
				.withRootCauseInstanceOf(IllegalArgumentException.class).satisfies(
						(ex) -> assertThat(Throwables.getRootCause(ex)).hasMessage("Path must not contain wildcards"));
	}

	@Test
	@SuppressWarnings("deprecation")
	void incompatiblePathMatchSuffixConfig() {
		this.properties.getPathmatch().setMatchingStrategy(WebMvcProperties.MatchingStrategy.PATH_PATTERN_PARSER);
		this.properties.getPathmatch().setUseSuffixPattern(true);
		assertThatExceptionOfType(IncompatibleConfigurationException.class)
				.isThrownBy(this.properties::checkConfiguration);
	}

	@Test
	@SuppressWarnings("deprecation")
	void incompatiblePathMatchRegisteredSuffixConfig() {
		this.properties.getPathmatch().setMatchingStrategy(WebMvcProperties.MatchingStrategy.PATH_PATTERN_PARSER);
		this.properties.getPathmatch().setUseRegisteredSuffixPattern(true);
		assertThatExceptionOfType(IncompatibleConfigurationException.class)
				.isThrownBy(this.properties::checkConfiguration);
	}

	@Test
	void incompatiblePathMatchServletPathConfig() {
		this.properties.getPathmatch().setMatchingStrategy(WebMvcProperties.MatchingStrategy.PATH_PATTERN_PARSER);
		this.properties.getServlet().setPath("/test");
		assertThatExceptionOfType(IncompatibleConfigurationException.class)
				.isThrownBy(this.properties::checkConfiguration);
	}

	private void bind(String name, String value) {
		bind(Collections.singletonMap(name, value));
	}

	private void bind(Map<String, String> map) {
		ConfigurationPropertySource source = new MapConfigurationPropertySource(map);
		new Binder(source).bind("spring.mvc", Bindable.ofInstance(this.properties));
	}

}
