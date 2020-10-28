package org.springframework.boot.actuate.health;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link HealthEndpointGroups}.
 *

 */
class HealthEndpointGroupsTests {

	@Test
	void ofWhenPrimaryIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> HealthEndpointGroups.of(null, Collections.emptyMap()))
				.withMessage("Primary must not be null");
	}

	@Test
	void ofWhenAdditionalIsNullThrowsException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> HealthEndpointGroups.of(mock(HealthEndpointGroup.class), null))
				.withMessage("Additional must not be null");
	}

	@Test
	void ofReturnsHealthEndpointGroupsInstance() {
		HealthEndpointGroup primary = mock(HealthEndpointGroup.class);
		HealthEndpointGroup group = mock(HealthEndpointGroup.class);
		HealthEndpointGroups groups = HealthEndpointGroups.of(primary, Collections.singletonMap("group", group));
		assertThat(groups.getPrimary()).isSameAs(primary);
		assertThat(groups.getNames()).containsExactly("group");
		assertThat(groups.get("group")).isSameAs(group);
		assertThat(groups.get("missing")).isNull();
	}

}
