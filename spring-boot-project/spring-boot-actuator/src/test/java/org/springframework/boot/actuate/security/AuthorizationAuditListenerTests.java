package org.springframework.boot.actuate.security;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.event.AbstractAuthorizationEvent;
import org.springframework.security.access.event.AuthenticationCredentialsNotFoundEvent;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link AuthorizationAuditListener}.
 */
class AuthorizationAuditListenerTests {

	private final AuthorizationAuditListener listener = new AuthorizationAuditListener();

	private final ApplicationEventPublisher publisher = mock(ApplicationEventPublisher.class);

	@BeforeEach
	void init() {
		this.listener.setApplicationEventPublisher(this.publisher);
	}

	@Test
	void testAuthenticationCredentialsNotFound() {
		AuditApplicationEvent event = handleAuthorizationEvent(
				new AuthenticationCredentialsNotFoundEvent(this, Collections.singletonList(new SecurityConfig("USER")),
						new AuthenticationCredentialsNotFoundException("Bad user")));
		assertThat(event.getAuditEvent().getType()).isEqualTo(AuthenticationAuditListener.AUTHENTICATION_FAILURE);
	}

	@Test
	void testAuthorizationFailure() {
		AuditApplicationEvent event = handleAuthorizationEvent(new AuthorizationFailureEvent(this,
				Collections.singletonList(new SecurityConfig("USER")),
				new UsernamePasswordAuthenticationToken("user", "password"), new AccessDeniedException("Bad user")));
		assertThat(event.getAuditEvent().getType()).isEqualTo(AuthorizationAuditListener.AUTHORIZATION_FAILURE);
	}

	@Test
	void testDetailsAreIncludedInAuditEvent() {
		Object details = new Object();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("user",
				"password");
		authentication.setDetails(details);
		AuditApplicationEvent event = handleAuthorizationEvent(
				new AuthorizationFailureEvent(this, Collections.singletonList(new SecurityConfig("USER")),
						authentication, new AccessDeniedException("Bad user")));
		assertThat(event.getAuditEvent().getType()).isEqualTo(AuthorizationAuditListener.AUTHORIZATION_FAILURE);
		assertThat(event.getAuditEvent().getData()).containsEntry("details", details);
	}

	private AuditApplicationEvent handleAuthorizationEvent(AbstractAuthorizationEvent event) {
		ArgumentCaptor<AuditApplicationEvent> eventCaptor = ArgumentCaptor.forClass(AuditApplicationEvent.class);
		this.listener.onApplicationEvent(event);
		verify(this.publisher).publishEvent(eventCaptor.capture());
		return eventCaptor.getValue();
	}

}
