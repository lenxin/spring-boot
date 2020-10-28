package org.springframework.boot.env;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;

import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.origin.PropertySourceOrigin;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.mock.env.MockPropertySource;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.web.context.support.StandardServletEnvironment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Tests for {@link SpringApplicationJsonEnvironmentPostProcessor}.
 *




 */
class SpringApplicationJsonEnvironmentPostProcessorTests {

	private SpringApplicationJsonEnvironmentPostProcessor processor = new SpringApplicationJsonEnvironmentPostProcessor();

	private ConfigurableEnvironment environment = new StandardEnvironment();

	@Test
	void error() {
		assertThat(this.environment.resolvePlaceholders("${foo:}")).isEmpty();
		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.environment, "spring.application.json=foo:bar");
		assertThatExceptionOfType(JsonParseException.class)
				.isThrownBy(() -> this.processor.postProcessEnvironment(this.environment, null))
				.withMessageContaining("Cannot parse JSON");
	}

	@Test
	void missing() {
		assertThat(this.environment.resolvePlaceholders("${foo:}")).isEmpty();
		this.processor.postProcessEnvironment(this.environment, null);
		assertThat(this.environment.resolvePlaceholders("${foo:}")).isEmpty();
	}

	@Test
	void empty() {
		assertThat(this.environment.resolvePlaceholders("${foo:}")).isEmpty();
		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.environment, "spring.application.json={}");
		this.processor.postProcessEnvironment(this.environment, null);
		assertThat(this.environment.resolvePlaceholders("${foo:}")).isEmpty();
	}

	@Test
	void periodSeparated() {
		assertThat(this.environment.resolvePlaceholders("${foo:}")).isEmpty();
		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.environment,
				"spring.application.json={\"foo\":\"bar\"}");
		this.processor.postProcessEnvironment(this.environment, null);
		assertThat(this.environment.resolvePlaceholders("${foo:}")).isEqualTo("bar");
	}

	@Test
	void envVar() {
		assertThat(this.environment.resolvePlaceholders("${foo:}")).isEmpty();
		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.environment,
				"SPRING_APPLICATION_JSON={\"foo\":\"bar\"}");
		this.processor.postProcessEnvironment(this.environment, null);
		assertThat(this.environment.resolvePlaceholders("${foo:}")).isEqualTo("bar");
	}

	@Test
	void nested() {
		assertThat(this.environment.resolvePlaceholders("${foo:}")).isEmpty();
		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.environment,
				"SPRING_APPLICATION_JSON={\"foo\":{\"bar\":\"spam\",\"rab\":\"maps\"}}");
		this.processor.postProcessEnvironment(this.environment, null);
		assertThat(this.environment.resolvePlaceholders("${foo.bar:}")).isEqualTo("spam");
		assertThat(this.environment.resolvePlaceholders("${foo.rab:}")).isEqualTo("maps");
	}

	@Test
	void prefixed() {
		assertThat(this.environment.resolvePlaceholders("${foo:}")).isEmpty();
		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.environment,
				"SPRING_APPLICATION_JSON={\"foo.bar\":\"spam\"}");
		this.processor.postProcessEnvironment(this.environment, null);
		assertThat(this.environment.resolvePlaceholders("${foo.bar:}")).isEqualTo("spam");
	}

	@Test
	void list() {
		assertThat(this.environment.resolvePlaceholders("${foo[1]:}")).isEmpty();
		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.environment,
				"SPRING_APPLICATION_JSON={\"foo\":[\"bar\",\"spam\"]}");
		this.processor.postProcessEnvironment(this.environment, null);
		assertThat(this.environment.resolvePlaceholders("${foo[1]:}")).isEqualTo("spam");
	}

	@Test
	void listOfObject() {
		assertThat(this.environment.resolvePlaceholders("${foo[0].bar:}")).isEmpty();
		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.environment,
				"SPRING_APPLICATION_JSON={\"foo\":[{\"bar\":\"spam\"}]}");
		this.processor.postProcessEnvironment(this.environment, null);
		assertThat(this.environment.resolvePlaceholders("${foo[0].bar:}")).isEqualTo("spam");
	}

	@Test
	void propertySourceShouldTrackOrigin() {
		assertThat(this.environment.resolvePlaceholders("${foo:}")).isEmpty();
		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.environment,
				"spring.application.json={\"foo\":\"bar\"}");
		this.processor.postProcessEnvironment(this.environment, null);
		PropertySource<?> propertySource = this.environment.getPropertySources().get("spring.application.json");
		PropertySourceOrigin origin = (PropertySourceOrigin) PropertySourceOrigin.get(propertySource, "foo");
		assertThat(origin.getPropertySource().getName()).isEqualTo("Inlined Test Properties");
		assertThat(origin.getPropertyName()).isEqualTo("spring.application.json");
		assertThat(this.environment.resolvePlaceholders("${foo:}")).isEqualTo("bar");
	}

	@Test
	void propertySourceShouldBeOrderedBeforeJndiPropertySource() {
		testServletPropertySource(StandardServletEnvironment.JNDI_PROPERTY_SOURCE_NAME);
	}

	@Test
	void propertySourceShouldBeOrderedBeforeServletContextPropertySource() {
		testServletPropertySource(StandardServletEnvironment.SERVLET_CONTEXT_PROPERTY_SOURCE_NAME);
	}

	@Test
	void propertySourceShouldBeOrderedBeforeServletConfigPropertySource() {
		testServletPropertySource(StandardServletEnvironment.SERVLET_CONFIG_PROPERTY_SOURCE_NAME);
	}

	@Test
	void propertySourceOrderingWhenMultipleServletSpecificPropertySources() {
		MapPropertySource jndi = getPropertySource(StandardServletEnvironment.JNDI_PROPERTY_SOURCE_NAME, "jndi");
		this.environment.getPropertySources().addFirst(jndi);
		MapPropertySource servlet = getPropertySource(StandardServletEnvironment.SERVLET_CONTEXT_PROPERTY_SOURCE_NAME,
				"servlet");
		this.environment.getPropertySources().addFirst(servlet);
		MapPropertySource custom = getPropertySource("custom", "custom");
		this.environment.getPropertySources().addFirst(custom);
		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.environment,
				"SPRING_APPLICATION_JSON={\"foo\":\"bar\"}");
		this.processor.postProcessEnvironment(this.environment, null);
		PropertySource<?> json = this.environment.getPropertySources().get("spring.application.json");
		assertThat(this.environment.getProperty("foo")).isEqualTo("custom");
		assertThat(this.environment.getPropertySources()).containsSequence(custom, json, servlet, jndi);
	}

	@Test
	void nullValuesShouldBeAddedToPropertySource() {
		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.environment,
				"SPRING_APPLICATION_JSON={\"foo\":null}");
		this.processor.postProcessEnvironment(this.environment, null);
		assertThat(this.environment.containsProperty("foo")).isTrue();
	}

	@Test
	void emptyValuesForCollectionShouldNotBeIgnored() {
		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.environment,
				"SPRING_APPLICATION_JSON={\"foo\":[]}");
		MockPropertySource source = new MockPropertySource();
		source.setProperty("foo", "bar");
		this.environment.getPropertySources().addLast(source);
		assertThat(this.environment.resolvePlaceholders("${foo}")).isEqualTo("bar");
		this.environment.getPropertySources().addLast(source);
		this.processor.postProcessEnvironment(this.environment, null);
		assertThat(this.environment.resolvePlaceholders("${foo}")).isEmpty();
	}

	@Test
	@SuppressWarnings("unchecked")
	void emptyMapValuesShouldNotBeIgnored() {
		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.environment,
				"SPRING_APPLICATION_JSON={\"foo\":{}}");
		MockPropertySource source = new MockPropertySource();
		source.setProperty("foo.baz", "bar");
		this.environment.getPropertySources().addLast(source);
		this.processor.postProcessEnvironment(this.environment, null);
		assertThat(this.environment.getProperty("foo", Map.class)).isEmpty();
	}

	private void testServletPropertySource(String servletPropertySourceName) {
		this.environment.getPropertySources().addFirst(getPropertySource(servletPropertySourceName, "servlet"));
		TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.environment,
				"SPRING_APPLICATION_JSON={\"foo\":\"bar\"}");
		this.processor.postProcessEnvironment(this.environment, null);
		assertThat(this.environment.getProperty("foo")).isEqualTo("bar");
	}

	private MapPropertySource getPropertySource(String name, String value) {
		return new MapPropertySource(name, Collections.singletonMap("foo", value));
	}

}
