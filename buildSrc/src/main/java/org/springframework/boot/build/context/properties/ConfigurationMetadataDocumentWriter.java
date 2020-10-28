package org.springframework.boot.build.context.properties;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.gradle.api.file.FileCollection;

/**
 * Write Asciidoc documents with configuration properties listings.
 *

 * @since 2.0.0
 */
public class ConfigurationMetadataDocumentWriter {

	public void writeDocument(Path outputDirectory, DocumentOptions options, FileCollection metadataFiles)
			throws IOException {
		assertValidOutputDirectory(outputDirectory);
		if (!Files.exists(outputDirectory)) {
			Files.createDirectory(outputDirectory);
		}
		List<ConfigurationTable> tables = createConfigTables(ConfigurationProperties.fromFiles(metadataFiles), options);
		for (ConfigurationTable table : tables) {
			writeConfigurationTable(table, outputDirectory);
		}
	}

	private void assertValidOutputDirectory(Path outputDirPath) {
		if (outputDirPath == null) {
			throw new IllegalArgumentException("output path should not be null");
		}
		if (Files.exists(outputDirPath) && !Files.isDirectory(outputDirPath)) {
			throw new IllegalArgumentException("output path already exists and is not a directory");
		}
	}

	private List<ConfigurationTable> createConfigTables(Map<String, ConfigurationProperty> metadataProperties,
			DocumentOptions options) {
		List<ConfigurationTable> tables = new ArrayList<>();
		List<String> unmappedKeys = metadataProperties.values().stream().filter((property) -> !property.isDeprecated())
				.map(ConfigurationProperty::getName).collect(Collectors.toList());
		Map<String, CompoundConfigurationTableEntry> overrides = getOverrides(metadataProperties, unmappedKeys,
				options);
		options.getMetadataSections().forEach((id, keyPrefixes) -> tables
				.add(createConfigTable(metadataProperties, unmappedKeys, overrides, id, keyPrefixes)));
		if (!unmappedKeys.isEmpty()) {
			throw new IllegalStateException(
					"The following keys were not written to the documentation: " + String.join(", ", unmappedKeys));
		}
		if (!overrides.isEmpty()) {
			throw new IllegalStateException("The following keys  were not written to the documentation: "
					+ String.join(", ", overrides.keySet()));
		}
		return tables;
	}

	private Map<String, CompoundConfigurationTableEntry> getOverrides(
			Map<String, ConfigurationProperty> metadataProperties, List<String> unmappedKeys, DocumentOptions options) {
		Map<String, CompoundConfigurationTableEntry> overrides = new HashMap<>();
		options.getOverrides().forEach((keyPrefix, description) -> {
			CompoundConfigurationTableEntry entry = new CompoundConfigurationTableEntry(keyPrefix, description);
			List<String> matchingKeys = unmappedKeys.stream().filter((key) -> key.startsWith(keyPrefix))
					.collect(Collectors.toList());
			for (String matchingKey : matchingKeys) {
				entry.addConfigurationKeys(metadataProperties.get(matchingKey));
			}
			overrides.put(keyPrefix, entry);
			unmappedKeys.removeAll(matchingKeys);
		});
		return overrides;
	}

	private ConfigurationTable createConfigTable(Map<String, ConfigurationProperty> metadataProperties,
			List<String> unmappedKeys, Map<String, CompoundConfigurationTableEntry> overrides, String id,
			List<String> keyPrefixes) {
		ConfigurationTable table = new ConfigurationTable(id);
		for (String keyPrefix : keyPrefixes) {
			List<String> matchingOverrides = overrides.keySet().stream()
					.filter((overrideKey) -> overrideKey.startsWith(keyPrefix)).collect(Collectors.toList());
			matchingOverrides.forEach((match) -> table.addEntry(overrides.remove(match)));
		}
		List<String> matchingKeys = unmappedKeys.stream()
				.filter((key) -> keyPrefixes.stream().anyMatch(key::startsWith)).collect(Collectors.toList());
		for (String matchingKey : matchingKeys) {
			ConfigurationProperty property = metadataProperties.get(matchingKey);
			table.addEntry(new SingleConfigurationTableEntry(property));
		}
		unmappedKeys.removeAll(matchingKeys);
		return table;
	}

	private void writeConfigurationTable(ConfigurationTable table, Path outputDirectory) throws IOException {
		Path outputFilePath = outputDirectory.resolve(table.getId() + ".adoc");
		Files.deleteIfExists(outputFilePath);
		Files.createFile(outputFilePath);
		try (OutputStream outputStream = Files.newOutputStream(outputFilePath)) {
			outputStream.write(table.toAsciidocTable().getBytes(StandardCharsets.UTF_8));
		}
	}

}
