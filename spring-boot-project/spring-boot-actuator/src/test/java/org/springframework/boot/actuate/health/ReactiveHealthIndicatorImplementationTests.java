package org.springframework.boot.actuate.health;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link AbstractReactiveHealthIndicator}.
 *


 */
@ExtendWith(OutputCaptureExtension.class)
class ReactiveHealthIndicatorImplementationTests {

	@Test
	void healthUp(CapturedOutput output) {
		StepVerifier.create(new SimpleReactiveHealthIndicator().health())
				.consumeNextWith((health) -> assertThat(health).isEqualTo(Health.up().build())).verifyComplete();
		assertThat(output).doesNotContain("Health check failed for simple");
	}

	@Test
	void healthDownWithCustomErrorMessage(CapturedOutput output) {
		StepVerifier.create(new CustomErrorMessageReactiveHealthIndicator().health()).consumeNextWith(
				(health) -> assertThat(health).isEqualTo(Health.down(new UnsupportedOperationException()).build()))
				.verifyComplete();
		assertThat(output).contains("Health check failed for custom");
	}

	@Test
	void healthDownWithCustomErrorMessageFunction(CapturedOutput output) {
		StepVerifier.create(new CustomErrorMessageFunctionReactiveHealthIndicator().health())
				.consumeNextWith((health) -> assertThat(health).isEqualTo(Health.down(new RuntimeException()).build()))
				.verifyComplete();
		assertThat(output).contains("Health check failed with RuntimeException");
	}

	private static final class SimpleReactiveHealthIndicator extends AbstractReactiveHealthIndicator {

		SimpleReactiveHealthIndicator() {
			super("Health check failed for simple");
		}

		@Override
		protected Mono<Health> doHealthCheck(Builder builder) {
			return Mono.just(builder.up().build());
		}

	}

	private static final class CustomErrorMessageReactiveHealthIndicator extends AbstractReactiveHealthIndicator {

		CustomErrorMessageReactiveHealthIndicator() {
			super("Health check failed for custom");
		}

		@Override
		protected Mono<Health> doHealthCheck(Builder builder) {
			return Mono.error(new UnsupportedOperationException());
		}

	}

	private static final class CustomErrorMessageFunctionReactiveHealthIndicator
			extends AbstractReactiveHealthIndicator {

		CustomErrorMessageFunctionReactiveHealthIndicator() {
			super((ex) -> "Health check failed with " + ex.getClass().getSimpleName());
		}

		@Override
		protected Mono<Health> doHealthCheck(Builder builder) {
			throw new RuntimeException();
		}

	}

}
