package org.springframework.boot.autoconfigure.data.redis;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.boot.autoconfigure.TestAutoConfigurationPackage;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.data.alt.redis.CityRedisRepository;
import org.springframework.boot.autoconfigure.data.empty.EmptyDataPackage;
import org.springframework.boot.autoconfigure.data.redis.city.City;
import org.springframework.boot.autoconfigure.data.redis.city.CityRepository;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.testsupport.testcontainers.RedisContainer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link RedisRepositoriesAutoConfiguration}.
 *

 */
@Testcontainers(disabledWithoutDocker = true)
class RedisRepositoriesAutoConfigurationTests {

	@Container
	public static RedisContainer redis = new RedisContainer().withStartupAttempts(5)
			.withStartupTimeout(Duration.ofMinutes(10));

	private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

	@BeforeEach
	void setUp() {
		TestPropertyValues.of("spring.redis.host=" + redis.getHost(), "spring.redis.port=" + redis.getFirstMappedPort())
				.applyTo(this.context.getEnvironment());
	}

	@AfterEach
	void close() {
		this.context.close();
	}

	@Test
	void testDefaultRepositoryConfiguration() {
		this.context.register(TestConfiguration.class, RedisAutoConfiguration.class,
				RedisRepositoriesAutoConfiguration.class, PropertyPlaceholderAutoConfiguration.class);
		this.context.refresh();
		assertThat(this.context.getBean(CityRepository.class)).isNotNull();
	}

	@Test
	void testNoRepositoryConfiguration() {
		this.context.register(EmptyConfiguration.class, RedisAutoConfiguration.class,
				RedisRepositoriesAutoConfiguration.class, PropertyPlaceholderAutoConfiguration.class);
		this.context.refresh();
		assertThat(this.context.getBean("redisTemplate")).isNotNull();
	}

	@Test
	void doesNotTriggerDefaultRepositoryDetectionIfCustomized() {
		this.context.register(CustomizedConfiguration.class, RedisAutoConfiguration.class,
				RedisRepositoriesAutoConfiguration.class, PropertyPlaceholderAutoConfiguration.class);
		this.context.refresh();
		assertThat(this.context.getBean(CityRedisRepository.class)).isNotNull();
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
	@TestAutoConfigurationPackage(RedisRepositoriesAutoConfigurationTests.class)
	@EnableRedisRepositories(basePackageClasses = CityRedisRepository.class)
	static class CustomizedConfiguration {

	}

}
