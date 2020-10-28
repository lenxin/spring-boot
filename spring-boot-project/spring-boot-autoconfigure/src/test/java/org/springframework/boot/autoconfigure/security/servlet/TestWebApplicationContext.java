package org.springframework.boot.autoconfigure.security.servlet;

import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.server.WebServer;
import org.springframework.web.context.support.StaticWebApplicationContext;

/**
 * Test {@link StaticWebApplicationContext} that also implements
 * {@link WebServerApplicationContext}.
 *

 */
class TestWebApplicationContext extends StaticWebApplicationContext implements WebServerApplicationContext {

	private final String serverNamespace;

	TestWebApplicationContext(String serverNamespace) {
		this.serverNamespace = serverNamespace;
	}

	@Override
	public WebServer getWebServer() {
		return null;
	}

	@Override
	public String getServerNamespace() {
		return this.serverNamespace;
	}

}
