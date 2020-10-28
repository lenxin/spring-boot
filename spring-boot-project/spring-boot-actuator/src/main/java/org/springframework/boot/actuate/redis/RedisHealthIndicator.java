package org.springframework.boot.actuate.redis;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.util.Assert;

/**
 * Simple implementation of a {@link HealthIndicator} returning status information for
 * Redis data stores.
 *



 * @since 2.0.0
 */
public class RedisHealthIndicator extends AbstractHealthIndicator {

	private final RedisConnectionFactory redisConnectionFactory;

	public RedisHealthIndicator(RedisConnectionFactory connectionFactory) {
		super("Redis health check failed");
		Assert.notNull(connectionFactory, "ConnectionFactory must not be null");
		this.redisConnectionFactory = connectionFactory;
	}

	@Override
	protected void doHealthCheck(Health.Builder builder) throws Exception {
		RedisConnection connection = RedisConnectionUtils.getConnection(this.redisConnectionFactory);
		try {
			doHealthCheck(builder, connection);
		}
		finally {
			RedisConnectionUtils.releaseConnection(connection, this.redisConnectionFactory, false);
		}
	}

	private void doHealthCheck(Health.Builder builder, RedisConnection connection) {
		if (connection instanceof RedisClusterConnection) {
			RedisHealth.up(builder, ((RedisClusterConnection) connection).clusterGetClusterInfo());
		}
		else {
			RedisHealth.up(builder, connection.info());
		}
	}

}
