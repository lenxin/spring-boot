package org.springframework.boot.actuate.autoconfigure.web.server;

import java.net.InetAddress;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.web.server.Ssl;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Properties for the management server (e.g. port and path settings).
 *



 * @since 2.0.0
 * @see ServerProperties
 */
@ConfigurationProperties(prefix = "management.server", ignoreUnknownFields = true)
public class ManagementServerProperties {

	/**
	 * Management endpoint HTTP port (uses the same port as the application by default).
	 * Configure a different port to use management-specific SSL.
	 */
	private Integer port;

	/**
	 * Network address to which the management endpoints should bind. Requires a custom
	 * management.server.port.
	 */
	private InetAddress address;

	/**
	 * Management endpoint base path (for instance, `/management`). Requires a custom
	 * management.server.port.
	 */
	private String basePath = "";

	private final Servlet servlet = new Servlet();

	@NestedConfigurationProperty
	private Ssl ssl;

	/**
	 * Returns the management port or {@code null} if the
	 * {@link ServerProperties#getPort() server port} should be used.
	 * @return the port
	 * @see #setPort(Integer)
	 */
	public Integer getPort() {
		return this.port;
	}

	/**
	 * Sets the port of the management server, use {@code null} if the
	 * {@link ServerProperties#getPort() server port} should be used. Set to 0 to use a
	 * random port or set to -1 to disable.
	 * @param port the port
	 */
	public void setPort(Integer port) {
		this.port = port;
	}

	public InetAddress getAddress() {
		return this.address;
	}

	public void setAddress(InetAddress address) {
		this.address = address;
	}

	public String getBasePath() {
		return this.basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = cleanBasePath(basePath);
	}

	public Ssl getSsl() {
		return this.ssl;
	}

	public void setSsl(Ssl ssl) {
		this.ssl = ssl;
	}

	public Servlet getServlet() {
		return this.servlet;
	}

	private String cleanBasePath(String basePath) {
		String candidate = StringUtils.trimWhitespace(basePath);
		if (StringUtils.hasText(candidate)) {
			if (!candidate.startsWith("/")) {
				candidate = "/" + candidate;
			}
			if (candidate.endsWith("/")) {
				candidate = candidate.substring(0, candidate.length() - 1);
			}
		}
		return candidate;
	}

	/**
	 * Servlet properties.
	 */
	public static class Servlet {

		/**
		 * Management endpoint context-path (for instance, `/management`). Requires a
		 * custom management.server.port.
		 */
		private String contextPath = "";

		/**
		 * Return the context path with no trailing slash (i.e. the '/' root context is
		 * represented as the empty string).
		 * @return the context path (no trailing slash)
		 * @deprecated since 2.4.0 in favor of
		 * {@link ManagementServerProperties#getBasePath()}
		 */
		@Deprecated
		@DeprecatedConfigurationProperty(replacement = "management.server.base-path")
		public String getContextPath() {
			return this.contextPath;
		}

		/**
		 * Set the context path.
		 * @param contextPath the context path
		 * @deprecated since 2.4.0 in favor of
		 * {@link ManagementServerProperties#setBasePath(String)}
		 */
		@Deprecated
		public void setContextPath(String contextPath) {
			Assert.notNull(contextPath, "ContextPath must not be null");
			this.contextPath = cleanContextPath(contextPath);
		}

		private String cleanContextPath(String contextPath) {
			if (StringUtils.hasText(contextPath) && contextPath.endsWith("/")) {
				return contextPath.substring(0, contextPath.length() - 1);
			}
			return contextPath;
		}

	}

}
