package org.springframework.boot.autoconfigure.data.neo4j;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.TestAutoConfigurationPackage;
import org.springframework.boot.autoconfigure.data.empty.EmptyDataPackage;
import org.springframework.boot.autoconfigure.data.neo4j.city.City;
import org.springframework.boot.autoconfigure.data.neo4j.city.CityRepository;
import org.springframework.boot.autoconfigure.data.neo4j.city.ReactiveCityRepository;
import org.springframework.boot.autoconfigure.data.neo4j.country.CountryRepository;
import org.springframework.boot.autoconfigure.data.neo4j.country.ReactiveCountryRepository;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.ReactiveNeo4jTemplate;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableReactiveNeo4jRepositories;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Neo4jReactiveRepositoriesAutoConfiguration}.
 *


 */
public class Neo4jReactiveRepositoriesAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withUserConfiguration(MockedDriverConfiguration.class)
			.withConfiguration(AutoConfigurations.of(Neo4jDataAutoConfiguration.class,
					Neo4jReactiveDataAutoConfiguration.class, Neo4jRepositoriesAutoConfiguration.class,
					Neo4jReactiveRepositoriesAutoConfiguration.class));

	@Test
	void configurationWithDefaultRepositories() {
		this.contextRunner.withUserConfiguration(TestConfiguration.class)
				.run((context) -> assertThat(context).hasSingleBean(ReactiveCityRepository.class));
	}

	@Test
	void configurationWithNoRepositories() {
		this.contextRunner.withUserConfiguration(EmptyConfiguration.class).run((context) -> assertThat(context)
				.hasSingleBean(ReactiveNeo4jTemplate.class).doesNotHaveBean(ReactiveNeo4jRepository.class));
	}

	@Test
	void configurationWithDisabledRepositories() {
		this.contextRunner.withUserConfiguration(TestConfiguration.class)
				.withPropertyValues("spring.data.neo4j.repositories.type=none")
				.run((context) -> assertThat(context).doesNotHaveBean(ReactiveNeo4jRepository.class));
	}

	@Test
	void autoConfigurationShouldNotKickInEvenIfManualConfigDidNotCreateAnyRepositories() {
		this.contextRunner.withUserConfiguration(SortOfInvalidCustomConfiguration.class)
				.run((context) -> assertThat(context).hasSingleBean(ReactiveNeo4jTemplate.class)
						.doesNotHaveBean(ReactiveNeo4jRepository.class));
	}

	@Test
	void shouldRespectAtEnableReactiveNeo4jRepositories() {
		this.contextRunner
				.withUserConfiguration(SortOfInvalidCustomConfiguration.class, WithCustomReactiveRepositoryScan.class)
				.withPropertyValues("spring.data.neo4j.repositories.type=reactive")
				.run((context) -> assertThat(context).doesNotHaveBean(CityRepository.class)
						.doesNotHaveBean(ReactiveCityRepository.class).doesNotHaveBean(CountryRepository.class)
						.hasSingleBean(ReactiveCountryRepository.class));
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
	@EnableReactiveNeo4jRepositories("foo.bar")
	@TestAutoConfigurationPackage(Neo4jReactiveRepositoriesAutoConfigurationTests.class)
	static class SortOfInvalidCustomConfiguration {

	}

	@Configuration(proxyBeanMethods = false)
	@EnableReactiveNeo4jRepositories(basePackageClasses = ReactiveCountryRepository.class)
	static class WithCustomReactiveRepositoryScan {

	}

}
