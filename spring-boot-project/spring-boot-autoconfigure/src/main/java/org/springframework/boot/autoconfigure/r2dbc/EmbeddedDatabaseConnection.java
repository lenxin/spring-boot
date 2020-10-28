package org.springframework.boot.autoconfigure.r2dbc;

import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * Connection details for embedded databases compatible with r2dbc.
 *


 * @since 2.3.0
 */
public enum EmbeddedDatabaseConnection {

	/**
	 * No Connection.
	 */
	NONE(null, null, null),

	/**
	 * H2 Database Connection.
	 */
	H2("H2", "io.r2dbc.h2.H2ConnectionFactoryProvider",
			"r2dbc:h2:mem://in-memory/%s?options=DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");

	private final String type;

	private final String driverClassName;

	private final String url;

	EmbeddedDatabaseConnection(String type, String driverClassName, String url) {
		this.type = type;
		this.driverClassName = driverClassName;
		this.url = url;
	}

	/**
	 * Returns the driver class name.
	 * @return the driver class name
	 */
	public String getDriverClassName() {
		return this.driverClassName;
	}

	/**
	 * Returns the embedded database type name for the connection.
	 * @return the database type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Returns the R2DBC URL for the connection using the specified {@code databaseName}.
	 * @param databaseName the name of the database
	 * @return the connection URL
	 */
	public String getUrl(String databaseName) {
		Assert.hasText(databaseName, "DatabaseName must not be empty");
		return (this.url != null) ? String.format(this.url, databaseName) : null;
	}

	/**
	 * Returns the most suitable {@link EmbeddedDatabaseConnection} for the given class
	 * loader.
	 * @param classLoader the class loader used to check for classes
	 * @return an {@link EmbeddedDatabaseConnection} or {@link #NONE}.
	 */
	public static EmbeddedDatabaseConnection get(ClassLoader classLoader) {
		for (EmbeddedDatabaseConnection candidate : EmbeddedDatabaseConnection.values()) {
			if (candidate != NONE && ClassUtils.isPresent(candidate.getDriverClassName(), classLoader)) {
				return candidate;
			}
		}
		return NONE;
	}

}
