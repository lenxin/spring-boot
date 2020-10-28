package org.springframework.boot.actuate.management;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;

import org.springframework.boot.actuate.endpoint.web.test.WebEndpointTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.FileCopyUtils;

import static org.hamcrest.Matchers.is;

/**
 * Integration tests for {@link HeapDumpWebEndpoint} exposed by Jersey, Spring MVC, and
 * WebFlux.
 *


 */
class HeapDumpWebEndpointWebIntegrationTests {

	private TestHeapDumpWebEndpoint endpoint;

	@BeforeEach
	void configureEndpoint(ApplicationContext context) {
		this.endpoint = context.getBean(TestHeapDumpWebEndpoint.class);
		this.endpoint.setAvailable(true);
	}

	@WebEndpointTest
	void invokeWhenNotAvailableShouldReturnServiceUnavailableStatus(WebTestClient client) {
		this.endpoint.setAvailable(false);
		client.get().uri("/actuator/heapdump").exchange().expectStatus().isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
	}

	@WebEndpointTest
	void getRequestShouldReturnHeapDumpInResponseBody(WebTestClient client) throws Exception {
		client.get().uri("/actuator/heapdump").exchange().expectStatus().isOk().expectHeader()
				.contentType(MediaType.APPLICATION_OCTET_STREAM).expectBody(String.class).isEqualTo("HEAPDUMP");
		assertHeapDumpFileIsDeleted();
	}

	private void assertHeapDumpFileIsDeleted() throws InterruptedException {
		Awaitility.waitAtMost(Duration.ofSeconds(5)).until(this.endpoint.file::exists, is(false));
	}

	@Configuration(proxyBeanMethods = false)
	static class TestConfiguration {

		@Bean
		HeapDumpWebEndpoint endpoint() {
			return new TestHeapDumpWebEndpoint();
		}

	}

	static class TestHeapDumpWebEndpoint extends HeapDumpWebEndpoint {

		private boolean available;

		private String heapDump = "HEAPDUMP";

		private File file;

		TestHeapDumpWebEndpoint() {
			super(TimeUnit.SECONDS.toMillis(1));
			reset();
		}

		void reset() {
			this.available = true;
		}

		@Override
		protected HeapDumper createHeapDumper() {
			return (file, live) -> {
				this.file = file;
				if (!TestHeapDumpWebEndpoint.this.available) {
					throw new HeapDumperUnavailableException("Not available", null);
				}
				if (file.exists()) {
					throw new IOException("File exists");
				}
				FileCopyUtils.copy(TestHeapDumpWebEndpoint.this.heapDump.getBytes(), file);
			};
		}

		void setAvailable(boolean available) {
			this.available = available;
		}

	}

}
