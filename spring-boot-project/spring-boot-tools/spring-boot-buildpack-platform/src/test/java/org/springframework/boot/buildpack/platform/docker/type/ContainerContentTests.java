package org.springframework.boot.buildpack.platform.docker.type;

import org.junit.jupiter.api.Test;

import org.springframework.boot.buildpack.platform.io.TarArchive;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link ContainerContent}.
 *

 */
class ContainerContentTests {

	@Test
	void ofWhenArchiveIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> ContainerContent.of(null))
				.withMessage("Archive must not be null");
	}

	@Test
	void ofWhenDestinationPathIsNullThrowsException() {
		TarArchive archive = mock(TarArchive.class);
		assertThatIllegalArgumentException().isThrownBy(() -> ContainerContent.of(archive, null))
				.withMessage("DestinationPath must not be empty");
	}

	@Test
	void ofWhenDestinationPathIsEmptyThrowsException() {
		TarArchive archive = mock(TarArchive.class);
		assertThatIllegalArgumentException().isThrownBy(() -> ContainerContent.of(archive, ""))
				.withMessage("DestinationPath must not be empty");
	}

	@Test
	void ofCreatesContainerContent() {
		TarArchive archive = mock(TarArchive.class);
		ContainerContent content = ContainerContent.of(archive);
		assertThat(content.getArchive()).isSameAs(archive);
		assertThat(content.getDestinationPath()).isEqualTo("/");
	}

	@Test
	void ofWithDestinationPathCreatesContainerContent() {
		TarArchive archive = mock(TarArchive.class);
		ContainerContent content = ContainerContent.of(archive, "/test");
		assertThat(content.getArchive()).isSameAs(archive);
		assertThat(content.getDestinationPath()).isEqualTo("/test");
	}

}
