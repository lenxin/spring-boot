package org.springframework.boot.autoconfigure.webservices;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;

/**
 * {@link ConfigurationProperties @ConfigurationProperties} for Spring Web Services.
 *


 * @since 1.4.0
 */
@ConfigurationProperties(prefix = "spring.webservices")
public class WebServicesProperties {

	/**
	 * Path that serves as the base URI for the services.
	 */
	private String path = "/services";

	private final Servlet servlet = new Servlet();

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		Assert.notNull(path, "Path must not be null");
		Assert.isTrue(path.length() > 1, "Path must have length greater than 1");
		Assert.isTrue(path.startsWith("/"), "Path must start with '/'");
		this.path = path;
	}

	public Servlet getServlet() {
		return this.servlet;
	}

	public static class Servlet {

		/**
		 * Servlet init parameters to pass to Spring Web Services.
		 */
		private Map<String, String> init = new HashMap<>();

		/**
		 * Load on startup priority of the Spring Web Services servlet.
		 */
		private int loadOnStartup = -1;

		public Map<String, String> getInit() {
			return this.init;
		}

		public void setInit(Map<String, String> init) {
			this.init = init;
		}

		public int getLoadOnStartup() {
			return this.loadOnStartup;
		}

		public void setLoadOnStartup(int loadOnStartup) {
			this.loadOnStartup = loadOnStartup;
		}

	}

}
