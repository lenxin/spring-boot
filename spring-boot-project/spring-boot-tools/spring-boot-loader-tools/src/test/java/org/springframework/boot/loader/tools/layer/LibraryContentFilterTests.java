package org.springframework.boot.loader.tools.layer;

import org.junit.jupiter.api.Test;

import org.springframework.boot.loader.tools.Library;
import org.springframework.boot.loader.tools.LibraryCoordinates;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link LibraryContentFilter}.
 *



 */
class LibraryContentFilterTests {

	@Test
	void createWhenCoordinatesPatternIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new LibraryContentFilter(null))
				.withMessage("CoordinatesPattern must not be empty");
	}

	@Test
	void createWhenCoordinatesPatternIsEmptyThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new LibraryContentFilter(""))
				.withMessage("CoordinatesPattern must not be empty");
	}

	@Test
	void matchesWhenGroupIdIsNullAndToMatchHasWildcardReturnsTrue() {
		LibraryContentFilter filter = new LibraryContentFilter("*:*");
		assertThat(filter.matches(mockLibrary(null, null, null))).isTrue();
	}

	@Test
	void matchesWhenArtifactIdIsNullAndToMatchHasWildcardReturnsTrue() {
		LibraryContentFilter filter = new LibraryContentFilter("org.acme:*");
		assertThat(filter.matches(mockLibrary("org.acme", null, null))).isTrue();
	}

	@Test
	void matchesWhenVersionIsNullAndToMatchHasWildcardReturnsTrue() {
		LibraryContentFilter filter = new LibraryContentFilter("org.acme:something:*");
		assertThat(filter.matches(mockLibrary("org.acme", "something", null))).isTrue();
	}

	@Test
	void matchesWhenGroupIdDoesNotMatchReturnsFalse() {
		LibraryContentFilter filter = new LibraryContentFilter("org.acme:*");
		assertThat(filter.matches(mockLibrary("other.foo", null, null))).isFalse();
	}

	@Test
	void matchesWhenWhenArtifactIdDoesNotMatchReturnsFalse() {
		LibraryContentFilter filter = new LibraryContentFilter("org.acme:test:*");
		assertThat(filter.matches(mockLibrary("org.acme", "other", null))).isFalse();
	}

	@Test
	void matchesWhenArtifactIdMatchesReturnsTrue() {
		LibraryContentFilter filter = new LibraryContentFilter("org.acme:test:*");
		assertThat(filter.matches(mockLibrary("org.acme", "test", null))).isTrue();
	}

	@Test
	void matchesWhenVersionDoesNotMatchReturnsFalse() {
		LibraryContentFilter filter = new LibraryContentFilter("org.acme:test:*SNAPSHOT");
		assertThat(filter.matches(mockLibrary("org.acme", "test", "1.0.0"))).isFalse();
	}

	@Test
	void matchesWhenVersionMatchesReturnsTrue() {
		LibraryContentFilter filter = new LibraryContentFilter("org.acme:test:*SNAPSHOT");
		assertThat(filter.matches(mockLibrary("org.acme", "test", "1.0.0-SNAPSHOT"))).isTrue();
	}

	private Library mockLibrary(String groupId, String artifactId, String version) {
		return mockLibrary(LibraryCoordinates.of(groupId, artifactId, version));
	}

	private Library mockLibrary(LibraryCoordinates coordinates) {
		Library library = mock(Library.class);
		given(library.getCoordinates()).willReturn(coordinates);
		return library;
	}

}
