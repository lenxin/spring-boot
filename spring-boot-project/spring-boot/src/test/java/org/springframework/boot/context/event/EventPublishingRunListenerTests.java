package org.springframework.boot.context.event;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.DefaultBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.StaticApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link EventPublishingRunListener}
 *

 */
class EventPublishingRunListenerTests {

	private DefaultBootstrapContext bootstrapContext = new DefaultBootstrapContext();

	private SpringApplication application;

	private EventPublishingRunListener runListener;

	private TestApplicationListener eventListener;

	@BeforeEach
	void setup() {
		this.eventListener = new TestApplicationListener();
		this.application = mock(SpringApplication.class);
		given(this.application.getListeners()).willReturn(Collections.singleton(this.eventListener));
		this.runListener = new EventPublishingRunListener(this.application, null);
	}

	@Test
	void shouldPublishLifecycleEvents() {
		StaticApplicationContext context = new StaticApplicationContext();
		assertThat(this.eventListener.receivedEvents()).isEmpty();
		this.runListener.starting(this.bootstrapContext);
		checkApplicationEvents(ApplicationStartingEvent.class);
		this.runListener.environmentPrepared(this.bootstrapContext, null);
		checkApplicationEvents(ApplicationEnvironmentPreparedEvent.class);
		this.runListener.contextPrepared(context);
		checkApplicationEvents(ApplicationContextInitializedEvent.class);
		this.runListener.contextLoaded(context);
		checkApplicationEvents(ApplicationPreparedEvent.class);
		context.refresh();
		this.runListener.started(context);
		checkApplicationEvents(ApplicationStartedEvent.class, AvailabilityChangeEvent.class);
		this.runListener.running(context);
		checkApplicationEvents(ApplicationReadyEvent.class, AvailabilityChangeEvent.class);
	}

	void checkApplicationEvents(Class<?>... eventClasses) {
		assertThat(this.eventListener.receivedEvents()).extracting("class").contains((Object[]) eventClasses);
	}

	static class TestApplicationListener implements ApplicationListener<ApplicationEvent> {

		private Deque<ApplicationEvent> events = new ArrayDeque<>();

		@Override
		public void onApplicationEvent(ApplicationEvent event) {
			this.events.add(event);
		}

		List<ApplicationEvent> receivedEvents() {
			List<ApplicationEvent> receivedEvents = new ArrayList<>();
			while (!this.events.isEmpty()) {
				receivedEvents.add(this.events.pollFirst());
			}
			return receivedEvents;
		}

	}

}
