package smoketest.simple;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SampleSimpleApplication}.
 *


 */
@ExtendWith(OutputCaptureExtension.class)
class SampleSimpleApplicationTests {

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
	void testDefaultSettings(CapturedOutput output) {
		SampleSimpleApplication.main(new String[0]);
		assertThat(output).contains("Hello Phil");
	}

	@Test
	void testCommandLineOverrides(CapturedOutput output) {
		SampleSimpleApplication.main(new String[] { "--name=Gordon", "--duration=1m" });
		assertThat(output).contains("Hello Gordon for 60 seconds");
	}

}
