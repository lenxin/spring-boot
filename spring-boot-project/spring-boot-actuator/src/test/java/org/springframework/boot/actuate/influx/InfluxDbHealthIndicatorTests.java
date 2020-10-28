package org.springframework.boot.actuate.influx;

import java.io.IOException;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBException;
import org.influxdb.dto.Pong;
import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link InfluxDbHealthIndicator}.
 *

 */
class InfluxDbHealthIndicatorTests {

	@Test
	void influxDbIsUp() {
		Pong pong = mock(Pong.class);
		given(pong.getVersion()).willReturn("0.9");
		InfluxDB influxDB = mock(InfluxDB.class);
		given(influxDB.ping()).willReturn(pong);
		InfluxDbHealthIndicator healthIndicator = new InfluxDbHealthIndicator(influxDB);
		Health health = healthIndicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.UP);
		assertThat(health.getDetails().get("version")).isEqualTo("0.9");
		verify(influxDB).ping();
	}

	@Test
	void influxDbIsDown() {
		InfluxDB influxDB = mock(InfluxDB.class);
		given(influxDB.ping()).willThrow(new InfluxDBException(new IOException("Connection failed")));
		InfluxDbHealthIndicator healthIndicator = new InfluxDbHealthIndicator(influxDB);
		Health health = healthIndicator.health();
		assertThat(health.getStatus()).isEqualTo(Status.DOWN);
		assertThat((String) health.getDetails().get("error")).contains("Connection failed");
		verify(influxDB).ping();
	}

}
