package smoketest.ant;

import java.io.File;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import org.springframework.boot.loader.tools.JavaExecutable;
import org.springframework.util.FileCopyUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration Tests for {@code SampleAntApplication}.
 *


 */
public class SampleAntApplicationIT {

	@Test
	void runJar() throws Exception {
		File libs = new File("build/ant/libs");
		Process process = new JavaExecutable().processBuilder("-jar", "spring-boot-smoke-test-ant.jar").directory(libs)
				.start();
		process.waitFor(5, TimeUnit.MINUTES);
		assertThat(process.exitValue()).isEqualTo(0);
		String output = FileCopyUtils.copyToString(new InputStreamReader(process.getInputStream()));
		assertThat(output).contains("Spring Boot Ant Example");
	}

}
