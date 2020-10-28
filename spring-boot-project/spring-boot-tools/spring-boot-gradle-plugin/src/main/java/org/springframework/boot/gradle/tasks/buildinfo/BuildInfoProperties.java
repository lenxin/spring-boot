package org.springframework.boot.gradle.tasks.buildinfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.gradle.api.Project;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;

/**
 * The properties that are written into the {@code build-info.properties} file.
 *

 * @since 2.0.0
 */
@SuppressWarnings("serial")
public class BuildInfoProperties implements Serializable {

	private transient Instant creationTime = Instant.now();

	private final Property<String> group;

	private final Property<String> artifact;

	private final Property<String> version;

	private final Property<String> name;

	private final Property<Long> time;

	private boolean timeConfigured = false;

	private Map<String, Object> additionalProperties = new HashMap<>();

	BuildInfoProperties(Project project) {
		this.time = project.getObjects().property(Long.class);
		this.group = project.getObjects().property(String.class);
		this.group.set(project.provider(() -> project.getGroup().toString()));
		this.artifact = project.getObjects().property(String.class);
		this.version = project.getObjects().property(String.class);
		this.version.set(projectVersion(project));
		this.name = project.getObjects().property(String.class);
		this.name.set(project.provider(project::getName));
	}

	private Provider<String> projectVersion(Project project) {
		try {
			Provider<String> externalVersionProperty = project.getProviders().gradleProperty("version")
					.forUseAtConfigurationTime();
			externalVersionProperty.getOrNull();
		}
		catch (NoSuchMethodError ex) {
			// Gradle < 6.5
		}
		return project.provider(() -> project.getVersion().toString());
	}

	/**
	 * Returns the value used for the {@code build.group} property. Defaults to the
	 * {@link Project#getGroup() Project's group}.
	 * @return the group
	 */
	@Input
	@Optional
	public String getGroup() {
		return this.group.getOrNull();
	}

	/**
	 * Sets the value used for the {@code build.group} property.
	 * @param group the group name
	 */
	public void setGroup(String group) {
		this.group.set(group);
	}

	/**
	 * Returns the value used for the {@code build.artifact} property.
	 * @return the artifact
	 */
	@Input
	@Optional
	public String getArtifact() {
		return this.artifact.getOrNull();
	}

	/**
	 * Sets the value used for the {@code build.artifact} property.
	 * @param artifact the artifact
	 */
	public void setArtifact(String artifact) {
		this.artifact.set(artifact);
	}

	/**
	 * Returns the value used for the {@code build.version} property. Defaults to the
	 * {@link Project#getVersion() Project's version}.
	 * @return the version
	 */
	@Input
	@Optional
	public String getVersion() {
		return this.version.getOrNull();
	}

	/**
	 * Sets the value used for the {@code build.version} property.
	 * @param version the version
	 */
	public void setVersion(String version) {
		this.version.set(version);
	}

	/**
	 * Returns the value used for the {@code build.name} property. Defaults to the
	 * {@link Project#getDisplayName() Project's display name}.
	 * @return the name
	 */
	@Input
	@Optional
	public String getName() {
		return this.name.getOrNull();
	}

	/**
	 * Sets the value used for the {@code build.name} property.
	 * @param name the name
	 */
	public void setName(String name) {
		this.name.set(name);
	}

	/**
	 * Returns the value used for the {@code build.time} property. Defaults to
	 * {@link Instant#now} when the {@code BuildInfoProperties} instance was created.
	 * @return the time
	 */
	@Input
	@Optional
	public Instant getTime() {
		Long epochMillis = this.time.getOrNull();
		if (epochMillis != null) {
			return Instant.ofEpochMilli(epochMillis);
		}
		if (this.timeConfigured) {
			return null;
		}
		return this.creationTime;
	}

	/**
	 * Sets the value used for the {@code build.time} property.
	 * @param time the build time
	 */
	public void setTime(Instant time) {
		this.timeConfigured = true;
		this.time.set((time != null) ? time.toEpochMilli() : null);
	}

	/**
	 * Returns the additional properties that will be included. When written, the name of
	 * each additional property is prefixed with {@code build.}.
	 * @return the additional properties
	 */
	@Input
	@Optional
	public Map<String, Object> getAdditional() {
		return this.additionalProperties;
	}

	/**
	 * Sets the additional properties that will be included. When written, the name of
	 * each additional property is prefixed with {@code build.}.
	 * @param additionalProperties the additional properties
	 */
	public void setAdditional(Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}

	private void readObject(ObjectInputStream input) throws ClassNotFoundException, IOException {
		input.defaultReadObject();
		this.creationTime = Instant.now();
	}

}
