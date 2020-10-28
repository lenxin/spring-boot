package org.springframework.boot.autoconfigure.rsocket;

import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;

/**
 * Callback interface that can be used to customize a {@link RSocketMessageHandler}.
 *


 * @since 2.3.0
 */
@FunctionalInterface
public interface RSocketMessageHandlerCustomizer {

	/**
	 * Customize the {@link RSocketMessageHandler}.
	 * @param messageHandler the message handler to customize
	 */
	void customize(RSocketMessageHandler messageHandler);

}
