package org.springframework.boot.testsupport.testcontainers;

import org.testcontainers.containers.GenericContainer;

/**
 * A {@link GenericContainer} for Redis.
 *


 * @since 2.0.0
 */
public class RedisContainer extends GenericContainer<RedisContainer> {

	public RedisContainer() {
		super("redis:4.0.6");
		addExposedPorts(6379);
	}

}
