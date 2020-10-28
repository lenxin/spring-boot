package org.springframework.boot.build.bom.bomr.version;

import java.util.Optional;

import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.ComparableVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

/**
 * A {@link DependencyVersion} backed by an {@link ArtifactVersion}.
 *

 */
class ArtifactVersionDependencyVersion extends AbstractDependencyVersion {

	private final ArtifactVersion artifactVersion;

	protected ArtifactVersionDependencyVersion(ArtifactVersion artifactVersion) {
		super(new ComparableVersion(artifactVersion.toString()));
		this.artifactVersion = artifactVersion;
	}

	protected ArtifactVersionDependencyVersion(ArtifactVersion artifactVersion, ComparableVersion comparableVersion) {
		super(comparableVersion);
		this.artifactVersion = artifactVersion;
	}

	@Override
	public boolean isNewerThan(DependencyVersion other) {
		if (other instanceof ReleaseTrainDependencyVersion) {
			return false;
		}
		return compareTo(other) > 0;
	}

	@Override
	public boolean isSameMajorAndNewerThan(DependencyVersion other) {
		if (other instanceof ReleaseTrainDependencyVersion) {
			return false;
		}
		return extractArtifactVersionDependencyVersion(other).map(this::isSameMajorAndNewerThan).orElse(true);
	}

	private boolean isSameMajorAndNewerThan(ArtifactVersionDependencyVersion other) {
		return this.artifactVersion.getMajorVersion() == other.artifactVersion.getMajorVersion() && isNewerThan(other);
	}

	@Override
	public boolean isSameMinorAndNewerThan(DependencyVersion other) {
		if (other instanceof ReleaseTrainDependencyVersion) {
			return false;
		}
		return extractArtifactVersionDependencyVersion(other).map(this::isSameMinorAndNewerThan).orElse(true);
	}

	private boolean isSameMinorAndNewerThan(ArtifactVersionDependencyVersion other) {
		return this.artifactVersion.getMajorVersion() == other.artifactVersion.getMajorVersion()
				&& this.artifactVersion.getMinorVersion() == other.artifactVersion.getMinorVersion()
				&& isNewerThan(other);
	}

	@Override
	public String toString() {
		return this.artifactVersion.toString();
	}

	protected Optional<ArtifactVersionDependencyVersion> extractArtifactVersionDependencyVersion(
			DependencyVersion other) {
		ArtifactVersionDependencyVersion artifactVersion = null;
		if (other instanceof ArtifactVersionDependencyVersion) {
			artifactVersion = (ArtifactVersionDependencyVersion) other;
		}
		return Optional.ofNullable(artifactVersion);
	}

	static ArtifactVersionDependencyVersion parse(String version) {
		ArtifactVersion artifactVersion = new DefaultArtifactVersion(version);
		if (artifactVersion.getQualifier() != null && artifactVersion.getQualifier().equals(version)) {
			return null;
		}
		return new ArtifactVersionDependencyVersion(artifactVersion);
	}

}
