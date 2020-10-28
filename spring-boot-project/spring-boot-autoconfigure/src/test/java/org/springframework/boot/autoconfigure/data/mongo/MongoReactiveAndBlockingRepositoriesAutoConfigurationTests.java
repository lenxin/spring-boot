package org.springframework.boot.autoconfigure.data.mongo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.TestAutoConfigurationPackage;
import org.springframework.boot.autoconfigure.data.mongo.city.CityRepository;
import org.springframework.boot.autoconfigure.data.mongo.city.ReactiveCityRepository;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.util.StringUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MongoRepositoriesAutoConfiguration} and
 * {@link MongoReactiveRepositoriesAutoConfiguration}.
 *

 */
class MongoReactiveAndBlockingRepositoriesAutoConfigurationTests {

	private AnnotationConfigApplicationContext context;

	@AfterEach
	void close() {
		this.context.close();
	}

	@Test
	void shouldCreateInstancesForReactiveAndBlockingRepositories() {
		this.context = new AnnotationConfigApplicationContext();
		TestPropertyValues.of("spring.datasource.initialization-mode:never").applyTo(this.context);
		this.context.register(BlockingAndReactiveConfiguration.class, BaseConfiguration.class);
		this.context.refresh();
		assertThat(this.context.getBean(CityRepository.class)).isNotNull();
		assertThat(this.context.getBean(ReactiveCityRepository.class)).isNotNull();
	}

	@Configuration(proxyBeanMethods = false)
	@TestAutoConfigurationPackage(MongoAutoConfiguration.class)
	@EnableMongoRepositories(basePackageClasses = ReactiveCityRepository.class)
	@EnableReactiveMongoRepositories(basePackageClasses = ReactiveCityRepository.class)
	static class BlockingAndReactiveConfiguration {

	}

	@Configuration(proxyBeanMethods = false)
	@Import(Registrar.class)
	static class BaseConfiguration {

	}

	static class Registrar implements ImportSelector {

		@Override
		public String[] selectImports(AnnotationMetadata importingClassMetadata) {
			List<String> names = new ArrayList<>();
			for (Class<?> type : new Class<?>[] { MongoAutoConfiguration.class, MongoReactiveAutoConfiguration.class,
					MongoDataAutoConfiguration.class, MongoRepositoriesAutoConfiguration.class,
					MongoReactiveDataAutoConfiguration.class, MongoReactiveRepositoriesAutoConfiguration.class }) {
				names.add(type.getName());
			}
			return StringUtils.toStringArray(names);
		}

	}

}
