package org.springframework.boot.autoconfigure.data.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.autoconfigure.TestAutoConfigurationPackage;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.data.alt.solr.CitySolrRepository;
import org.springframework.boot.autoconfigure.data.empty.EmptyDataPackage;
import org.springframework.boot.autoconfigure.data.solr.city.City;
import org.springframework.boot.autoconfigure.data.solr.city.CityRepository;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Tests for {@link SolrRepositoriesAutoConfiguration}.
 *


 */
class SolrRepositoriesAutoConfigurationTests {

	private AnnotationConfigApplicationContext context;

	@AfterEach
	void close() {
		this.context.close();
	}

	@Test
	void testDefaultRepositoryConfiguration() {
		initContext(TestConfiguration.class);
		assertThat(this.context.getBean(CityRepository.class)).isNotNull();
		assertThat(this.context.getBean(SolrClient.class)).isInstanceOf(HttpSolrClient.class);
	}

	@Test
	void testNoRepositoryConfiguration() {
		initContext(EmptyConfiguration.class);
		assertThat(this.context.getBean(SolrClient.class)).isInstanceOf(HttpSolrClient.class);
	}

	@Test
	void doesNotTriggerDefaultRepositoryDetectionIfCustomized() {
		initContext(CustomizedConfiguration.class);
		assertThat(this.context.getBean(CitySolrRepository.class)).isNotNull();
	}

	@Test
	void autoConfigurationShouldNotKickInEvenIfManualConfigDidNotCreateAnyRepositories() {
		initContext(SortOfInvalidCustomConfiguration.class);
		assertThatExceptionOfType(NoSuchBeanDefinitionException.class)
				.isThrownBy(() -> this.context.getBean(CityRepository.class));
	}

	private void initContext(Class<?> configClass) {

		this.context = new AnnotationConfigApplicationContext();
		this.context.register(configClass, SolrAutoConfiguration.class, SolrRepositoriesAutoConfiguration.class,
				PropertyPlaceholderAutoConfiguration.class);
		this.context.refresh();
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
	@TestAutoConfigurationPackage(SolrRepositoriesAutoConfigurationTests.class)
	@EnableSolrRepositories(basePackageClasses = CitySolrRepository.class)
	static class CustomizedConfiguration {

	}

	@Configuration(proxyBeanMethods = false)
	@TestAutoConfigurationPackage(SolrRepositoriesAutoConfigurationTests.class)
	// To not find any repositories
	@EnableSolrRepositories("foo.bar")
	static class SortOfInvalidCustomConfiguration {

	}

}
