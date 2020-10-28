package org.springframework.boot.actuate.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.security.access.event.AbstractAuthorizationEvent;
import org.springframework.security.access.event.AuthenticationCredentialsNotFoundEvent;
import org.springframework.security.access.event.AuthorizationFailureEvent;

/**
 * Default implementation of {@link AbstractAuthorizationAuditListener}.
 *


 * @since 1.0.0
 */
public class AuthorizationAuditListener extends AbstractAuthorizationAuditListener {

	/**
	 * Authorization failure event type.
	 */
	public static final String AUTHORIZATION_FAILURE = "AUTHORIZATION_FAILURE";

	@Override
	public void onApplicationEvent(AbstractAuthorizationEvent event) {
		if (event instanceof AuthenticationCredentialsNotFoundEvent) {
			onAuthenticationCredentialsNotFoundEvent((AuthenticationCredentialsNotFoundEvent) event);
		}
		else if (event instanceof AuthorizationFailureEvent) {
			onAuthorizationFailureEvent((AuthorizationFailureEvent) event);
		}
	}

	private void onAuthenticationCredentialsNotFoundEvent(AuthenticationCredentialsNotFoundEvent event) {
		Map<String, Object> data = new HashMap<>();
		data.put("type", event.getCredentialsNotFoundException().getClass().getName());
		data.put("message", event.getCredentialsNotFoundException().getMessage());
		publish(new AuditEvent("<unknown>", AuthenticationAuditListener.AUTHENTICATION_FAILURE, data));
	}

	private void onAuthorizationFailureEvent(AuthorizationFailureEvent event) {
		Map<String, Object> data = new HashMap<>();
		data.put("type", event.getAccessDeniedException().getClass().getName());
		data.put("message", event.getAccessDeniedException().getMessage());
		if (event.getAuthentication().getDetails() != null) {
			data.put("details", event.getAuthentication().getDetails());
		}
		publish(new AuditEvent(event.getAuthentication().getName(), AUTHORIZATION_FAILURE, data));
	}

}
