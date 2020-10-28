package org.springframework.boot.actuate.autoconfigure.web.reactive;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebServerApplicationContext;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.HttpHandler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link ReactiveManagementContextFactory}.
 *

 */
class ReactiveManagementContextFactoryTests {

	private ReactiveManagementContextFactory factory = new ReactiveManagementContextFactory();

	private AnnotationConfigReactiveWebServerApplicationContext parent = new AnnotationConfigReactiveWebServerApplicationContext();

	@Test
	void createManagementContextShouldCreateChildContextWithConfigClasses() {
		this.parent.register(ParentConfiguration.class);
		this.parent.refresh();
		AnnotationConfigReactiveWebServerApplicationContext childContext = (AnnotationConfigReactiveWebServerApplicationContext) this.factory
				.createManagementContext(this.parent, TestConfiguration1.class, TestConfiguration2.class);
		childContext.refresh();
		assertThat(childContext.getBean(TestConfiguration1.class)).isNotNull();
		assertThat(childContext.getBean(TestConfiguration2.class)).isNotNull();
		assertThat(childContext.getBean(ReactiveWebServerFactoryAutoConfiguration.class)).isNotNull();

		childContext.close();
		this.parent.close();
	}

	@Configuration(proxyBeanMethods = false)
	static class ParentConfiguration {

		@Bean
		ReactiveWebServerFactory reactiveWebServerFactory() {
			return new MockReactiveWebServerFactory();
		}

		@Bean
		HttpHandler httpHandler(ApplicationContext applicationContext) {
			return mock(HttpHandler.class);
		}

	}

	@Configuration(proxyBeanMethods = false)
	static class TestConfiguration1 {

		@Bean
		HttpHandler httpHandler(ApplicationContext applicationContext) {
			return mock(HttpHandler.class);
		}

	}

	@Configuration(proxyBeanMethods = false)
	static class TestConfiguration2 {

	}

}
