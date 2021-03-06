package org.springframework.boot.devtools.autoconfigure;

/**
 * Configuration properties for remote Spring Boot applications.
 *


 * @since 1.3.0
 * @see DevToolsProperties
 */
public class RemoteDevToolsProperties {

	public static final String DEFAULT_CONTEXT_PATH = "/.~~spring-boot!~";

	public static final String DEFAULT_SECRET_HEADER_NAME = "X-AUTH-TOKEN";

	/**
	 * Context path used to handle the remote connection.
	 */
	private String contextPath = DEFAULT_CONTEXT_PATH;

	/**
	 * A shared secret required to establish a connection (required to enable remote
	 * support).
	 */
	private String secret;

	/**
	 * HTTP header used to transfer the shared secret.
	 */
	private String secretHeaderName = DEFAULT_SECRET_HEADER_NAME;

	private Restart restart = new Restart();

	private Proxy proxy = new Proxy();

	public String getContextPath() {
		return this.contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getSecret() {
		return this.secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getSecretHeaderName() {
		return this.secretHeaderName;
	}

	public void setSecretHeaderName(String secretHeaderName) {
		this.secretHeaderName = secretHeaderName;
	}

	public Restart getRestart() {
		return this.restart;
	}

	public Proxy getProxy() {
		return this.proxy;
	}

	public static class Restart {

		/**
		 * Whether to enable remote restart.
		 */
		private boolean enabled = true;

		public boolean isEnabled() {
			return this.enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

	}

	public static class Proxy {

		/**
		 * The host of the proxy to use to connect to the remote application.
		 */
		private String host;

		/**
		 * The port of the proxy to use to connect to the remote application.
		 */
		private Integer port;

		public String getHost() {
			return this.host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public Integer getPort() {
			return this.port;
		}

		public void setPort(Integer port) {
			this.port = port;
		}

	}

}
