package org.springframework.boot.autoconfigure.web;

import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.assertj.AssertableApplicationContext;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.context.runner.ContextConsumer;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Binding tests for {@link ResourceProperties}.
 *

 */
class ResourcePropertiesBindingTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withUserConfiguration(TestConfiguration.class);

	@Test
	void staticLocationsExpandArray() {
		this.contextRunner
				.withPropertyValues("spring.resources.static-locations[0]=classpath:/one/",
						"spring.resources.static-locations[1]=classpath:/two",
						"spring.resources.static-locations[2]=classpath:/three/",
						"spring.resources.static-locations[3]=classpath:/four",
						"spring.resources.static-locations[4]=classpath:/five/",
						"spring.resources.static-locations[5]=classpath:/six")
				.run(assertResourceProperties((properties) -> assertThat(properties.getStaticLocations()).contains(
						"classpath:/one/", "classpath:/two/", "classpath:/three/", "classpath:/four/",
						"classpath:/five/", "classpath:/six/")));
	}

	private ContextConsumer<AssertableApplicationContext> assertResourceProperties(
			Consumer<ResourceProperties> consumer) {
		return (context) -> {
			assertThat(context).hasSingleBean(ResourceProperties.class);
			consumer.accept(context.getBean(ResourceProperties.class));
		};
	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(ResourceProperties.class)
	static class TestConfiguration {

	}

}
