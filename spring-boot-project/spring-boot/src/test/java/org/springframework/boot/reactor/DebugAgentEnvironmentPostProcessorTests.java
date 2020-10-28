package org.springframework.boot.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;

import org.springframework.boot.testsupport.classpath.ClassPathOverrides;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DebugAgentEnvironmentPostProcessor}.
 *

 */
@ClassPathOverrides("io.projectreactor:reactor-tools:3.3.0.RELEASE")
class DebugAgentEnvironmentPostProcessorTests {

	static {
		MockEnvironment environment = new MockEnvironment();
		DebugAgentEnvironmentPostProcessor postProcessor = new DebugAgentEnvironmentPostProcessor();
		postProcessor.postProcessEnvironment(environment, null);
	}

	@Test
	void enablesReactorDebugAgent() {
		InstrumentedFluxProvider fluxProvider = new InstrumentedFluxProvider();
		Flux<Integer> flux = fluxProvider.newFluxJust();
		assertThat(Scannable.from(flux).stepName())
				.startsWith("Flux.just ⇢ at org.springframework.boot.reactor.InstrumentedFluxProvider.newFluxJust");
	}

}
