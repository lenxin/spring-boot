package smoketest.data.solr;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.core.NestedCheckedException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(OutputCaptureExtension.class)
class SampleSolrApplicationTests {

	@Test
	void testDefaultSettings(CapturedOutput output) throws Exception {
		try {
			SampleSolrApplication.main(new String[0]);
		}
		catch (IllegalStateException ex) {
			if (serverNotRunning(ex)) {
				return;
			}
		}
		assertThat(output).contains("name=Sony Playstation");
	}

	@SuppressWarnings("serial")
	private boolean serverNotRunning(IllegalStateException ex) {
		NestedCheckedException nested = new NestedCheckedException("failed", ex) {
		};
		Throwable root = nested.getRootCause();
		if (root.getMessage().contains("Connection refused")) {
			return true;
		}
		return false;
	}

}
