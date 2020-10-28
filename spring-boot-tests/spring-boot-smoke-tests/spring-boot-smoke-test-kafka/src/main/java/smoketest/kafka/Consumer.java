package smoketest.kafka;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
class Consumer {

	private final List<SampleMessage> messages = new CopyOnWriteArrayList<>();

	@KafkaListener(topics = "testTopic")
	void processMessage(SampleMessage message) {
		this.messages.add(message);
		System.out.println("Received sample message [" + message + "]");
	}

	List<SampleMessage> getMessages() {
		return this.messages;
	}

}
