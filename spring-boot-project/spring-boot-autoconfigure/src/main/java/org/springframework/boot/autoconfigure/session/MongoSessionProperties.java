package org.springframework.boot.autoconfigure.session;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for Mongo-backed Spring Session.
 *

 * @since 2.0.0
 */
@ConfigurationProperties(prefix = "spring.session.mongodb")
public class MongoSessionProperties {

	/**
	 * Collection name used to store sessions.
	 */
	private String collectionName = "sessions";

	public String getCollectionName() {
		return this.collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

}
