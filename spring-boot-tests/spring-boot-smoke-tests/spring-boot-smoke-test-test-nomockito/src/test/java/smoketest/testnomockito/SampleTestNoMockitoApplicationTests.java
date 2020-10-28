package smoketest.testnomockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests that {@code ResetMocksTestExecutionListener} and
 * {@code MockitoTestExecutionListener} gracefully degrade when Mockito is not on the
 * classpath.
 *

 */
@ExtendWith(SpringExtension.class)
class SampleTestNoMockitoApplicationTests {

	// gh-7065

	@Autowired
	private ApplicationContext context;

	@Test
	void contextLoads() throws Exception {
		assertThat(this.context).isNotNull();
	}

}
