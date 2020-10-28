package org.springframework.boot.autoconfigure.web.reactive;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ReactiveWebApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ContextPathCompositeHandler;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.web.reactive.DispatcherHandler;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.WebHandler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Tests for {@link HttpHandlerAutoConfiguration}.
 *



 */
class HttpHandlerAutoConfigurationTests {

	private final ReactiveWebApplicationContextRunner contextRunner = new ReactiveWebApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(HttpHandlerAutoConfiguration.class));

	@Test
	void shouldNotProcessIfExistingHttpHandler() {
		this.contextRunner.withUserConfiguration(CustomHttpHandler.class).run((context) -> {
			assertThat(context).hasSingleBean(HttpHandler.class);
			assertThat(context).getBean(HttpHandler.class).isSameAs(context.getBean("customHttpHandler"));
		});
	}

	@Test
	void shouldConfigureHttpHandlerAnnotation() {
		this.contextRunner.withConfiguration(AutoConfigurations.of(WebFluxAutoConfiguration.class))
				.run((context) -> assertThat(context).hasSingleBean(HttpHandler.class));
	}

	@Test
	void shouldConfigureHttpHandlerWithoutWebFluxAutoConfiguration() {
		this.contextRunner.withUserConfiguration(CustomWebHandler.class)
				.run((context) -> assertThat(context).hasSingleBean(HttpHandler.class));
	}

	@Test
	void shouldConfigureBasePathCompositeHandler() {
		this.contextRunner.withConfiguration(AutoConfigurations.of(WebFluxAutoConfiguration.class))
				.withPropertyValues("spring.webflux.base-path=/something").run((context) -> {
					assertThat(context).hasSingleBean(HttpHandler.class);
					HttpHandler httpHandler = context.getBean(HttpHandler.class);
					assertThat(httpHandler).isInstanceOf(ContextPathCompositeHandler.class)
							.extracting("handlerMap", InstanceOfAssertFactories.map(String.class, HttpHandler.class))
							.containsKey("/something");
				});
	}

	@Configuration(proxyBeanMethods = false)
	static class CustomHttpHandler {

		@Bean
		HttpHandler customHttpHandler() {
			return (serverHttpRequest, serverHttpResponse) -> null;
		}

		@Bean
		RouterFunction<ServerResponse> routerFunction() {
			return route(GET("/test"), (serverRequest) -> null);
		}

	}

	@Configuration(proxyBeanMethods = false)
	static class CustomWebHandler {

		@Bean
		WebHandler webHandler() {
			return new DispatcherHandler();
		}

	}

}
