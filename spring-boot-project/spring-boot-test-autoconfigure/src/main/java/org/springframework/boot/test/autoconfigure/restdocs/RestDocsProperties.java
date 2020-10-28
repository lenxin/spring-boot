package org.springframework.boot.test.autoconfigure.restdocs;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for Spring REST Docs.
 *



 * @since 2.0.0
 */
@ConfigurationProperties("spring.test.restdocs")
public class RestDocsProperties {

	/**
	 * The URI scheme for to use (for example http).
	 */
	private String uriScheme;

	/**
	 * The URI host to use.
	 */
	private String uriHost;

	/**
	 * The URI port to use.
	 */
	private Integer uriPort;

	public String getUriScheme() {
		return this.uriScheme;
	}

	public void setUriScheme(String uriScheme) {
		this.uriScheme = uriScheme;
	}

	public String getUriHost() {
		return this.uriHost;
	}

	public void setUriHost(String uriHost) {
		this.uriHost = uriHost;
	}

	public Integer getUriPort() {
		return this.uriPort;
	}

	public void setUriPort(Integer uriPort) {
		this.uriPort = uriPort;
	}

}
