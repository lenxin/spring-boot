package org.springframework.boot.autoconfigure.data.elasticsearch;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.TestAutoConfigurationPackage;
import org.springframework.boot.autoconfigure.data.alt.elasticsearch.CityElasticsearchDbRepository;
import org.springframework.boot.autoconfigure.data.elasticsearch.city.City;
import org.springframework.boot.autoconfigure.data.elasticsearch.city.CityRepository;
import org.springframework.boot.autoconfigure.data.empty.EmptyDataPackage;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ElasticsearchRepositoriesAutoConfiguration}.
 *



 */
@Testcontainers(disabledWithoutDocker = true)
class ElasticsearchRepositoriesAutoConfigurationTests {

	@Container
	static final ElasticsearchContainer elasticsearch = new VersionOverridingElasticsearchContainer()
			.withStartupAttempts(5).withStartupTimeout(Duration.ofMinutes(10));

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(ElasticsearchRestClientAutoConfiguration.class,
					ElasticsearchRepositoriesAutoConfiguration.class, ElasticsearchDataAutoConfiguration.class))
			.withPropertyValues("spring.elasticsearch.rest.uris=" + elasticsearch.getHttpHostAddress());

	@Test
	void testDefaultRepositoryConfiguration() {
		this.contextRunner.withUserConfiguration(TestConfiguration.class).run((context) -> assertThat(context)
				.hasSingleBean(CityRepository.class).hasSingleBean(ElasticsearchRestTemplate.class));
	}

	@Test
	void testNoRepositoryConfiguration() {
		this.contextRunner.withUserConfiguration(EmptyConfiguration.class)
				.run((context) -> assertThat(context).hasSingleBean(ElasticsearchRestTemplate.class));
	}

	@Test
	void doesNotTriggerDefaultRepositoryDetectionIfCustomized() {
		this.contextRunner.withUserConfiguration(CustomizedConfiguration.class)
				.run((context) -> assertThat(context).hasSingleBean(CityElasticsearchDbRepository.class));
	}

	@Configuration(proxyBeanMethods = false)
	@TestAutoConfigurationPackage(City.class)
	static class TestConfiguration {

	}

	@Configuration(proxyBeanMethods = false)
	@TestAutoConfigurationPackage(EmptyDataPackage.class)
	static class EmptyConfiguration {

	}

	@Configuration(proxyBeanMethods = false)
	@TestAutoConfigurationPackage(ElasticsearchRepositoriesAutoConfigurationTests.class)
	@EnableElasticsearchRepositories(basePackageClasses = CityElasticsearchDbRepository.class)
	static class CustomizedConfiguration {

	}

}
