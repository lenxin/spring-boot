package smoketest.websocket.tomcat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import smoketest.websocket.tomcat.client.GreetingService;
import smoketest.websocket.tomcat.client.SimpleClientWebSocketHandler;
import smoketest.websocket.tomcat.client.SimpleGreetingService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = SampleTomcatWebSocketApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class SampleWebSocketsApplicationTests {

	private static Log logger = LogFactory.getLog(SampleWebSocketsApplicationTests.class);

	@LocalServerPort
	private int port = 1234;

	@Test
	void echoEndpoint() {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(ClientConfiguration.class,
				PropertyPlaceholderAutoConfiguration.class)
						.properties("websocket.uri:ws://localhost:" + this.port + "/echo/websocket")
						.run("--spring.main.web-application-type=none");
		long count = context.getBean(ClientConfiguration.class).latch.getCount();
		AtomicReference<String> messagePayloadReference = context.getBean(ClientConfiguration.class).messagePayload;
		context.close();
		assertThat(count).isEqualTo(0);
		assertThat(messagePayloadReference.get()).isEqualTo("Did you say \"Hello world!\"?");
	}

	@Test
	void reverseEndpoint() {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(ClientConfiguration.class,
				PropertyPlaceholderAutoConfiguration.class)
						.properties("websocket.uri:ws://localhost:" + this.port + "/reverse")
						.run("--spring.main.web-application-type=none");
		long count = context.getBean(ClientConfiguration.class).latch.getCount();
		AtomicReference<String> messagePayloadReference = context.getBean(ClientConfiguration.class).messagePayload;
		context.close();
		assertThat(count).isEqualTo(0);
		assertThat(messagePayloadReference.get()).isEqualTo("Reversed: !dlrow olleH");
	}

	@Configuration(proxyBeanMethods = false)
	static class ClientConfiguration implements CommandLineRunner {

		@Value("${websocket.uri}")
		private String webSocketUri;

		private final CountDownLatch latch = new CountDownLatch(1);

		private final AtomicReference<String> messagePayload = new AtomicReference<>();

		@Override
		public void run(String... args) throws Exception {
			logger.info("Waiting for response: latch=" + this.latch.getCount());
			if (this.latch.await(10, TimeUnit.SECONDS)) {
				logger.info("Got response: " + this.messagePayload.get());
			}
			else {
				logger.info("Response not received: latch=" + this.latch.getCount());
			}
		}

		@Bean
		WebSocketConnectionManager wsConnectionManager() {
			WebSocketConnectionManager manager = new WebSocketConnectionManager(client(), handler(), this.webSocketUri);
			manager.setAutoStartup(true);
			return manager;
		}

		@Bean
		StandardWebSocketClient client() {
			return new StandardWebSocketClient();
		}

		@Bean
		SimpleClientWebSocketHandler handler() {
			return new SimpleClientWebSocketHandler(greetingService(), this.latch, this.messagePayload);
		}

		@Bean
		GreetingService greetingService() {
			return new SimpleGreetingService();
		}

	}

}
