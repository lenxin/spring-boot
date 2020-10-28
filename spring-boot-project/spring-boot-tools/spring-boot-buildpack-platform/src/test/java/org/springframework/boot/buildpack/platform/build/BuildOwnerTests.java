package org.springframework.boot.buildpack.platform.build;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/**
 * Tests for {@link BuildOwner}.
 *


 */
class BuildOwnerTests {

	@Test
	void fromEnvReturnsOwner() {
		Map<String, String> env = new LinkedHashMap<>();
		env.put("CNB_USER_ID", "123");
		env.put("CNB_GROUP_ID", "456");
		BuildOwner owner = BuildOwner.fromEnv(env);
		assertThat(owner.getUid()).isEqualTo(123);
		assertThat(owner.getGid()).isEqualTo(456);
		assertThat(owner.toString()).isEqualTo("123/456");
	}

	@Test
	void fromEnvWhenEnvIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> BuildOwner.fromEnv(null))
				.withMessage("Env must not be null");
	}

	@Test
	void fromEnvWhenUserPropertyIsMissingThrowsException() {
		Map<String, String> env = new LinkedHashMap<>();
		env.put("CNB_GROUP_ID", "456");
		assertThatIllegalStateException().isThrownBy(() -> BuildOwner.fromEnv(env))
				.withMessage("Missing 'CNB_USER_ID' value from the builder environment '" + env + "'");
	}

	@Test
	void fromEnvWhenGroupPropertyIsMissingThrowsException() {
		Map<String, String> env = new LinkedHashMap<>();
		env.put("CNB_USER_ID", "123");
		assertThatIllegalStateException().isThrownBy(() -> BuildOwner.fromEnv(env))
				.withMessage("Missing 'CNB_GROUP_ID' value from the builder environment '" + env + "'");
	}

	@Test
	void fromEnvWhenUserPropertyIsMalformedThrowsException() {
		Map<String, String> env = new LinkedHashMap<>();
		env.put("CNB_USER_ID", "nope");
		env.put("CNB_GROUP_ID", "456");
		assertThatIllegalStateException().isThrownBy(() -> BuildOwner.fromEnv(env))
				.withMessage("Malformed 'CNB_USER_ID' value 'nope' in the builder environment '" + env + "'");
	}

	@Test
	void fromEnvWhenGroupPropertyIsMalformedThrowsException() {
		Map<String, String> env = new LinkedHashMap<>();
		env.put("CNB_USER_ID", "123");
		env.put("CNB_GROUP_ID", "nope");
		assertThatIllegalStateException().isThrownBy(() -> BuildOwner.fromEnv(env))
				.withMessage("Malformed 'CNB_GROUP_ID' value 'nope' in the builder environment '" + env + "'");
	}

}
