package org.springframework.boot.autoconfigure.flyway;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.callback.Context;
import org.flywaydb.core.api.callback.Event;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDataSourceConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.testsupport.classpath.ClassPathOverrides;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link FlywayAutoConfiguration} with Flyway 6.x.
 *

 */
@ClassPathOverrides("org.flywaydb:flyway-core:6.5.6")
class Flyway6xAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(FlywayAutoConfiguration.class))
			.withPropertyValues("spring.datasource.generate-unique-name=true");

	@Test
	void defaultFlyway() {
		this.contextRunner.withUserConfiguration(EmbeddedDataSourceConfiguration.class).run((context) -> {
			assertThat(context).hasSingleBean(Flyway.class);
			Flyway flyway = context.getBean(Flyway.class);
			assertThat(flyway.getConfiguration().getLocations())
					.containsExactly(new Location("classpath:db/migration"));
		});
	}

	@Test
	void callbacksAreConfiguredAndOrdered() {
		this.contextRunner.withUserConfiguration(EmbeddedDataSourceConfiguration.class, CallbackConfiguration.class)
				.run((context) -> {
					assertThat(context).hasSingleBean(Flyway.class);
					Flyway flyway = context.getBean(Flyway.class);
					Callback callbackOne = context.getBean("callbackOne", Callback.class);
					Callback callbackTwo = context.getBean("callbackTwo", Callback.class);
					assertThat(flyway.getConfiguration().getCallbacks()).hasSize(2);
					assertThat(flyway.getConfiguration().getCallbacks()).containsExactly(callbackTwo, callbackOne);
					InOrder orderedCallbacks = inOrder(callbackOne, callbackTwo);
					orderedCallbacks.verify(callbackTwo).handle(any(Event.class), any(Context.class));
					orderedCallbacks.verify(callbackOne).handle(any(Event.class), any(Context.class));
				});
	}

	@Configuration(proxyBeanMethods = false)
	static class CallbackConfiguration {

		@Bean
		@Order(1)
		Callback callbackOne() {
			return mockCallback();
		}

		@Bean
		@Order(0)
		Callback callbackTwo() {
			return mockCallback();
		}

		private Callback mockCallback() {
			Callback callback = mock(Callback.class);
			given(callback.supports(any(Event.class), any(Context.class))).willReturn(true);
			return callback;
		}

	}

}
