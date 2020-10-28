package org.springframework.boot.buildpack.platform.docker.type;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import org.springframework.boot.buildpack.platform.json.AbstractJsonTests;
import org.springframework.util.StreamUtils;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link ContainerConfig}.
 *


 */
class ContainerConfigTests extends AbstractJsonTests {

	@Test
	void ofWhenImageReferenceIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> ContainerConfig.of(null, (update) -> {
		})).withMessage("ImageReference must not be null");
	}

	@Test
	void ofWhenUpdateIsNullThrowsException() {
		ImageReference imageReference = ImageReference.of("ubuntu:bionic");
		assertThatIllegalArgumentException().isThrownBy(() -> ContainerConfig.of(imageReference, null))
				.withMessage("Update must not be null");
	}

	@Test
	void writeToWritesJson() throws Exception {
		ImageReference imageReference = ImageReference.of("ubuntu:bionic");
		ContainerConfig containerConfig = ContainerConfig.of(imageReference, (update) -> {
			update.withUser("root");
			update.withCommand("ls", "-l");
			update.withArgs("-h");
			update.withLabel("spring", "boot");
			update.withBind("bind-source", "bind-dest");
			update.withEnv("name1", "value1");
			update.withEnv("name2", "value2");
		});
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		containerConfig.writeTo(outputStream);
		String actualJson = new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
		String expectedJson = StreamUtils.copyToString(getContent("container-config.json"), StandardCharsets.UTF_8);
		JSONAssert.assertEquals(expectedJson, actualJson, false);
	}

}
