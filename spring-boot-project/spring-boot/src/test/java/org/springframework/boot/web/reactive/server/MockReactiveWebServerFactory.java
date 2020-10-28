package org.springframework.boot.web.reactive.server;

import java.util.Map;

import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.server.WebServerException;
import org.springframework.http.server.reactive.HttpHandler;

import static org.mockito.Mockito.spy;

/**
 * Mock {@link ReactiveWebServerFactory}.
 *

 */
public class MockReactiveWebServerFactory extends AbstractReactiveWebServerFactory {

	private MockReactiveWebServer webServer;

	@Override
	public WebServer getWebServer(HttpHandler httpHandler) {
		this.webServer = spy(new MockReactiveWebServer(httpHandler, getPort()));
		return this.webServer;
	}

	public MockReactiveWebServer getWebServer() {
		return this.webServer;
	}

	public static class MockReactiveWebServer implements WebServer {

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

		public HttpHandler getHttpHandler() {
			return this.httpHandler;
		}

		public Map<String, HttpHandler> getHttpHandlerMap() {
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
