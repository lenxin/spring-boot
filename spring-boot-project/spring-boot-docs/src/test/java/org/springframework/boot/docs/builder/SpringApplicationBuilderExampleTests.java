package org.springframework.boot.docs.builder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SpringApplicationBuilderExample}.
 *

 */
@ExtendWith(OutputCaptureExtension.class)
class SpringApplicationBuilderExampleTests {

	@Test
	void contextHierarchyWithDisabledBanner(CapturedOutput output) {
		System.setProperty("spring.main.web-application-type", "none");
		try {
			new SpringApplicationBuilderExample().hierarchyWithDisabledBanner(new String[0]);
			assertThat(output).doesNotContain(":: Spring Boot ::");
		}
		finally {
			System.clearProperty("spring.main.web-application-type");
		}
	}

}
