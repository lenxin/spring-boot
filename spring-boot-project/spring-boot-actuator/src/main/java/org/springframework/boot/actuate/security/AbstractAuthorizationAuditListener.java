package org.springframework.boot.actuate.security;

import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.security.access.event.AbstractAuthorizationEvent;

/**
 * Abstract {@link ApplicationListener} to expose Spring Security
 * {@link AbstractAuthorizationEvent authorization events} as {@link AuditEvent}s.
 *


 * @since 1.3.0
 */
public abstract class AbstractAuthorizationAuditListener
		implements ApplicationListener<AbstractAuthorizationEvent>, ApplicationEventPublisherAware {

	private ApplicationEventPublisher publisher;

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	protected ApplicationEventPublisher getPublisher() {
		return this.publisher;
	}

	protected void publish(AuditEvent event) {
		if (getPublisher() != null) {
			getPublisher().publishEvent(new AuditApplicationEvent(event));
		}
	}

}
