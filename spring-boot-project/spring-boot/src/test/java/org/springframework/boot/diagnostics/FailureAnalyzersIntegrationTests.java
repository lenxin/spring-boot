package org.springframework.boot.diagnostics;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.testsupport.system.CapturedOutput;
import org.springframework.boot.testsupport.system.OutputCaptureExtension;
import org.springframework.boot.web.server.PortInUseException;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Integration tests for {@link FailureAnalyzers}.
 *

 */
@ExtendWith(OutputCaptureExtension.class)
class FailureAnalyzersIntegrationTests {

	@Test
	void analysisIsPerformed(CapturedOutput output) {
		assertThatExceptionOfType(Exception.class).isThrownBy(
				() -> new SpringApplicationBuilder(TestConfiguration.class).web(WebApplicationType.NONE).run());
		assertThat(output).contains("APPLICATION FAILED TO START");
	}

	@Configuration(proxyBeanMethods = false)
	static class TestConfiguration {

		@PostConstruct
		void fail() {
			throw new PortInUseException(8080);
		}

	}

}
