package smoketest.bitronix;

import java.util.function.Consumer;

import bitronix.tm.resource.jms.PoolingConnectionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Basic integration tests for demo application.
 *

 */
@ExtendWith(OutputCaptureExtension.class)
class SampleBitronixApplicationTests {

	@Test
	void testTransactionRollback(CapturedOutput output) throws Exception {
		SampleBitronixApplication.main(new String[] {});
		assertThat(output).satisfies(numberOfOccurrences("---->", 1));
		assertThat(output).satisfies(numberOfOccurrences("----> josh", 1));
		assertThat(output).satisfies(numberOfOccurrences("Count is 1", 2));
		assertThat(output).satisfies(numberOfOccurrences("Simulated error", 1));
	}

	@Test
	void testExposesXaAndNonXa() {
		ApplicationContext context = SpringApplication.run(SampleBitronixApplication.class);
		Object jmsConnectionFactory = context.getBean("jmsConnectionFactory");
		Object xaJmsConnectionFactory = context.getBean("xaJmsConnectionFactory");
		Object nonXaJmsConnectionFactory = context.getBean("nonXaJmsConnectionFactory");
		assertThat(jmsConnectionFactory).isSameAs(xaJmsConnectionFactory);
		assertThat(jmsConnectionFactory).isInstanceOf(PoolingConnectionFactory.class);
		assertThat(nonXaJmsConnectionFactory).isNotInstanceOf(PoolingConnectionFactory.class);
	}

	private <T extends CharSequence> Consumer<T> numberOfOccurrences(String substring, int expectedCount) {
		return (charSequence) -> {
			int count = StringUtils.countOccurrencesOf(charSequence.toString(), substring);
			assertThat(count).isEqualTo(expectedCount);
		};
	}

}
