package org.springframework.boot.cli.command.init;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represent a project type that is supported by a service.
 *

 */
class ProjectType {

	private final String id;

	private final String name;

	private final String action;

	private final boolean defaultType;

	private final Map<String, String> tags = new HashMap<>();

	ProjectType(String id, String name, String action, boolean defaultType, Map<String, String> tags) {
		this.id = id;
		this.name = name;
		this.action = action;
		this.defaultType = defaultType;
		if (tags != null) {
			this.tags.putAll(tags);
		}
	}

	String getId() {
		return this.id;
	}

	String getName() {
		return this.name;
	}

	String getAction() {
		return this.action;
	}

	boolean isDefaultType() {
		return this.defaultType;
	}

	Map<String, String> getTags() {
		return Collections.unmodifiableMap(this.tags);
	}

}
