package org.springframework.boot.autoconfigure.data.couchbase;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.TestAutoConfigurationPackage;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration;
import org.springframework.boot.autoconfigure.data.alt.couchbase.CityCouchbaseRepository;
import org.springframework.boot.autoconfigure.data.alt.couchbase.ReactiveCityCouchbaseRepository;
import org.springframework.boot.autoconfigure.data.couchbase.city.City;
import org.springframework.boot.autoconfigure.data.couchbase.city.ReactiveCityRepository;
import org.springframework.boot.autoconfigure.data.empty.EmptyDataPackage;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CouchbaseReactiveRepositoriesAutoConfiguration}.
 *


 */
class CouchbaseReactiveRepositoriesAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner().withConfiguration(
			AutoConfigurations.of(CouchbaseAutoConfiguration.class, CouchbaseDataAutoConfiguration.class,
					CouchbaseRepositoriesAutoConfiguration.class, CouchbaseReactiveDataAutoConfiguration.class,
					CouchbaseReactiveRepositoriesAutoConfiguration.class));

	@Test
	void couchbaseNotAvailable() {
		this.contextRunner.run((context) -> assertThat(context).doesNotHaveBean(ReactiveCityRepository.class));
	}

	@Test
	void defaultRepository() {
		this.contextRunner.withUserConfiguration(DefaultConfiguration.class)
				.run((context) -> assertThat(context).hasSingleBean(ReactiveCityRepository.class));
	}

	@Test
	void imperativeRepositories() {
		this.contextRunner.withUserConfiguration(DefaultConfiguration.class)
				.withPropertyValues("spring.data.couchbase.repositories.type=imperative")
				.run((context) -> assertThat(context).doesNotHaveBean(ReactiveCityRepository.class));
	}

	@Test
	void disabledRepositories() {
		this.contextRunner.withUserConfiguration(DefaultConfiguration.class)
				.withPropertyValues("spring.data.couchbase.repositories.type=none")
				.run((context) -> assertThat(context).doesNotHaveBean(ReactiveCityRepository.class));
	}

	@Test
	void noRepositoryAvailable() {
		this.contextRunner.withUserConfiguration(NoRepositoryConfiguration.class)
				.run((context) -> assertThat(context).doesNotHaveBean(ReactiveCityRepository.class));
	}

	@Test
	void doesNotTriggerDefaultRepositoryDetectionIfCustomized() {
		this.contextRunner.withUserConfiguration(CustomizedConfiguration.class)
				.run((context) -> assertThat(context).doesNotHaveBean(ReactiveCityCouchbaseRepository.class));
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

	@Configuration(proxyBeanMethods = false)
	@TestAutoConfigurationPackage(CouchbaseReactiveRepositoriesAutoConfigurationTests.class)
	@EnableCouchbaseRepositories(basePackageClasses = CityCouchbaseRepository.class)
	@Import(CouchbaseMockConfiguration.class)
	static class CustomizedConfiguration {

	}

}
