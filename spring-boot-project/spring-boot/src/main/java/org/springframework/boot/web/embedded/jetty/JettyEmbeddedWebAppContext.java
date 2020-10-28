package org.springframework.boot.web.embedded.jetty;

import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Jetty {@link WebAppContext} used by {@link JettyWebServer} to support deferred
 * initialization.
 *

 */
class JettyEmbeddedWebAppContext extends WebAppContext {

	@Override
	protected ServletHandler newServletHandler() {
		return new JettyEmbeddedServletHandler();
	}

	void deferredInitialize() throws Exception {
		((JettyEmbeddedServletHandler) getServletHandler()).deferredInitialize();
	}

	private static class JettyEmbeddedServletHandler extends ServletHandler {

		@Override
		public void initialize() throws Exception {
		}

		void deferredInitialize() throws Exception {
			super.initialize();
		}

	}

}
