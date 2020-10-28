package org.springframework.boot.actuate.redis;

import java.util.Properties;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.data.redis.connection.ClusterInfo;

/**
 * Shared class used by {@link RedisHealthIndicator} and
 * {@link RedisReactiveHealthIndicator} to provide health details.
 *

 */
final class RedisHealth {

	private RedisHealth() {
	}

	static Builder up(Health.Builder builder, Properties info) {
		builder.withDetail("version", info.getProperty("redis_version"));
		return builder.up();
	}

	static Builder up(Health.Builder builder, ClusterInfo clusterInfo) {
		builder.withDetail("cluster_size", clusterInfo.getClusterSize());
		builder.withDetail("slots_up", clusterInfo.getSlotsOk());
		builder.withDetail("slots_fail", clusterInfo.getSlotsFail());
		return builder.up();
	}

}
