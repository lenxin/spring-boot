package org.springframework.boot.context.config;

import org.junit.jupiter.api.Test;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link StandardConfigDataResource}.
 *


 */
public class StandardConfigDataResourceTests {

	StandardConfigDataReference reference = mock(StandardConfigDataReference.class);

	private final Resource resource = mock(Resource.class);

	@Test
	void createWhenReferenceIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new StandardConfigDataResource(null, this.resource))
				.withMessage("Reference must not be null");
	}

	@Test
	void createWhenResourceIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new StandardConfigDataResource(this.reference, null))
				.withMessage("Resource must not be null");
	}

	@Test
	void equalsWhenResourceIsTheSameReturnsTrue() {
		Resource resource = new ClassPathResource("config/");
		StandardConfigDataResource location = new StandardConfigDataResource(this.reference, resource);
		StandardConfigDataResource other = new StandardConfigDataResource(this.reference, resource);
		assertThat(location).isEqualTo(other);
	}

	@Test
	void equalsWhenResourceIsDifferentReturnsFalse() {
		Resource resource1 = new ClassPathResource("config/");
		Resource resource2 = new ClassPathResource("configdata/");
		StandardConfigDataResource location = new StandardConfigDataResource(this.reference, resource1);
		StandardConfigDataResource other = new StandardConfigDataResource(this.reference, resource2);
		assertThat(location).isNotEqualTo(other);
	}

}
