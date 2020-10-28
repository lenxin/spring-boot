package org.springframework.boot.devtools;

import ch.qos.logback.classic.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/**
 * Tests for {@link RemoteUrlPropertyExtractor}.
 *

 */
class RemoteUrlPropertyExtractorTests {

	@AfterEach
	void preventRunFailuresFromPollutingLoggerContext() {
		((Logger) LoggerFactory.getLogger(RemoteUrlPropertyExtractorTests.class)).getLoggerContext()
				.getTurboFilterList().clear();
	}

	@Test
	void missingUrl() {
		assertThatIllegalStateException().isThrownBy(this::doTest).withMessageContaining("No remote URL specified");
	}

	@Test
	void malformedUrl() {
		assertThatIllegalStateException().isThrownBy(() -> doTest("::://wibble"))
				.withMessageContaining("Malformed URL '::://wibble'");

	}

	@Test
	void multipleUrls() {
		assertThatIllegalStateException().isThrownBy(() -> doTest("http://localhost:8080", "http://localhost:9090"))
				.withMessageContaining("Multiple URLs specified");
	}

	@Test
	void validUrl() {
		ApplicationContext context = doTest("http://localhost:8080");
		assertThat(context.getEnvironment().getProperty("remoteUrl")).isEqualTo("http://localhost:8080");
		assertThat(context.getEnvironment().getProperty("spring.thymeleaf.cache")).isNull();
	}

	@Test
	void cleanValidUrl() {
		ApplicationContext context = doTest("http://localhost:8080/");
		assertThat(context.getEnvironment().getProperty("remoteUrl")).isEqualTo("http://localhost:8080");
	}

	private ApplicationContext doTest(String... args) {
		SpringApplication application = new SpringApplication(Config.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.addListeners(new RemoteUrlPropertyExtractor());
		return application.run(args);
	}

	@Configuration(proxyBeanMethods = false)
	static class Config {

	}

}
