package org.springframework.boot.test.autoconfigure.data.redis;

import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.testsupport.testcontainers.RedisContainer;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.stereotype.Service;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test with custom include filter for {@link DataRedisTest @DataRedisTest}.
 *

 */
@Testcontainers(disabledWithoutDocker = true)
@DataRedisTest(includeFilters = @Filter(Service.class))
class DataRedisTestWithIncludeFilterIntegrationTests {

	@Container
	static final RedisContainer redis = new RedisContainer();

	@Autowired
	private ExampleRepository exampleRepository;

	@Autowired
	private ExampleService service;

	@DynamicPropertySource
	static void redisProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.redis.host", redis::getHost);
		registry.add("spring.redis.port", redis::getFirstMappedPort);
	}

	@Test
	void testService() {
		PersonHash personHash = new PersonHash();
		personHash.setDescription("Look, new @DataRedisTest!");
		assertThat(personHash.getId()).isNull();
		PersonHash savedEntity = this.exampleRepository.save(personHash);
		assertThat(this.service.hasRecord(savedEntity)).isTrue();
	}

}
