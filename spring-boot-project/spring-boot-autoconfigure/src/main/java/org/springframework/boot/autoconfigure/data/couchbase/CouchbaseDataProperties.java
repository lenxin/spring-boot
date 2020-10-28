package org.springframework.boot.autoconfigure.data.couchbase;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for Spring Data Couchbase.
 *

 * @since 1.4.0
 */
@ConfigurationProperties(prefix = "spring.data.couchbase")
public class CouchbaseDataProperties {

	/**
	 * Automatically create views and indexes. Use the meta-data provided by
	 * "@ViewIndexed", "@N1qlPrimaryIndexed" and "@N1qlSecondaryIndexed".
	 */
	private boolean autoIndex;

	/**
	 * Name of the bucket to connect to.
	 */
	private String bucketName;

	/**
	 * Name of the scope used for all collection access.
	 */
	private String scopeName;

	/**
	 * Fully qualified name of the FieldNamingStrategy to use.
	 */
	private Class<?> fieldNamingStrategy;

	/**
	 * Name of the field that stores the type information for complex types when using
	 * "MappingCouchbaseConverter".
	 */
	private String typeKey = "_class";

	public boolean isAutoIndex() {
		return this.autoIndex;
	}

	public void setAutoIndex(boolean autoIndex) {
		this.autoIndex = autoIndex;
	}

	public String getBucketName() {
		return this.bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getScopeName() {
		return this.scopeName;
	}

	public void setScopeName(String scopeName) {
		this.scopeName = scopeName;
	}

	public Class<?> getFieldNamingStrategy() {
		return this.fieldNamingStrategy;
	}

	public void setFieldNamingStrategy(Class<?> fieldNamingStrategy) {
		this.fieldNamingStrategy = fieldNamingStrategy;
	}

	public String getTypeKey() {
		return this.typeKey;
	}

	public void setTypeKey(String typeKey) {
		this.typeKey = typeKey;
	}

}
