package org.springframework.boot.build.bom.bomr;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@code UpgradeApplicator} is used to apply an {@link Upgrade}. Modifies the bom
 * configuration in the build file or a version property in {@code gradle.properties}.
 *

 */
class UpgradeApplicator {

	private final Path buildFile;

	private final Path gradleProperties;

	UpgradeApplicator(Path buildFile, Path gradleProperties) {
		this.buildFile = buildFile;
		this.gradleProperties = gradleProperties;
	}

	Path apply(Upgrade upgrade) throws IOException {
		String buildFileContents = new String(Files.readAllBytes(this.buildFile), StandardCharsets.UTF_8);
		Matcher matcher = Pattern.compile("library\\(\"" + upgrade.getLibrary().getName() + "\", \"(.+)\"\\)")
				.matcher(buildFileContents);
		if (!matcher.find()) {
			throw new IllegalStateException("Failed to find definition for library '" + upgrade.getLibrary().getName()
					+ "' in bom '" + this.buildFile + "'");
		}
		String version = matcher.group(1);
		if (version.startsWith("${") && version.endsWith("}")) {
			updateGradleProperties(upgrade, version);
			return this.gradleProperties;
		}
		else {
			updateBuildFile(upgrade, buildFileContents);
			return this.buildFile;
		}
	}

	private void updateGradleProperties(Upgrade upgrade, String version) throws IOException {
		String property = version.substring(2, version.length() - 1);
		String gradlePropertiesContents = new String(Files.readAllBytes(this.gradleProperties), StandardCharsets.UTF_8);
		String modified = gradlePropertiesContents.replace(property + "=" + upgrade.getLibrary().getVersion(),
				property + "=" + upgrade.getVersion());
		Files.write(this.gradleProperties, modified.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
	}

	private void updateBuildFile(Upgrade upgrade, String buildFileContents) throws IOException {
		String modified = buildFileContents.replace(
				"library(\"" + upgrade.getLibrary().getName() + "\", \"" + upgrade.getLibrary().getVersion() + "\")",
				"library(\"" + upgrade.getLibrary().getName() + "\", \"" + upgrade.getVersion() + "\")");
		Files.write(this.buildFile, modified.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
	}

}
