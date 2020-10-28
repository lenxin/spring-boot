package org.springframework.boot.test.json;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link BasicJsonTester}.
 *

 */
class BasicJsonTesterTests {

	private static final String JSON = "{\"spring\":[\"boot\",\"framework\"]}";

	private BasicJsonTester json = new BasicJsonTester(getClass());

	@Test
	void createWhenResourceLoadClassIsNullShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new BasicJsonTester(null))
				.withMessageContaining("ResourceLoadClass must not be null");
	}

	@Test
	void fromJsonStringShouldReturnJsonContent() {
		assertThat(this.json.from(JSON)).isEqualToJson("source.json");
	}

	@Test
	void fromResourceStringShouldReturnJsonContent() {
		assertThat(this.json.from("source.json")).isEqualToJson(JSON);
	}

	@Test
	void fromResourceStringWithClassShouldReturnJsonContent() {
		assertThat(this.json.from("source.json", getClass())).isEqualToJson(JSON);
	}

	@Test
	void fromByteArrayShouldReturnJsonContent() {
		assertThat(this.json.from(JSON.getBytes())).isEqualToJson("source.json");
	}

	@Test
	void fromFileShouldReturnJsonContent(@TempDir Path temp) throws Exception {
		File file = new File(temp.toFile(), "file.json");
		FileCopyUtils.copy(JSON.getBytes(), file);
		assertThat(this.json.from(file)).isEqualToJson("source.json");
	}

	@Test
	void fromInputStreamShouldReturnJsonContent() {
		InputStream inputStream = new ByteArrayInputStream(JSON.getBytes());
		assertThat(this.json.from(inputStream)).isEqualToJson("source.json");
	}

	@Test
	void fromResourceShouldReturnJsonContent() {
		Resource resource = new ByteArrayResource(JSON.getBytes());
		assertThat(this.json.from(resource)).isEqualToJson("source.json");
	}

}
