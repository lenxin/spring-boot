package smoketest.data.redis;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.data.redis.RedisConnectionFailureException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SampleRedisApplication}.
 *

 */
@ExtendWith(OutputCaptureExtension.class)
class SampleRedisApplicationTests {

	@Test
	void testDefaultSettings(CapturedOutput output) {
		try {
			SampleRedisApplication.main(new String[0]);
		}
		catch (Exception ex) {
			if (!redisServerRunning(ex)) {
				return;
			}
		}
		assertThat(output).contains("Found key spring.boot.redis.test");
	}

	private boolean redisServerRunning(Throwable ex) {
		System.out.println(ex.getMessage());
		if (ex instanceof RedisConnectionFailureException) {
			return false;
		}
		return (ex.getCause() == null || redisServerRunning(ex.getCause()));
	}

}
