package org.springframework.boot.autoconfigure.session;

/**
 * Supported Spring Session data store types.
 *



 * @since 1.4.0
 */
public enum StoreType {

	/**
	 * Redis backed sessions.
	 */
	REDIS,

	/**
	 * MongoDB backed sessions.
	 */
	MONGODB,

	/**
	 * JDBC backed sessions.
	 */
	JDBC,

	/**
	 * Hazelcast backed sessions.
	 */
	HAZELCAST,

	/**
	 * No session data-store.
	 */
	NONE

}
