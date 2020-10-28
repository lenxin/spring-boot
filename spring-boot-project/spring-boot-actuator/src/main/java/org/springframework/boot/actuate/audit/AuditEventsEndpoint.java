package org.springframework.boot.actuate.audit;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * {@link Endpoint @Endpoint} to expose audit events.
 *

 * @since 2.0.0
 */
@Endpoint(id = "auditevents")
public class AuditEventsEndpoint {

	private final AuditEventRepository auditEventRepository;

	public AuditEventsEndpoint(AuditEventRepository auditEventRepository) {
		Assert.notNull(auditEventRepository, "AuditEventRepository must not be null");
		this.auditEventRepository = auditEventRepository;
	}

	@ReadOperation
	public AuditEventsDescriptor events(@Nullable String principal, @Nullable OffsetDateTime after,
			@Nullable String type) {
		List<AuditEvent> events = this.auditEventRepository.find(principal, getInstant(after), type);
		return new AuditEventsDescriptor(events);
	}

	private Instant getInstant(OffsetDateTime offsetDateTime) {
		return (offsetDateTime != null) ? offsetDateTime.toInstant() : null;
	}

	/**
	 * A description of an application's {@link AuditEvent audit events}. Primarily
	 * intended for serialization to JSON.
	 */
	public static final class AuditEventsDescriptor {

		private final List<AuditEvent> events;

		private AuditEventsDescriptor(List<AuditEvent> events) {
			this.events = events;
		}

		public List<AuditEvent> getEvents() {
			return this.events;
		}

	}

}
