package org.springframework.boot.actuate.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.util.Assert;

/**
 * Simple implementation of a {@link HealthIndicator} returning status information for the
 * RabbitMQ messaging system.
 *

 * @since 1.1.0
 */
public class RabbitHealthIndicator extends AbstractHealthIndicator {

	private final RabbitTemplate rabbitTemplate;

	public RabbitHealthIndicator(RabbitTemplate rabbitTemplate) {
		super("Rabbit health check failed");
		Assert.notNull(rabbitTemplate, "RabbitTemplate must not be null");
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	protected void doHealthCheck(Health.Builder builder) throws Exception {
		builder.up().withDetail("version", getVersion());
	}

	private String getVersion() {
		return this.rabbitTemplate
				.execute((channel) -> channel.getConnection().getServerProperties().get("version").toString());
	}

}
