package org.springframework.boot.autoconfigure.data.elasticsearch;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link ElasticsearchDataAutoConfiguration}.
 *





 */
class ElasticsearchDataAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(ElasticsearchRestClientAutoConfiguration.class,
					ReactiveElasticsearchRestClientAutoConfiguration.class, ElasticsearchDataAutoConfiguration.class));

	@BeforeEach
	void setUp() {
		System.setProperty("es.set.netty.runtime.available.processors", "false");
	}

	@AfterEach
	void tearDown() {
		System.clearProperty("es.set.netty.runtime.available.processors");
	}

	@Test
	void defaultRestBeansRegistered() {
		this.contextRunner.run((context) -> assertThat(context).hasSingleBean(ElasticsearchRestTemplate.class)
				.hasSingleBean(ReactiveElasticsearchTemplate.class).hasSingleBean(ElasticsearchConverter.class)
				.hasSingleBean(ElasticsearchConverter.class));
	}

	@Test
	void customRestTemplateShouldBeUsed() {
		this.contextRunner.withUserConfiguration(CustomRestTemplate.class).run((context) -> assertThat(context)
				.getBeanNames(ElasticsearchRestTemplate.class).hasSize(1).contains("elasticsearchTemplate"));
	}

	@Test
	void customReactiveRestTemplateShouldBeUsed() {
		this.contextRunner.withUserConfiguration(CustomReactiveRestTemplate.class)
				.run((context) -> assertThat(context).getBeanNames(ReactiveElasticsearchTemplate.class).hasSize(1)
						.contains("reactiveElasticsearchTemplate"));
	}

	@Configuration(proxyBeanMethods = false)
	static class CustomRestTemplate {

		@Bean
		ElasticsearchRestTemplate elasticsearchTemplate() {
			return mock(ElasticsearchRestTemplate.class);
		}

	}

	@Configuration(proxyBeanMethods = false)
	static class CustomReactiveRestTemplate {

		@Bean
		ReactiveElasticsearchTemplate reactiveElasticsearchTemplate() {
			return mock(ReactiveElasticsearchTemplate.class);
		}

	}

}
