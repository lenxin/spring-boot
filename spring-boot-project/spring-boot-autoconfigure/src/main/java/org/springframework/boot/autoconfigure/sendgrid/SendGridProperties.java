package org.springframework.boot.autoconfigure.sendgrid;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link ConfigurationProperties @ConfigurationProperties} for SendGrid.
 *


 * @since 1.3.0
 */
@ConfigurationProperties(prefix = "spring.sendgrid")
public class SendGridProperties {

	/**
	 * SendGrid API key.
	 */
	private String apiKey;

	/**
	 * Proxy configuration.
	 */
	private Proxy proxy;

	public String getApiKey() {
		return this.apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public Proxy getProxy() {
		return this.proxy;
	}

	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}

	public boolean isProxyConfigured() {
		return this.proxy != null && this.proxy.getHost() != null && this.proxy.getPort() != null;
	}

	public static class Proxy {

		/**
		 * SendGrid proxy host.
		 */
		private String host;

		/**
		 * SendGrid proxy port.
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
