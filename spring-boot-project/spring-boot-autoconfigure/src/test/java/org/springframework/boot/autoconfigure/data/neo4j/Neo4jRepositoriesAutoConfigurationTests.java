package org.springframework.boot.autoconfigure.data.neo4j;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.TestAutoConfigurationPackage;
import org.springframework.boot.autoconfigure.data.empty.EmptyDataPackage;
import org.springframework.boot.autoconfigure.data.neo4j.city.City;
import org.springframework.boot.autoconfigure.data.neo4j.city.CityRepository;
import org.springframework.boot.autoconfigure.data.neo4j.city.ReactiveCityRepository;
import org.springframework.boot.autoconfigure.data.neo4j.country.CountryRepository;
import org.springframework.boot.autoconfigure.data.neo4j.country.ReactiveCountryRepository;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.repository.support.ReactiveNeo4jRepositoryFactoryBean;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Neo4jRepositoriesAutoConfiguration}.
 *






 */
class Neo4jRepositoriesAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withUserConfiguration(MockedDriverConfiguration.class).withConfiguration(
					AutoConfigurations.of(Neo4jDataAutoConfiguration.class, Neo4jRepositoriesAutoConfiguration.class));

	@Test
	void configurationWithDefaultRepositories() {
		this.contextRunner.withUserConfiguration(TestConfiguration.class)
				.run((context) -> assertThat(context).hasSingleBean(CityRepository.class));
	}

	@Test
	void configurationWithNoRepositories() {
		this.contextRunner.withUserConfiguration(EmptyConfiguration.class).run((context) -> assertThat(context)
				.hasSingleBean(Neo4jTransactionManager.class).doesNotHaveBean(Neo4jRepository.class));
	}

	@Test
	void configurationWithDisabledRepositories() {
		this.contextRunner.withUserConfiguration(TestConfiguration.class)
				.withPropertyValues("spring.data.neo4j.repositories.type=none")
				.run((context) -> assertThat(context).doesNotHaveBean(Neo4jRepository.class));
	}

	@Test
	void autoConfigurationShouldNotKickInEvenIfManualConfigDidNotCreateAnyRepositories() {
		this.contextRunner.withUserConfiguration(SortOfInvalidCustomConfiguration.class)
				.run((context) -> assertThat(context).hasSingleBean(Neo4jTransactionManager.class)
						.doesNotHaveBean(Neo4jRepository.class));
	}

	@Test
	void shouldRespectAtEnableNeo4jRepositories() {
		this.contextRunner.withUserConfiguration(SortOfInvalidCustomConfiguration.class, WithCustomRepositoryScan.class)
				.run((context) -> assertThat(context).doesNotHaveBean(CityRepository.class)
						.doesNotHaveBean(ReactiveCityRepository.class).hasSingleBean(CountryRepository.class)
						.doesNotHaveBean(ReactiveCountryRepository.class));
	}

	@Configuration(proxyBeanMethods = false)
	@EnableNeo4jRepositories(basePackageClasses = CountryRepository.class)
	static class WithCustomRepositoryScan {

	}

	@Configuration(proxyBeanMethods = false)
	static class WithFakeEnabledReactiveNeo4jRepositories {

		@Bean
		ReactiveNeo4jRepositoryFactoryBean<?, ?, ?> reactiveNeo4jRepositoryFactoryBean() {
			return Mockito.mock(ReactiveNeo4jRepositoryFactoryBean.class);
		}

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
	@EnableNeo4jRepositories("foo.bar")
	@TestAutoConfigurationPackage(Neo4jRepositoriesAutoConfigurationTests.class)
	static class SortOfInvalidCustomConfiguration {

	}

}
