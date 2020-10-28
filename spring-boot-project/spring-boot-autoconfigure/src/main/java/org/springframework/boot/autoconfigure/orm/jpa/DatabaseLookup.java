package org.springframework.boot.autoconfigure.orm.jpa;

import java.sql.DatabaseMetaData;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.orm.jpa.vendor.Database;

/**
 * Utility to lookup well known {@link Database Databases} from a {@link DataSource}.
 *


 */
final class DatabaseLookup {

	private static final Log logger = LogFactory.getLog(DatabaseLookup.class);

	private static final Map<DatabaseDriver, Database> LOOKUP;

	static {
		Map<DatabaseDriver, Database> map = new EnumMap<>(DatabaseDriver.class);
		map.put(DatabaseDriver.DERBY, Database.DERBY);
		map.put(DatabaseDriver.H2, Database.H2);
		map.put(DatabaseDriver.HSQLDB, Database.HSQL);
		map.put(DatabaseDriver.MYSQL, Database.MYSQL);
		map.put(DatabaseDriver.ORACLE, Database.ORACLE);
		map.put(DatabaseDriver.POSTGRESQL, Database.POSTGRESQL);
		map.put(DatabaseDriver.SQLSERVER, Database.SQL_SERVER);
		map.put(DatabaseDriver.DB2, Database.DB2);
		map.put(DatabaseDriver.INFORMIX, Database.INFORMIX);
		map.put(DatabaseDriver.HANA, Database.HANA);
		LOOKUP = Collections.unmodifiableMap(map);
	}

	private DatabaseLookup() {
	}

	/**
	 * Return the most suitable {@link Database} for the given {@link DataSource}.
	 * @param dataSource the source {@link DataSource}
	 * @return the most suitable {@link Database}
	 */
	static Database getDatabase(DataSource dataSource) {
		if (dataSource == null) {
			return Database.DEFAULT;
		}
		try {
			String url = JdbcUtils.extractDatabaseMetaData(dataSource, DatabaseMetaData::getURL);
			DatabaseDriver driver = DatabaseDriver.fromJdbcUrl(url);
			Database database = LOOKUP.get(driver);
			if (database != null) {
				return database;
			}
		}
		catch (MetaDataAccessException ex) {
			logger.warn("Unable to determine jdbc url from datasource", ex);
		}
		return Database.DEFAULT;
	}

}
