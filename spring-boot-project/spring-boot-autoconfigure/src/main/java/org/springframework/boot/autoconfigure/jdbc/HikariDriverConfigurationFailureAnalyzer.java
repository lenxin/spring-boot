package org.springframework.boot.autoconfigure.jdbc;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

/**
 * An {@link AbstractFailureAnalyzer} that performs analysis of a Hikari configuration
 * failure caused by the use of the unsupported 'dataSourceClassName' property.
 *

 */
class HikariDriverConfigurationFailureAnalyzer extends AbstractFailureAnalyzer<CannotGetJdbcConnectionException> {

	private static final String EXPECTED_MESSAGE = "Failed to obtain JDBC Connection:"
			+ " cannot use driverClassName and dataSourceClassName together.";

	@Override
	protected FailureAnalysis analyze(Throwable rootFailure, CannotGetJdbcConnectionException cause) {
		if (!EXPECTED_MESSAGE.equals(cause.getMessage())) {
			return null;
		}
		return new FailureAnalysis(
				"Configuration of the Hikari connection pool failed: 'dataSourceClassName' is not supported.",
				"Spring Boot auto-configures only a driver and can't specify a custom "
						+ "DataSource. Consider configuring the Hikari DataSource in your own configuration.",
				cause);
	}

}
