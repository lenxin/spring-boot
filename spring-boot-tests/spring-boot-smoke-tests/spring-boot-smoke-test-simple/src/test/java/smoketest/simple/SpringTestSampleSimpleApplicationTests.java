package smoketest.simple;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SampleSimpleApplication}.
 *

 */
@SpringBootTest
class SpringTestSampleSimpleApplicationTests {

	@Autowired
	ApplicationContext ctx;

	@Test
	void testContextLoads() {
		assertThat(this.ctx).isNotNull();
		assertThat(this.ctx.containsBean("helloWorldService")).isTrue();
		assertThat(this.ctx.containsBean("sampleSimpleApplication")).isTrue();
	}

}
