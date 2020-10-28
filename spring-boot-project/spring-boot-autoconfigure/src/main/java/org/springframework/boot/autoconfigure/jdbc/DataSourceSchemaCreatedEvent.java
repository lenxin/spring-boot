package org.springframework.boot.autoconfigure.jdbc;

import javax.sql.DataSource;

import org.springframework.context.ApplicationEvent;

/**
 * {@link ApplicationEvent} used internally to indicate that the schema of a new
 * {@link DataSource} has been created. This happens when {@literal schema-*.sql} files
 * are executed or when Hibernate initializes the database.
 *


 * @since 2.0.0
 */
@SuppressWarnings("serial")
public class DataSourceSchemaCreatedEvent extends ApplicationEvent {

	/**
	 * Create a new {@link DataSourceSchemaCreatedEvent}.
	 * @param source the source {@link DataSource}.
	 */
	public DataSourceSchemaCreatedEvent(DataSource source) {
		super(source);
	}

}
