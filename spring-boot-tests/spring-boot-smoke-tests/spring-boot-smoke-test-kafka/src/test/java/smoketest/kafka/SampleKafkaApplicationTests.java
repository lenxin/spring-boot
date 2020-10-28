package smoketest.kafka;

import java.time.Duration;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

/**
 * Integration tests for demo application.
 *



 */
@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
@EmbeddedKafka(topics = "testTopic")
class SampleKafkaApplicationTests {

	@Autowired
	private Consumer consumer;

	@Test
	void testVanillaExchange() throws Exception {
		Awaitility.waitAtMost(Duration.ofSeconds(30)).until(this.consumer::getMessages, not(empty()));
		assertThat(this.consumer.getMessages()).extracting("message").containsOnly("A simple test message");
	}

}
