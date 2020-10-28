package org.springframework.boot.actuate.influx;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Pong;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.util.Assert;

/**
 * {@link HealthIndicator} for InfluxDB.
 *

 * @since 2.0.0
 */
public class InfluxDbHealthIndicator extends AbstractHealthIndicator {

	private final InfluxDB influxDb;

	public InfluxDbHealthIndicator(InfluxDB influxDb) {
		super("InfluxDB health check failed");
		Assert.notNull(influxDb, "InfluxDB must not be null");
		this.influxDb = influxDb;
	}

	@Override
	protected void doHealthCheck(Health.Builder builder) {
		Pong pong = this.influxDb.ping();
		builder.up().withDetail("version", pong.getVersion());
	}

}
