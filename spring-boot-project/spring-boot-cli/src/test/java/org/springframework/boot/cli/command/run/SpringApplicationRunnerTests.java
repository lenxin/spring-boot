package org.springframework.boot.cli.command.run;

import java.util.logging.Level;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link SpringApplicationRunner}.
 *

 */
class SpringApplicationRunnerTests {

	@Test
	void exceptionMessageWhenSourcesContainsNoClasses() throws Exception {
		SpringApplicationRunnerConfiguration configuration = mock(SpringApplicationRunnerConfiguration.class);
		given(configuration.getClasspath()).willReturn(new String[] { "foo", "bar" });
		given(configuration.getLogLevel()).willReturn(Level.INFO);
		assertThatExceptionOfType(RuntimeException.class)
				.isThrownBy(
						() -> new SpringApplicationRunner(configuration, new String[] { "foo", "bar" }).compileAndRun())
				.withMessage("No classes found in '[foo, bar]'");
	}

}
