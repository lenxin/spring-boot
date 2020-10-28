package org.springframework.boot.actuate.health;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link ReactiveHealthContributor}.
 *

 */
class ReactiveHealthContributorTests {

	@Test
	void adaptWhenNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> ReactiveHealthContributor.adapt(null))
				.withMessage("HealthContributor must not be null");
	}

	@Test
	void adaptWhenHealthIndicatorReturnsHealthIndicatorReactiveAdapter() {
		HealthIndicator indicator = () -> Health.outOfService().build();
		ReactiveHealthContributor adapted = ReactiveHealthContributor.adapt(indicator);
		assertThat(adapted).isInstanceOf(HealthIndicatorReactiveAdapter.class);
		assertThat(((ReactiveHealthIndicator) adapted).health().block().getStatus()).isEqualTo(Status.OUT_OF_SERVICE);
	}

	@Test
	void adaptWhenCompositeHealthContributorReturnsCompositeHealthContributorReactiveAdapter() {
		HealthIndicator indicator = () -> Health.outOfService().build();
		CompositeHealthContributor contributor = CompositeHealthContributor
				.fromMap(Collections.singletonMap("a", indicator));
		ReactiveHealthContributor adapted = ReactiveHealthContributor.adapt(contributor);
		assertThat(adapted).isInstanceOf(CompositeHealthContributorReactiveAdapter.class);
		ReactiveHealthContributor contained = ((CompositeReactiveHealthContributor) adapted).getContributor("a");
		assertThat(((ReactiveHealthIndicator) contained).health().block().getStatus()).isEqualTo(Status.OUT_OF_SERVICE);
	}

	@Test
	void adaptWhenUnknownThrowsException() {
		assertThatIllegalStateException()
				.isThrownBy(() -> ReactiveHealthContributor.adapt(mock(HealthContributor.class)))
				.withMessage("Unknown HealthContributor type");
	}

}
