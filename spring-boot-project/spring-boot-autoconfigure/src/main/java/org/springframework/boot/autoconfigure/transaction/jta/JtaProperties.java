package org.springframework.boot.autoconfigure.transaction.jta;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.transaction.jta.JtaTransactionManager;

/**
 * External configuration properties for a {@link JtaTransactionManager} created by
 * Spring. All {@literal spring.jta.} properties are also applied to the appropriate
 * vendor specific configuration.
 *



 * @since 1.2.0
 */
@ConfigurationProperties(prefix = "spring.jta", ignoreUnknownFields = true)
public class JtaProperties {

	/**
	 * Transaction logs directory.
	 */
	private String logDir;

	/**
	 * Transaction manager unique identifier.
	 */
	private String transactionManagerId;

	public void setLogDir(String logDir) {
		this.logDir = logDir;
	}

	public String getLogDir() {
		return this.logDir;
	}

	public String getTransactionManagerId() {
		return this.transactionManagerId;
	}

	public void setTransactionManagerId(String transactionManagerId) {
		this.transactionManagerId = transactionManagerId;
	}

}
