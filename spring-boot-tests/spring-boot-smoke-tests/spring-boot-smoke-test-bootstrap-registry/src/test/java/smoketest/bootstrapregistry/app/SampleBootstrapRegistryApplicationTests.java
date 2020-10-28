package smoketest.bootstrapregistry.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SampleBootstrapRegistryApplication}.
 *

 */
@ExtendWith(OutputCaptureExtension.class)
class SampleBootstrapRegistryApplicationTests {

	@Test
	void testBootrapper(CapturedOutput output) {
		SampleBootstrapRegistryApplication.main(new String[0]);
		assertThat(output).contains("svn my-data from svn / example.com[secret]")
				.contains("client smoketest.bootstrapregistry.app.MySubversionClient");
	}

}
