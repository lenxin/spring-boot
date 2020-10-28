package org.springframework.boot.test.autoconfigure.data.redis;

import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.testsupport.testcontainers.RedisContainer;
import org.springframework.core.env.Environment;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for the {@link DataRedisTest#properties properties} attribute of
 * {@link DataRedisTest @DataRedisTest}.
 *

 */
@Testcontainers(disabledWithoutDocker = true)
@DataRedisTest(properties = "spring.profiles.active=test")
class DataRedisTestPropertiesIntegrationTests {

	@Container
	static final RedisContainer redis = new RedisContainer();

	@Autowired
	private Environment environment;

	@DynamicPropertySource
	static void redisProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.redis.host", redis::getHost);
		registry.add("spring.redis.port", redis::getFirstMappedPort);
	}

	@Test
	void environmentWithNewProfile() {
		assertThat(this.environment.getActiveProfiles()).containsExactly("test");
	}

}
