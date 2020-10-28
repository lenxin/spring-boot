package org.springframework.boot.actuate.autoconfigure.metrics.web.reactive;

import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.actuate.autoconfigure.metrics.test.MetricsRun;
import org.springframework.boot.actuate.autoconfigure.metrics.web.TestController;
import org.springframework.boot.actuate.metrics.web.reactive.server.DefaultWebFluxTagsProvider;
import org.springframework.boot.actuate.metrics.web.reactive.server.MetricsWebFilter;
import org.springframework.boot.actuate.metrics.web.reactive.server.WebFluxTagsContributor;
import org.springframework.boot.actuate.metrics.web.reactive.server.WebFluxTagsProvider;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.test.context.assertj.AssertableReactiveWebApplicationContext;
import org.springframework.boot.test.context.runner.ReactiveWebApplicationContextRunner;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link WebFluxMetricsAutoConfiguration}
 *



 */
@ExtendWith(OutputCaptureExtension.class)
class WebFluxMetricsAutoConfigurationTests {

	private final ReactiveWebApplicationContextRunner contextRunner = new ReactiveWebApplicationContextRunner()
			.with(MetricsRun.simple()).withConfiguration(AutoConfigurations.of(WebFluxMetricsAutoConfiguration.class));

	@Test
	void shouldProvideWebFluxMetricsBeans() {
		this.contextRunner.run((context) -> {
			assertThat(context).getBeans(MetricsWebFilter.class).hasSize(1);
			assertThat(context).getBeans(DefaultWebFluxTagsProvider.class).hasSize(1);
			assertThat(context.getBean(DefaultWebFluxTagsProvider.class)).extracting("ignoreTrailingSlash")
					.isEqualTo(true);
		});
	}

	@Test
	void tagsProviderWhenIgnoreTrailingSlashIsFalse() {
		this.contextRunner.withPropertyValues("management.metrics.web.server.request.ignore-trailing-slash=false")
				.run((context) -> {
					assertThat(context).hasSingleBean(DefaultWebFluxTagsProvider.class);
					assertThat(context.getBean(DefaultWebFluxTagsProvider.class)).extracting("ignoreTrailingSlash")
							.isEqualTo(false);
				});
	}

	@Test
	void shouldNotOverrideCustomTagsProvider() {
		this.contextRunner.withUserConfiguration(CustomWebFluxTagsProviderConfig.class)
				.run((context) -> assertThat(context).getBeans(WebFluxTagsProvider.class).hasSize(1)
						.containsKey("customWebFluxTagsProvider"));
	}

	@Test
	void afterMaxUrisReachedFurtherUrisAreDenied(CapturedOutput output) {
		this.contextRunner.withConfiguration(AutoConfigurations.of(WebFluxAutoConfiguration.class))
				.withUserConfiguration(TestController.class)
				.withPropertyValues("management.metrics.web.server.max-uri-tags=2").run((context) -> {
					MeterRegistry registry = getInitializedMeterRegistry(context);
					assertThat(registry.get("http.server.requests").meters()).hasSize(2);
					assertThat(output).contains("Reached the maximum number of URI tags for 'http.server.requests'");
				});
	}

	@Test
	void shouldNotDenyNorLogIfMaxUrisIsNotReached(CapturedOutput output) {
		this.contextRunner.withConfiguration(AutoConfigurations.of(WebFluxAutoConfiguration.class))
				.withUserConfiguration(TestController.class)
				.withPropertyValues("management.metrics.web.server.max-uri-tags=5").run((context) -> {
					MeterRegistry registry = getInitializedMeterRegistry(context);
					assertThat(registry.get("http.server.requests").meters()).hasSize(3);
					assertThat(output)
							.doesNotContain("Reached the maximum number of URI tags for 'http.server.requests'");
				});
	}

	@Test
	void metricsAreNotRecordedIfAutoTimeRequestsIsDisabled() {
		this.contextRunner.withConfiguration(AutoConfigurations.of(WebFluxAutoConfiguration.class))
				.withUserConfiguration(TestController.class)
				.withPropertyValues("management.metrics.web.server.request.autotime.enabled=false").run((context) -> {
					MeterRegistry registry = getInitializedMeterRegistry(context);
					assertThat(registry.find("http.server.requests").meter()).isNull();
				});
	}

	@Test
	void whenTagContributorsAreDefinedThenTagsProviderUsesThem() {
		this.contextRunner.withUserConfiguration(TagsContributorsConfiguration.class).run((context) -> {
			assertThat(context).hasSingleBean(DefaultWebFluxTagsProvider.class);
			assertThat(context.getBean(DefaultWebFluxTagsProvider.class)).extracting("contributors").asList()
					.hasSize(2);
		});
	}

	private MeterRegistry getInitializedMeterRegistry(AssertableReactiveWebApplicationContext context) {
		WebTestClient webTestClient = WebTestClient.bindToApplicationContext(context).build();
		for (int i = 0; i < 3; i++) {
			webTestClient.get().uri("/test" + i).exchange().expectStatus().isOk();
		}
		return context.getBean(MeterRegistry.class);
	}

	@Configuration(proxyBeanMethods = false)
	static class CustomWebFluxTagsProviderConfig {

		@Bean
		WebFluxTagsProvider customWebFluxTagsProvider() {
			return mock(WebFluxTagsProvider.class);
		}

	}

	@Configuration(proxyBeanMethods = false)
	static class TagsContributorsConfiguration {

		@Bean
		WebFluxTagsContributor tagContributorOne() {
			return mock(WebFluxTagsContributor.class);
		}

		@Bean
		WebFluxTagsContributor tagContributorTwo() {
			return mock(WebFluxTagsContributor.class);
		}

	}

}
