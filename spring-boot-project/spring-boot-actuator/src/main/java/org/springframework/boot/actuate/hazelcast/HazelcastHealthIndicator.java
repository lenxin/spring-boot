package org.springframework.boot.actuate.hazelcast;

import java.lang.reflect.Method;

import com.hazelcast.core.HazelcastInstance;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

/**
 * {@link HealthIndicator} for Hazelcast.
 *


 * @since 2.2.0
 */
public class HazelcastHealthIndicator extends AbstractHealthIndicator {

	private final HazelcastInstance hazelcast;

	public HazelcastHealthIndicator(HazelcastInstance hazelcast) {
		super("Hazelcast health check failed");
		Assert.notNull(hazelcast, "HazelcastInstance must not be null");
		this.hazelcast = hazelcast;
	}

	@Override
	protected void doHealthCheck(Health.Builder builder) {
		this.hazelcast.executeTransaction((context) -> {
			builder.up().withDetail("name", this.hazelcast.getName()).withDetail("uuid", extractUuid());
			return null;
		});
	}

	private String extractUuid() {
		try {
			return this.hazelcast.getLocalEndpoint().getUuid().toString();
		}
		catch (NoSuchMethodError ex) {
			// Hazelcast 3
			Method endpointAccessor = ReflectionUtils.findMethod(HazelcastInstance.class, "getLocalEndpoint");
			Object endpoint = ReflectionUtils.invokeMethod(endpointAccessor, this.hazelcast);
			Method uuidAccessor = ReflectionUtils.findMethod(endpoint.getClass(), "getUuid");
			return (String) ReflectionUtils.invokeMethod(uuidAccessor, endpoint);
		}
	}

}
