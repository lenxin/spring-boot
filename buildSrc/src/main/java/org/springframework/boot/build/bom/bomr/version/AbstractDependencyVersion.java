package org.springframework.boot.build.bom.bomr.version;

import org.apache.maven.artifact.versioning.ComparableVersion;

/**
 * Base class for {@link DependencyVersion} implementations.
 *

 */
abstract class AbstractDependencyVersion implements DependencyVersion {

	private final ComparableVersion comparableVersion;

	protected AbstractDependencyVersion(ComparableVersion comparableVersion) {
		this.comparableVersion = comparableVersion;
	}

	@Override
	public int compareTo(DependencyVersion other) {
		ComparableVersion otherComparable = (other instanceof AbstractDependencyVersion)
				? ((AbstractDependencyVersion) other).comparableVersion : new ComparableVersion(other.toString());
		return this.comparableVersion.compareTo(otherComparable);
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
		AbstractDependencyVersion other = (AbstractDependencyVersion) obj;
		if (!this.comparableVersion.equals(other.comparableVersion)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return this.comparableVersion.hashCode();
	}

	@Override
	public String toString() {
		return this.comparableVersion.toString();
	}

}
