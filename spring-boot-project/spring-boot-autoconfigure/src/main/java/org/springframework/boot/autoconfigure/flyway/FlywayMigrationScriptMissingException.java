package org.springframework.boot.autoconfigure.flyway;

import java.util.ArrayList;
import java.util.List;

/**
 * Exception thrown when no Flyway migration script is available.
 *


 * @since 2.2.0
 */
public class FlywayMigrationScriptMissingException extends RuntimeException {

	private final List<String> locations;

	FlywayMigrationScriptMissingException(List<String> locations) {
		super(locations.isEmpty() ? "Migration script locations not configured" : "Cannot find migration scripts in: "
				+ locations + " (please add migration scripts or check your Flyway configuration)");
		this.locations = new ArrayList<>(locations);
	}

	public List<String> getLocations() {
		return this.locations;
	}

}
