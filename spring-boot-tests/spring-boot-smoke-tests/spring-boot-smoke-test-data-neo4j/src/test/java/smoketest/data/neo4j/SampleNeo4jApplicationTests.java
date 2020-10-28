package smoketest.data.neo4j;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.neo4j.driver.exceptions.ServiceUnavailableException;

import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SampleNeo4jApplication}.
 *

 */
@ExtendWith(OutputCaptureExtension.class)
class SampleNeo4jApplicationTests {

	@Test
	void testDefaultSettings(CapturedOutput output) {
		try {
			SampleNeo4jApplication.main(new String[0]);
		}
		catch (Exception ex) {
			if (!neo4jServerRunning(ex)) {
				return;
			}
		}
		assertThat(output).contains("firstName='Alice', lastName='Smith'");
	}

	private boolean neo4jServerRunning(Throwable ex) {
		if (ex instanceof ServiceUnavailableException) {
			return false;
		}
		return (ex.getCause() == null || neo4jServerRunning(ex.getCause()));
	}

}
