package org.springframework.boot.web.embedded.tomcat;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.ProtocolHandler;

/**
 * Callback interface that can be used to customize the {@link ProtocolHandler} on the
 * {@link Connector}.
 *
 * @param <T> specified type for customization based on {@link ProtocolHandler}

 * @see ConfigurableTomcatWebServerFactory
 * @since 2.2.0
 */
@FunctionalInterface
public interface TomcatProtocolHandlerCustomizer<T extends ProtocolHandler> {

	/**
	 * Customize the protocol handler.
	 * @param protocolHandler the protocol handler to customize
	 */
	void customize(T protocolHandler);

}
