package org.springframework.boot.build.context.properties;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

/**
 * Table entry regrouping a list of configuration properties sharing the same description.
 *

 */
class CompoundConfigurationTableEntry extends ConfigurationTableEntry {

	private final Set<String> configurationKeys;

	private final String description;

	CompoundConfigurationTableEntry(String key, String description) {
		this.key = key;
		this.description = description;
		this.configurationKeys = new TreeSet<>();
	}

	void addConfigurationKeys(ConfigurationProperty... properties) {
		Stream.of(properties).map(ConfigurationProperty::getName).forEach(this.configurationKeys::add);
	}

	@Override
	void write(AsciidocBuilder builder) {
		builder.append("|[[" + this.key + "]]<<" + this.key + ",");
		this.configurationKeys.forEach(builder::appendKey);
		builder.appendln(">>");
		builder.newLine().appendln("|").appendln("|+++", this.description, "+++");
	}

}
