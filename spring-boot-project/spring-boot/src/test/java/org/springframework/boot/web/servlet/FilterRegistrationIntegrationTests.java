package org.springframework.boot.web.servlet;

import javax.servlet.Filter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.mock.MockFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link Filter} registration.
 *

 */
class FilterRegistrationIntegrationTests {

	private AnnotationConfigServletWebServerApplicationContext context;

	@AfterEach
	void cleanUp() {
		if (this.context != null) {
			this.context.close();
		}
	}

	@Test
	void normalFiltersAreRegistered() {
		load(FilterConfiguration.class);
		assertThat(this.context.getServletContext().getFilterRegistrations()).hasSize(1);
	}

	@Test
	void scopedTargetFiltersAreNotRegistered() {
		load(ScopedTargetFilterConfiguration.class);
		assertThat(this.context.getServletContext().getFilterRegistrations()).isEmpty();
	}

	private void load(Class<?> configuration) {
		this.context = new AnnotationConfigServletWebServerApplicationContext(ContainerConfiguration.class,
				configuration);
	}

	@Configuration(proxyBeanMethods = false)
	static class ContainerConfiguration {

		@Bean
		TomcatServletWebServerFactory webServerFactory() {
			return new TomcatServletWebServerFactory(0);
		}

	}

	@Configuration(proxyBeanMethods = false)
	static class ScopedTargetFilterConfiguration {

		@Bean(name = "scopedTarget.myFilter")
		Filter myFilter() {
			return new MockFilter();
		}

	}

	@Configuration(proxyBeanMethods = false)
	static class FilterConfiguration {

		@Bean
		Filter myFilter() {
			return new MockFilter();
		}

	}

}
