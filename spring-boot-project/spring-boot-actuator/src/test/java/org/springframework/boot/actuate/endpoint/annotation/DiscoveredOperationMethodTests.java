package org.springframework.boot.actuate.endpoint.annotation;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.endpoint.OperationType;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.util.ReflectionUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link DiscoveredOperationMethod}.
 *

 */
class DiscoveredOperationMethodTests {

	@Test
	void createWhenAnnotationAttributesIsNullShouldThrowException() {
		Method method = ReflectionUtils.findMethod(getClass(), "example");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new DiscoveredOperationMethod(method, OperationType.READ, null))
				.withMessageContaining("AnnotationAttributes must not be null");
	}

	@Test
	void getProducesMediaTypesShouldReturnMediaTypes() {
		Method method = ReflectionUtils.findMethod(getClass(), "example");
		AnnotationAttributes annotationAttributes = new AnnotationAttributes();
		String[] produces = new String[] { "application/json" };
		annotationAttributes.put("produces", produces);
		DiscoveredOperationMethod discovered = new DiscoveredOperationMethod(method, OperationType.READ,
				annotationAttributes);
		assertThat(discovered.getProducesMediaTypes()).containsExactly("application/json");
	}

	void example() {
	}

}
