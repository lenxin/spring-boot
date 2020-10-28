package org.springframework.boot.autoconfigure.session;

import java.util.Collections;

import org.springframework.boot.test.context.assertj.AssertableReactiveWebApplicationContext;
import org.springframework.boot.test.context.assertj.AssertableWebApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.ReactiveSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.SessionRepositoryFilter;
import org.springframework.web.server.session.WebSessionManager;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Shared test utilities for {@link SessionAutoConfiguration} tests.
 *

 */
public abstract class AbstractSessionAutoConfigurationTests {

	protected <T extends SessionRepository<?>> T validateSessionRepository(AssertableWebApplicationContext context,
			Class<T> type) {
		assertThat(context).hasSingleBean(SessionRepositoryFilter.class);
		assertThat(context).hasSingleBean(SessionRepository.class);
		SessionRepository<?> repository = context.getBean(SessionRepository.class);
		assertThat(repository).as("Wrong session repository type").isInstanceOf(type);
		return type.cast(repository);
	}

	protected <T extends ReactiveSessionRepository<?>> T validateSessionRepository(
			AssertableReactiveWebApplicationContext context, Class<T> type) {
		assertThat(context).hasSingleBean(WebSessionManager.class);
		assertThat(context).hasSingleBean(ReactiveSessionRepository.class);
		ReactiveSessionRepository<?> repository = context.getBean(ReactiveSessionRepository.class);
		assertThat(repository).as("Wrong session repository type").isInstanceOf(type);
		return type.cast(repository);
	}

	@Configuration
	@EnableSpringHttpSession
	static class SessionRepositoryConfiguration {

		@Bean
		MapSessionRepository mySessionRepository() {
			return new MapSessionRepository(Collections.emptyMap());
		}

	}

}
