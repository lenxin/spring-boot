package smoketest.profile;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(OutputCaptureExtension.class)
class SampleProfileApplicationTests {

	private String profiles;

	@BeforeEach
	void before() {
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
	void testDefaultProfile(CapturedOutput output) {
		SampleProfileApplication.main(new String[0]);
		assertThat(output).contains("Hello Phil");
	}

	@Test
	void testGoodbyeProfile(CapturedOutput output) {
		System.setProperty("spring.profiles.active", "goodbye");
		SampleProfileApplication.main(new String[0]);
		assertThat(output).contains("Goodbye Everyone");
	}

	@Test
	void testGenericProfile(CapturedOutput output) {
		/*
		 * This is a profile that requires a new environment property, and one which is
		 * only overridden in the current working directory. That file also only contains
		 * partial overrides, and the default application.yml should still supply the
		 * "name" property.
		 */
		System.setProperty("spring.profiles.active", "generic");
		SampleProfileApplication.main(new String[0]);
		assertThat(output).contains("Bonjour Phil");
	}

	@Test
	void testGoodbyeProfileFromCommandline(CapturedOutput output) {
		SampleProfileApplication.main(new String[] { "--spring.profiles.active=goodbye" });
		assertThat(output).contains("Goodbye Everyone");
	}

}
