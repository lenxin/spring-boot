package org.springframework.boot.actuate.autoconfigure.web.reactive;

import java.util.Map;

import org.springframework.boot.web.reactive.server.AbstractReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.server.WebServerException;
import org.springframework.http.server.reactive.HttpHandler;

import static org.mockito.Mockito.spy;

/**
 * Mock {@link ReactiveWebServerFactory}.
 *

 */
class MockReactiveWebServerFactory extends AbstractReactiveWebServerFactory {

	private MockReactiveWebServer webServer;

	@Override
	public WebServer getWebServer(HttpHandler httpHandler) {
		this.webServer = spy(new MockReactiveWebServer(httpHandler, getPort()));
		return this.webServer;
	}

	MockReactiveWebServer getWebServer() {
		return this.webServer;
	}

	static class MockReactiveWebServer implements WebServer {

		private final int port;

		private HttpHandler httpHandler;

		private Map<String, HttpHandler> httpHandlerMap;

		MockReactiveWebServer(HttpHandler httpHandler, int port) {
			this.httpHandler = httpHandler;
			this.port = port;
		}

		MockReactiveWebServer(Map<String, HttpHandler> httpHandlerMap, int port) {
			this.httpHandlerMap = httpHandlerMap;
			this.port = port;
		}

		HttpHandler getHttpHandler() {
			return this.httpHandler;
		}

		Map<String, HttpHandler> getHttpHandlerMap() {
			return this.httpHandlerMap;
		}

		@Override
		public void start() throws WebServerException {

		}

		@Override
		public void stop() throws WebServerException {

		}

		@Override
		public int getPort() {
			return this.port;
		}

	}

}
