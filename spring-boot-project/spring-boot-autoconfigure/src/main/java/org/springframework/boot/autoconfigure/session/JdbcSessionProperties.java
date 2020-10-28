package org.springframework.boot.autoconfigure.session;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceInitializationMode;
import org.springframework.session.FlushMode;
import org.springframework.session.SaveMode;

/**
 * Configuration properties for JDBC backed Spring Session.
 *

 * @since 2.0.0
 */
@ConfigurationProperties(prefix = "spring.session.jdbc")
public class JdbcSessionProperties {

	private static final String DEFAULT_SCHEMA_LOCATION = "classpath:org/springframework/"
			+ "session/jdbc/schema-@@platform@@.sql";

	private static final String DEFAULT_TABLE_NAME = "SPRING_SESSION";

	private static final String DEFAULT_CLEANUP_CRON = "0 * * * * *";

	/**
	 * Path to the SQL file to use to initialize the database schema.
	 */
	private String schema = DEFAULT_SCHEMA_LOCATION;

	/**
	 * Name of the database table used to store sessions.
	 */
	private String tableName = DEFAULT_TABLE_NAME;

	/**
	 * Cron expression for expired session cleanup job.
	 */
	private String cleanupCron = DEFAULT_CLEANUP_CRON;

	/**
	 * Database schema initialization mode.
	 */
	private DataSourceInitializationMode initializeSchema = DataSourceInitializationMode.EMBEDDED;

	/**
	 * Sessions flush mode. Determines when session changes are written to the session
	 * store.
	 */
	private FlushMode flushMode = FlushMode.ON_SAVE;

	/**
	 * Sessions save mode. Determines how session changes are tracked and saved to the
	 * session store.
	 */
	private SaveMode saveMode = SaveMode.ON_SET_ATTRIBUTE;

	public String getSchema() {
		return this.schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getCleanupCron() {
		return this.cleanupCron;
	}

	public void setCleanupCron(String cleanupCron) {
		this.cleanupCron = cleanupCron;
	}

	public DataSourceInitializationMode getInitializeSchema() {
		return this.initializeSchema;
	}

	public void setInitializeSchema(DataSourceInitializationMode initializeSchema) {
		this.initializeSchema = initializeSchema;
	}

	public FlushMode getFlushMode() {
		return this.flushMode;
	}

	public void setFlushMode(FlushMode flushMode) {
		this.flushMode = flushMode;
	}

	public SaveMode getSaveMode() {
		return this.saveMode;
	}

	public void setSaveMode(SaveMode saveMode) {
		this.saveMode = saveMode;
	}

}
