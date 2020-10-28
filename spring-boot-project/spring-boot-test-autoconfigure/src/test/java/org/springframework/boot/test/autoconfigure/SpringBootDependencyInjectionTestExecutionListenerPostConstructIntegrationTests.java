package org.springframework.boot.test.autoconfigure;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link SpringBootDependencyInjectionTestExecutionListener}.
 *

 */
@SpringBootTest
class SpringBootDependencyInjectionTestExecutionListenerPostConstructIntegrationTests {

	private List<String> calls = new ArrayList<>();

	@PostConstruct
	void postConstruct() {
		StringWriter writer = new StringWriter();
		new RuntimeException().printStackTrace(new PrintWriter(writer));
		this.calls.add(writer.toString());
	}

	@Test
	void postConstructShouldBeInvokedOnlyOnce() {
		// gh-6874
		assertThat(this.calls).hasSize(1);
	}

	@Configuration(proxyBeanMethods = false)
	static class Config {

	}

}
