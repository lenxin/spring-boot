package org.springframework.boot.autoconfigure.mongo.embedded;

import java.util.Set;

import de.flapdoodle.embed.mongo.distribution.Feature;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

/**
 * Configuration properties for Embedded Mongo.
 *


 * @since 1.3.0
 */
@ConfigurationProperties(prefix = "spring.mongodb.embedded")
public class EmbeddedMongoProperties {

	/**
	 * Version of Mongo to use.
	 */
	private String version = "3.5.5";

	private final Storage storage = new Storage();

	/**
	 * Comma-separated list of features to enable. Uses the defaults of the configured
	 * version by default.
	 */
	private Set<Feature> features = null;

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Set<Feature> getFeatures() {
		return this.features;
	}

	public void setFeatures(Set<Feature> features) {
		this.features = features;
	}

	public Storage getStorage() {
		return this.storage;
	}

	public static class Storage {

		/**
		 * Maximum size of the oplog.
		 */
		@DataSizeUnit(DataUnit.MEGABYTES)
		private DataSize oplogSize;

		/**
		 * Name of the replica set.
		 */
		private String replSetName;

		/**
		 * Directory used for data storage.
		 */
		private String databaseDir;

		public DataSize getOplogSize() {
			return this.oplogSize;
		}

		public void setOplogSize(DataSize oplogSize) {
			this.oplogSize = oplogSize;
		}

		public String getReplSetName() {
			return this.replSetName;
		}

		public void setReplSetName(String replSetName) {
			this.replSetName = replSetName;
		}

		public String getDatabaseDir() {
			return this.databaseDir;
		}

		public void setDatabaseDir(String databaseDir) {
			this.databaseDir = databaseDir;
		}

	}

}
