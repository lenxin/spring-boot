package org.springframework.boot.actuate.health;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link Status}.
 *

 */
class StatusTests {

	@Test
	void createWhenCodeIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new Status(null, ""))
				.withMessage("Code must not be null");
	}

	@Test
	void createWhenDescriptionIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new Status("code", null))
				.withMessage("Description must not be null");
	}

	@Test
	void getCodeReturnsCode() {
		Status status = new Status("spring", "boot");
		assertThat(status.getCode()).isEqualTo("spring");
	}

	@Test
	void getDescriptionReturnsDescription() {
		Status status = new Status("spring", "boot");
		assertThat(status.getDescription()).isEqualTo("boot");
	}

	@Test
	void equalsAndHashCode() {
		Status one = new Status("spring", "boot");
		Status two = new Status("spring", "framework");
		Status three = new Status("spock", "framework");
		assertThat(one).isEqualTo(one).isEqualTo(two).isNotEqualTo(three);
		assertThat(one.hashCode()).isEqualTo(two.hashCode());
	}

	@Test
	void toStringReturnsCode() {
		assertThat(Status.OUT_OF_SERVICE.getCode()).isEqualTo("OUT_OF_SERVICE");
	}

	@Test
	void serializeWithJacksonReturnsValidJson() throws Exception {
		Status status = new Status("spring", "boot");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(status);
		assertThat(json).isEqualTo("{\"description\":\"boot\",\"status\":\"spring\"}");
	}

}
