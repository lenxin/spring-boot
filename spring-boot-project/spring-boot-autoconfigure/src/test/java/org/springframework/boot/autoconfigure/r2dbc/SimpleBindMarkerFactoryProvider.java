package org.springframework.boot.autoconfigure.r2dbc;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.spi.ConnectionFactory;

import org.springframework.boot.autoconfigure.r2dbc.SimpleConnectionFactoryProvider.SimpleTestConnectionFactory;
import org.springframework.r2dbc.core.binding.BindMarkersFactory;
import org.springframework.r2dbc.core.binding.BindMarkersFactoryResolver.BindMarkerFactoryProvider;

/**
 * Simple {@link BindMarkerFactoryProvider} for {@link SimpleConnectionFactoryProvider}.
 *

 */
public class SimpleBindMarkerFactoryProvider implements BindMarkerFactoryProvider {

	@Override
	public BindMarkersFactory getBindMarkers(ConnectionFactory connectionFactory) {
		if (unwrapIfNecessary(connectionFactory) instanceof SimpleTestConnectionFactory) {
			return BindMarkersFactory.anonymous("?");
		}
		return null;
	}

	private ConnectionFactory unwrapIfNecessary(ConnectionFactory connectionFactory) {
		if (connectionFactory instanceof ConnectionPool) {
			return ((ConnectionPool) connectionFactory).unwrap();
		}
		return connectionFactory;
	}

}
