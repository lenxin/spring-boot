package org.springframework.boot.reactor;

import reactor.core.publisher.Flux;

/**
 * Utility class that should be instrumented by the reactor debug agent.
 *

 * @see DebugAgentEnvironmentPostProcessorTests
 */
class InstrumentedFluxProvider {

	Flux<Integer> newFluxJust() {
		return Flux.just(1);
	}

}
