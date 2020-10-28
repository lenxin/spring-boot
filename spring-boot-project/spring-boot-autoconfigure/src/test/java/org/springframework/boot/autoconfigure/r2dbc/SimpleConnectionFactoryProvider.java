package org.springframework.boot.autoconfigure.r2dbc;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryMetadata;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.ConnectionFactoryProvider;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

/**
 * Simple driver to capture {@link ConnectionFactoryOptions}.
 *

 */
public class SimpleConnectionFactoryProvider implements ConnectionFactoryProvider {

	@Override
	public ConnectionFactory create(ConnectionFactoryOptions connectionFactoryOptions) {
		return new SimpleTestConnectionFactory(connectionFactoryOptions);
	}

	@Override
	public boolean supports(ConnectionFactoryOptions connectionFactoryOptions) {
		return connectionFactoryOptions.getRequiredValue(ConnectionFactoryOptions.DRIVER).equals("simple");
	}

	@Override
	public String getDriver() {
		return "simple";
	}

	public static class SimpleTestConnectionFactory implements ConnectionFactory {

		final ConnectionFactoryOptions options;

		SimpleTestConnectionFactory(ConnectionFactoryOptions options) {
			this.options = options;
		}

		@Override
		public Publisher<? extends Connection> create() {
			return Mono.error(new UnsupportedOperationException());
		}

		@Override
		public ConnectionFactoryMetadata getMetadata() {
			return SimpleConnectionFactoryProvider.class::getName;
		}

		public ConnectionFactoryOptions getOptions() {
			return this.options;
		}

	}

}
