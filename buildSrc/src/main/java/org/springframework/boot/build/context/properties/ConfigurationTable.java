package org.springframework.boot.build.context.properties;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Asciidoctor table listing configuration properties sharing to a common theme.
 *

 */
class ConfigurationTable {

	private final String id;

	private final Set<ConfigurationTableEntry> entries;

	ConfigurationTable(String id) {
		this.id = id;
		this.entries = new TreeSet<>();
	}

	String getId() {
		return this.id;
	}

	void addEntry(ConfigurationTableEntry... entries) {
		this.entries.addAll(Arrays.asList(entries));
	}

	String toAsciidocTable() {
		AsciidocBuilder builder = new AsciidocBuilder();
		builder.appendln("[cols=\"2,1,1\", options=\"header\"]");
		builder.appendln("|===");
		builder.appendln("|Key|Default Value|Description");
		builder.appendln();
		this.entries.forEach((entry) -> {
			entry.write(builder);
			builder.appendln();
		});
		return builder.appendln("|===").toString();
	}

}
