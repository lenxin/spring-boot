package org.springframework.boot.autoconfigure.solr;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link ConfigurationProperties @ConfigurationProperties} for Solr.
 *

 * @since 1.1.0
 */
@ConfigurationProperties(prefix = "spring.data.solr")
public class SolrProperties {

	/**
	 * Solr host. Ignored if "zk-host" is set.
	 */
	private String host = "http://127.0.0.1:8983/solr";

	/**
	 * ZooKeeper host address in the form HOST:PORT.
	 */
	private String zkHost;

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getZkHost() {
		return this.zkHost;
	}

	public void setZkHost(String zkHost) {
		this.zkHost = zkHost;
	}

}
