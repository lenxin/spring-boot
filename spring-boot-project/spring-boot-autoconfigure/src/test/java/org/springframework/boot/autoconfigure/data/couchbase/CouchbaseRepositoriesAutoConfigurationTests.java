package org.springframework.boot.autoconfigure.data.couchbase;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.TestAutoConfigurationPackage;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration;
import org.springframework.boot.autoconfigure.data.couchbase.city.City;
import org.springframework.boot.autoconfigure.data.couchbase.city.CityRepository;
import org.springframework.boot.autoconfigure.data.empty.EmptyDataPackage;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CouchbaseRepositoriesAutoConfiguration}.
 *


 */
class CouchbaseRepositoriesAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(CouchbaseAutoConfiguration.class,
					CouchbaseDataAutoConfiguration.class, CouchbaseRepositoriesAutoConfiguration.class));

	@Test
	void couchbaseNotAvailable() {
		this.contextRunner.run((context) -> assertThat(context).doesNotHaveBean(CityRepository.class));
	}

	@Test
	void defaultRepository() {
		this.contextRunner.withUserConfiguration(DefaultConfiguration.class)
				.run((context) -> assertThat(context).hasSingleBean(CityRepository.class));
	}

	@Test
	void reactiveRepositories() {
		this.contextRunner.withUserConfiguration(DefaultConfiguration.class)
				.withPropertyValues("spring.data.couchbase.repositories.type=reactive")
				.run((context) -> assertThat(context).doesNotHaveBean(CityRepository.class));
	}

	@Test
	void disabledRepositories() {
		this.contextRunner.withUserConfiguration(DefaultConfiguration.class)
				.withPropertyValues("spring.data.couchbase.repositories.type=none")
				.run((context) -> assertThat(context).doesNotHaveBean(CityRepository.class));
	}

	@Test
	void noRepositoryAvailable() {
		this.contextRunner.withUserConfiguration(NoRepositoryConfiguration.class)
				.run((context) -> assertThat(context).doesNotHaveBean(CityRepository.class));
	}

	@Configuration(proxyBeanMethods = false)
	@TestAutoConfigurationPackage(City.class)
	static class CouchbaseNotAvailableConfiguration {

	}

	@Configuration(proxyBeanMethods = false)
	@TestAutoConfigurationPackage(City.class)
	@Import(CouchbaseMockConfiguration.class)
	static class DefaultConfiguration {

	}

	@Configuration(proxyBeanMethods = false)
	@TestAutoConfigurationPackage(EmptyDataPackage.class)
	@Import(CouchbaseMockConfiguration.class)
	static class NoRepositoryConfiguration {

	}

}
