package smoketest.data.elasticsearch;

import java.net.ConnectException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SampleElasticsearchApplication}.
 *

 */
@ExtendWith(OutputCaptureExtension.class)
class SampleElasticsearchApplicationTests {

	@Test
	void testDefaultSettings(CapturedOutput output) {
		try {
			new SpringApplicationBuilder(SampleElasticsearchApplication.class).run();
		}
		catch (Exception ex) {
			if (!elasticsearchRunning(ex)) {
				return;
			}
			throw ex;
		}
		assertThat(output).contains("firstName='Alice', lastName='Smith'");
	}

	private boolean elasticsearchRunning(Exception ex) {
		Throwable candidate = ex;
		while (candidate != null) {
			if (candidate instanceof ConnectException) {
				return false;
			}
			candidate = candidate.getCause();
		}
		return true;
	}

}
