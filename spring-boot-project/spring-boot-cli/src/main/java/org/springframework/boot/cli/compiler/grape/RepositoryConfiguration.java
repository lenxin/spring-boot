package org.springframework.boot.cli.compiler.grape;

import java.net.URI;

import org.springframework.util.ObjectUtils;

/**
 * The configuration of a repository.
 *

 * @since 1.0.0
 */
public final class RepositoryConfiguration {

	private final String name;

	private final URI uri;

	private final boolean snapshotsEnabled;

	/**
	 * Creates a new {@code RepositoryConfiguration} instance.
	 * @param name the name of the repository
	 * @param uri the uri of the repository
	 * @param snapshotsEnabled {@code true} if the repository should enable access to
	 * snapshots, {@code false} otherwise
	 */
	public RepositoryConfiguration(String name, URI uri, boolean snapshotsEnabled) {
		this.name = name;
		this.uri = uri;
		this.snapshotsEnabled = snapshotsEnabled;
	}

	/**
	 * Return the name of the repository.
	 * @return the repository name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Return the URI of the repository.
	 * @return the repository URI
	 */
	public URI getUri() {
		return this.uri;
	}

	/**
	 * Return if the repository should enable access to snapshots.
	 * @return {@code true} if snapshot access is enabled
	 */
	public boolean getSnapshotsEnabled() {
		return this.snapshotsEnabled;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		RepositoryConfiguration other = (RepositoryConfiguration) obj;
		return ObjectUtils.nullSafeEquals(this.name, other.name);
	}

	@Override
	public int hashCode() {
		return ObjectUtils.nullSafeHashCode(this.name);
	}

	@Override
	public String toString() {
		return "RepositoryConfiguration [name=" + this.name + ", uri=" + this.uri + ", snapshotsEnabled="
				+ this.snapshotsEnabled + "]";
	}

}
