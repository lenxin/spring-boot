package org.springframework.boot.autoconfigure.session;

import org.springframework.session.SessionRepository;

/**
 * Exception thrown when no {@link SessionRepository} is available.
 *

 * @since 2.0.0
 */
public class SessionRepositoryUnavailableException extends RuntimeException {

	private final StoreType storeType;

	public SessionRepositoryUnavailableException(String message, StoreType storeType) {
		super(message);
		this.storeType = storeType;
	}

	public StoreType getStoreType() {
		return this.storeType;
	}

}
