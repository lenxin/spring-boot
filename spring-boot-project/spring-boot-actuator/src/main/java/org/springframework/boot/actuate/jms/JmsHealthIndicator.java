package org.springframework.boot.actuate.jms;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

/**
 * {@link HealthIndicator} for a JMS {@link ConnectionFactory}.
 *

 * @since 2.0.0
 */
public class JmsHealthIndicator extends AbstractHealthIndicator {

	private final Log logger = LogFactory.getLog(JmsHealthIndicator.class);

	private final ConnectionFactory connectionFactory;

	public JmsHealthIndicator(ConnectionFactory connectionFactory) {
		super("JMS health check failed");
		this.connectionFactory = connectionFactory;
	}

	@Override
	protected void doHealthCheck(Health.Builder builder) throws Exception {
		try (Connection connection = this.connectionFactory.createConnection()) {
			new MonitoredConnection(connection).start();
			builder.up().withDetail("provider", connection.getMetaData().getJMSProviderName());
		}
	}

	private final class MonitoredConnection {

		private final CountDownLatch latch = new CountDownLatch(1);

		private final Connection connection;

		MonitoredConnection(Connection connection) {
			this.connection = connection;
		}

		void start() throws JMSException {
			new Thread(() -> {
				try {
					if (!this.latch.await(5, TimeUnit.SECONDS)) {
						JmsHealthIndicator.this.logger
								.warn("Connection failed to start within 5 seconds and will be closed.");
						closeConnection();
					}
				}
				catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}, "jms-health-indicator").start();
			this.connection.start();
			this.latch.countDown();
		}

		private void closeConnection() {
			try {
				this.connection.close();
			}
			catch (Exception ex) {
				// Continue
			}
		}

	}

}
