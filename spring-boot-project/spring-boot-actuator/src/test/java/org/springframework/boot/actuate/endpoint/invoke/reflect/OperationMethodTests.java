package org.springframework.boot.actuate.endpoint.invoke.reflect;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.endpoint.OperationType;
import org.springframework.boot.actuate.endpoint.invoke.OperationParameters;
import org.springframework.util.ReflectionUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link OperationMethod}.
 *

 */
class OperationMethodTests {

	private Method exampleMethod = ReflectionUtils.findMethod(getClass(), "example", String.class);

	@Test
	void createWhenMethodIsNullShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new OperationMethod(null, OperationType.READ))
				.withMessageContaining("Method must not be null");
	}

	@Test
	void createWhenOperationTypeIsNullShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new OperationMethod(this.exampleMethod, null))
				.withMessageContaining("OperationType must not be null");
	}

	@Test
	void getMethodShouldReturnMethod() {
		OperationMethod operationMethod = new OperationMethod(this.exampleMethod, OperationType.READ);
		assertThat(operationMethod.getMethod()).isEqualTo(this.exampleMethod);
	}

	@Test
	void getOperationTypeShouldReturnOperationType() {
		OperationMethod operationMethod = new OperationMethod(this.exampleMethod, OperationType.READ);
		assertThat(operationMethod.getOperationType()).isEqualTo(OperationType.READ);
	}

	@Test
	void getParametersShouldReturnParameters() {
		OperationMethod operationMethod = new OperationMethod(this.exampleMethod, OperationType.READ);
		OperationParameters parameters = operationMethod.getParameters();
		assertThat(parameters.getParameterCount()).isEqualTo(1);
		assertThat(parameters.iterator().next().getName()).isEqualTo("name");
	}

	String example(String name) {
		return name;
	}

}
