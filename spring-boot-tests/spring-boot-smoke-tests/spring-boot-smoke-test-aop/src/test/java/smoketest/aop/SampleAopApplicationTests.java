package smoketest.aop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SampleAopApplication}.
 *


 */
@ExtendWith(OutputCaptureExtension.class)
class SampleAopApplicationTests {

	private String profiles;

	@BeforeEach
	void init() {
		this.profiles = System.getProperty("spring.profiles.active");
	}

	@AfterEach
	void after() {
		if (this.profiles != null) {
			System.setProperty("spring.profiles.active", this.profiles);
		}
		else {
			System.clearProperty("spring.profiles.active");
		}
	}

	@Test
	void testDefaultSettings(CapturedOutput output) throws Exception {
		SampleAopApplication.main(new String[0]);
		assertThat(output).contains("Hello Phil");
	}

	@Test
	void testCommandLineOverrides(CapturedOutput output) throws Exception {
		SampleAopApplication.main(new String[] { "--name=Gordon" });
		assertThat(output).contains("Hello Gordon");
	}

}
