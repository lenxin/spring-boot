package org.springframework.boot.actuate.startup;

import org.junit.jupiter.api.Test;

import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link StartupEndpoint}.
 *

 */
class StartupEndpointTests {

	@Test
	void startupEventsAreFound() {
		BufferingApplicationStartup applicationStartup = new BufferingApplicationStartup(256);
		ApplicationContextRunner contextRunner = new ApplicationContextRunner()
				.withInitializer((context) -> context.setApplicationStartup(applicationStartup))
				.withUserConfiguration(EndpointConfiguration.class);
		contextRunner.run((context) -> {
			StartupEndpoint.StartupResponse startup = context.getBean(StartupEndpoint.class).startup();
			assertThat(startup.getSpringBootVersion()).isEqualTo(SpringBootVersion.getVersion());
			assertThat(startup.getTimeline().getStartTime())
					.isEqualTo(applicationStartup.getBufferedTimeline().getStartTime());
		});
	}

	@Test
	void bufferIsDrained() {
		BufferingApplicationStartup applicationStartup = new BufferingApplicationStartup(256);
		ApplicationContextRunner contextRunner = new ApplicationContextRunner()
				.withInitializer((context) -> context.setApplicationStartup(applicationStartup))
				.withUserConfiguration(EndpointConfiguration.class);
		contextRunner.run((context) -> {
			StartupEndpoint.StartupResponse startup = context.getBean(StartupEndpoint.class).startup();
			assertThat(startup.getTimeline().getEvents()).isNotEmpty();
			assertThat(applicationStartup.getBufferedTimeline().getEvents()).isEmpty();
		});
	}

	@Configuration(proxyBeanMethods = false)
	static class EndpointConfiguration {

		@Bean
		StartupEndpoint endpoint(BufferingApplicationStartup applicationStartup) {
			return new StartupEndpoint(applicationStartup);
		}

	}

}
