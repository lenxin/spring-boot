package smoketest.quartz;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.ConfigurableApplicationContext;

import static org.hamcrest.Matchers.containsString;

/**
 * Tests for {@link SampleQuartzApplication}.
 *

 */
@ExtendWith(OutputCaptureExtension.class)
class SampleQuartzApplicationTests {

	@Test
	void quartzJobIsTriggered(CapturedOutput output) throws InterruptedException {
		try (ConfigurableApplicationContext context = SpringApplication.run(SampleQuartzApplication.class)) {
			Awaitility.waitAtMost(Duration.ofSeconds(5)).until(output::toString, containsString("Hello World!"));
		}
	}

}
