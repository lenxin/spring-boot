package org.springframework.boot.autoconfigure.batch;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceInitializationMode;

/**
 * Configuration properties for Spring Batch.
 *



 * @since 1.2.0
 */
@ConfigurationProperties(prefix = "spring.batch")
public class BatchProperties {

	private static final String DEFAULT_SCHEMA_LOCATION = "classpath:org/springframework/"
			+ "batch/core/schema-@@platform@@.sql";

	/**
	 * Path to the SQL file to use to initialize the database schema.
	 */
	private String schema = DEFAULT_SCHEMA_LOCATION;

	/**
	 * Table prefix for all the batch meta-data tables.
	 */
	private String tablePrefix;

	/**
	 * Database schema initialization mode.
	 */
	private DataSourceInitializationMode initializeSchema = DataSourceInitializationMode.EMBEDDED;

	private final Job job = new Job();

	public String getSchema() {
		return this.schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getTablePrefix() {
		return this.tablePrefix;
	}

	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}

	public DataSourceInitializationMode getInitializeSchema() {
		return this.initializeSchema;
	}

	public void setInitializeSchema(DataSourceInitializationMode initializeSchema) {
		this.initializeSchema = initializeSchema;
	}

	public Job getJob() {
		return this.job;
	}

	public static class Job {

		/**
		 * Comma-separated list of job names to execute on startup (for instance,
		 * `job1,job2`). By default, all Jobs found in the context are executed.
		 */
		private String names = "";

		public String getNames() {
			return this.names;
		}

		public void setNames(String names) {
			this.names = names;
		}

	}

}
