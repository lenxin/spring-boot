package org.springframework.boot.actuate.endpoint;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.endpoint.http.ApiVersion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link InvocationContext}.
 *

 */
class InvocationContextTests {

	private final SecurityContext securityContext = mock(SecurityContext.class);

	private final Map<String, Object> arguments = Collections.singletonMap("test", "value");

	@Test
	void createWhenApiVersionIsNullUsesLatestVersion() {
		InvocationContext context = new InvocationContext(null, this.securityContext, this.arguments);
		assertThat(context.getApiVersion()).isEqualTo(ApiVersion.LATEST);
	}

	@Test
	void createWhenSecurityContextIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new InvocationContext(null, this.arguments))
				.withMessage("SecurityContext must not be null");
	}

	@Test
	void createWhenArgumentsIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new InvocationContext(this.securityContext, null))
				.withMessage("Arguments must not be null");
	}

	@Test
	void getApiVersionReturnsApiVersion() {
		InvocationContext context = new InvocationContext(ApiVersion.V2, this.securityContext, this.arguments);
		assertThat(context.getApiVersion()).isEqualTo(ApiVersion.V2);
	}

	@Test
	void getSecurityContextReturnsSecurityContext() {
		InvocationContext context = new InvocationContext(this.securityContext, this.arguments);
		assertThat(context.getSecurityContext()).isEqualTo(this.securityContext);
	}

	@Test
	void getArgumentsReturnsArguments() {
		InvocationContext context = new InvocationContext(this.securityContext, this.arguments);
		assertThat(context.getArguments()).isEqualTo(this.arguments);
	}

}
