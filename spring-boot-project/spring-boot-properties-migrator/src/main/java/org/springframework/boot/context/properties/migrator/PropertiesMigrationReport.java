package org.springframework.boot.context.properties.migrator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.boot.configurationmetadata.ConfigurationMetadataProperty;

/**
 * Provides a properties migration report.
 *

 */
class PropertiesMigrationReport {

	private final Map<String, LegacyProperties> content = new LinkedHashMap<>();

	/**
	 * Return a report for all the properties that were automatically renamed. If no such
	 * properties were found, return {@code null}.
	 * @return a report with the configurations keys that should be renamed
	 */
	String getWarningReport() {
		Map<String, List<PropertyMigration>> content = getContent(LegacyProperties::getRenamed);
		if (content.isEmpty()) {
			return null;
		}
		StringBuilder report = new StringBuilder();
		report.append(String
				.format("%nThe use of configuration keys that have been renamed was found in the environment:%n%n"));
		append(report, content);
		report.append(String.format("%n"));
		report.append("Each configuration key has been temporarily mapped to its "
				+ "replacement for your convenience. To silence this warning, please "
				+ "update your configuration to use the new keys.");
		report.append(String.format("%n"));
		return report.toString();
	}

	/**
	 * Return a report for all the properties that are no longer supported. If no such
	 * properties were found, return {@code null}.
	 * @return a report with the configurations keys that are no longer supported
	 */
	String getErrorReport() {
		Map<String, List<PropertyMigration>> content = getContent(LegacyProperties::getUnsupported);
		if (content.isEmpty()) {
			return null;
		}
		StringBuilder report = new StringBuilder();
		report.append(String.format(
				"%nThe use of configuration keys that are no longer supported was found in the environment:%n%n"));
		append(report, content);
		report.append(String.format("%n"));
		report.append("Please refer to the release notes or reference guide for potential alternatives.");
		report.append(String.format("%n"));
		return report.toString();
	}

	private Map<String, List<PropertyMigration>> getContent(
			Function<LegacyProperties, List<PropertyMigration>> extractor) {
		return this.content.entrySet().stream().filter((entry) -> !extractor.apply(entry.getValue()).isEmpty()).collect(
				Collectors.toMap(Map.Entry::getKey, (entry) -> new ArrayList<>(extractor.apply(entry.getValue()))));
	}

	private void append(StringBuilder report, Map<String, List<PropertyMigration>> content) {
		content.forEach((name, properties) -> {
			report.append(String.format("Property source '%s':%n", name));
			properties.sort(PropertyMigration.COMPARATOR);
			properties.forEach((property) -> {
				ConfigurationMetadataProperty metadata = property.getMetadata();
				report.append(String.format("\tKey: %s%n", metadata.getId()));
				if (property.getLineNumber() != null) {
					report.append(String.format("\t\tLine: %d%n", property.getLineNumber()));
				}
				report.append(String.format("\t\t%s%n", property.determineReason()));
			});
			report.append(String.format("%n"));
		});
	}

	/**
	 * Register a new property source.
	 * @param name the name of the property source
	 * @param properties the {@link PropertyMigration} instances
	 */
	void add(String name, List<PropertyMigration> properties) {
		this.content.put(name, new LegacyProperties(properties));
	}

	private static class LegacyProperties {

		private final List<PropertyMigration> properties;

		LegacyProperties(List<PropertyMigration> properties) {
			this.properties = new ArrayList<>(properties);
		}

		List<PropertyMigration> getRenamed() {
			return this.properties.stream().filter(PropertyMigration::isCompatibleType).collect(Collectors.toList());
		}

		List<PropertyMigration> getUnsupported() {
			return this.properties.stream().filter((property) -> !property.isCompatibleType())
					.collect(Collectors.toList());
		}

	}

}
