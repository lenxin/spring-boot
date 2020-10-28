package org.springframework.boot.actuate.autoconfigure.jdbc;

import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * External configuration properties for {@link DataSourceHealthIndicator}.
 *

 * @since 2.4.0
 */
@ConfigurationProperties(prefix = "management.health.db")
public class DataSourceHealthIndicatorProperties {

	/**
	 * Whether to ignore AbstractRoutingDataSources when creating database health
	 * indicators.
	 */
	private boolean ignoreRoutingDataSources = false;

	public boolean isIgnoreRoutingDataSources() {
		return this.ignoreRoutingDataSources;
	}

	public void setIgnoreRoutingDataSources(boolean ignoreRoutingDataSources) {
		this.ignoreRoutingDataSources = ignoreRoutingDataSources;
	}

}
