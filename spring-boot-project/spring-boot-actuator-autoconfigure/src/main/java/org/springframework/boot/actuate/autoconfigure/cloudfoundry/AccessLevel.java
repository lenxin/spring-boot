package org.springframework.boot.actuate.autoconfigure.cloudfoundry;

import java.util.Arrays;
import java.util.List;

/**
 * The specific access level granted to the cloud foundry user that's calling the
 * endpoints.
 *

 * @since 2.0.0
 */
public enum AccessLevel {

	/**
	 * Restricted access to a limited set of endpoints.
	 */
	RESTRICTED("", "health", "info"),

	/**
	 * Full access to all endpoints.
	 */
	FULL;

	public static final String REQUEST_ATTRIBUTE = "cloudFoundryAccessLevel";

	private final List<String> ids;

	AccessLevel(String... ids) {
		this.ids = Arrays.asList(ids);
	}

	/**
	 * Returns if the access level should allow access to the specified ID.
	 * @param id the ID to check
	 * @return {@code true} if access is allowed
	 */
	public boolean isAccessAllowed(String id) {
		return this.ids.isEmpty() || this.ids.contains(id);
	}

}
