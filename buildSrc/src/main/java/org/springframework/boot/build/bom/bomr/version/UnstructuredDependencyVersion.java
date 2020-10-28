package org.springframework.boot.build.bom.bomr.version;

import org.apache.maven.artifact.versioning.ComparableVersion;

/**
 * A {@link DependencyVersion} with no structure such that version comparisons are not
 * possible.
 *

 */
final class UnstructuredDependencyVersion extends AbstractDependencyVersion implements DependencyVersion {

	private final String version;

	private UnstructuredDependencyVersion(String version) {
		super(new ComparableVersion(version));
		this.version = version;
	}

	@Override
	public boolean isNewerThan(DependencyVersion other) {
		return compareTo(other) > 0;
	}

	@Override
	public boolean isSameMajorAndNewerThan(DependencyVersion other) {
		return compareTo(other) > 0;
	}

	@Override
	public boolean isSameMinorAndNewerThan(DependencyVersion other) {
		return compareTo(other) > 0;
	}

	@Override
	public String toString() {
		return this.version;
	}

	static UnstructuredDependencyVersion parse(String version) {
		return new UnstructuredDependencyVersion(version);
	}

}
