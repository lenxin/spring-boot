package org.springframework.boot.actuate.endpoint.web;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.endpoint.http.ActuatorMediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link EndpointMediaTypes}.
 *

 */
class EndpointMediaTypesTests {

	@Test
	void defaultReturnsExpectedProducedAndConsumedTypes() {
		assertThat(EndpointMediaTypes.DEFAULT.getProduced()).containsExactly(ActuatorMediaType.V3_JSON,
				ActuatorMediaType.V2_JSON, "application/json");
		assertThat(EndpointMediaTypes.DEFAULT.getConsumed()).containsExactly(ActuatorMediaType.V3_JSON,
				ActuatorMediaType.V2_JSON, "application/json");
	}

	@Test
	void createWhenProducedIsNullShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new EndpointMediaTypes(null, Collections.emptyList()))
				.withMessageContaining("Produced must not be null");
	}

	@Test
	void createWhenConsumedIsNullShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new EndpointMediaTypes(Collections.emptyList(), null))
				.withMessageContaining("Consumed must not be null");
	}

	@Test
	void createFromProducedAndConsumedUsesSameListForBoth() {
		EndpointMediaTypes types = new EndpointMediaTypes("spring/framework", "spring/boot");
		assertThat(types.getProduced()).containsExactly("spring/framework", "spring/boot");
		assertThat(types.getConsumed()).containsExactly("spring/framework", "spring/boot");
	}

	@Test
	void getProducedShouldReturnProduced() {
		List<String> produced = Arrays.asList("a", "b", "c");
		EndpointMediaTypes types = new EndpointMediaTypes(produced, Collections.emptyList());
		assertThat(types.getProduced()).isEqualTo(produced);
	}

	@Test
	void getConsumedShouldReturnConsumed() {
		List<String> consumed = Arrays.asList("a", "b", "c");
		EndpointMediaTypes types = new EndpointMediaTypes(Collections.emptyList(), consumed);
		assertThat(types.getConsumed()).isEqualTo(consumed);
	}

}
