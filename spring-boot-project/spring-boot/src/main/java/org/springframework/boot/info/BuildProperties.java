package org.springframework.boot.info;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

/**
 * Provide build-related information such as group and artifact.
 *

 * @since 1.4.0
 */
public class BuildProperties extends InfoProperties {

	/**
	 * Create an instance with the specified entries.
	 * @param entries the information to expose
	 */
	public BuildProperties(Properties entries) {
		super(processEntries(entries));
	}

	/**
	 * Return the groupId of the project or {@code null}.
	 * @return the group
	 */
	public String getGroup() {
		return get("group");
	}

	/**
	 * Return the artifactId of the project or {@code null}.
	 * @return the artifact
	 */
	public String getArtifact() {
		return get("artifact");
	}

	/**
	 * Return the name of the project or {@code null}.
	 * @return the name
	 */
	public String getName() {
		return get("name");
	}

	/**
	 * Return the version of the project or {@code null}.
	 * @return the version
	 */
	public String getVersion() {
		return get("version");
	}

	/**
	 * Return the timestamp of the build or {@code null}.
	 * <p>
	 * If the original value could not be parsed properly, it is still available with the
	 * {@code time} key.
	 * @return the build time
	 * @see #get(String)
	 */
	public Instant getTime() {
		return getInstant("time");
	}

	private static Properties processEntries(Properties properties) {
		coerceDate(properties, "time");
		return properties;
	}

	private static void coerceDate(Properties properties, String key) {
		String value = properties.getProperty(key);
		if (value != null) {
			try {
				String updatedValue = String
						.valueOf(DateTimeFormatter.ISO_INSTANT.parse(value, Instant::from).toEpochMilli());
				properties.setProperty(key, updatedValue);
			}
			catch (DateTimeException ex) {
				// Ignore and store the original value
			}
		}
	}

}
