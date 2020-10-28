package org.springframework.boot.web.servlet.server;

import java.util.Arrays;

import javax.servlet.ServletContext;

import org.springframework.boot.testsupport.web.servlet.MockServletWebServer.RegisteredFilter;
import org.springframework.boot.testsupport.web.servlet.MockServletWebServer.RegisteredServlet;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.server.WebServerException;
import org.springframework.boot.web.servlet.ServletContextInitializer;

import static org.mockito.Mockito.spy;

/**
 * Mock {@link ServletWebServerFactory}.
 *


 */
public class MockServletWebServerFactory extends AbstractServletWebServerFactory {

	private MockServletWebServer webServer;

	@Override
	public WebServer getWebServer(ServletContextInitializer... initializers) {
		this.webServer = spy(new MockServletWebServer(mergeInitializers(initializers), getPort()));
		return this.webServer;
	}

	public MockServletWebServer getWebServer() {
		return this.webServer;
	}

	public ServletContext getServletContext() {
		return (getWebServer() != null) ? getWebServer().getServletContext() : null;
	}

	public RegisteredServlet getRegisteredServlet(int index) {
		return (getWebServer() != null) ? getWebServer().getRegisteredServlet(index) : null;
	}

	public RegisteredFilter getRegisteredFilter(int index) {
		return (getWebServer() != null) ? getWebServer().getRegisteredFilters(index) : null;
	}

	public static class MockServletWebServer
			extends org.springframework.boot.testsupport.web.servlet.MockServletWebServer implements WebServer {

		MockServletWebServer(ServletContextInitializer[] initializers, int port) {
			super(Arrays.stream(initializers).map((initializer) -> (Initializer) initializer::onStartup)
					.toArray(Initializer[]::new), port);
		}

		@Override
		public void start() throws WebServerException {
		}

	}

}
