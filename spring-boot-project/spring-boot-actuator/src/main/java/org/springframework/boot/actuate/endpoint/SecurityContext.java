package org.springframework.boot.actuate.endpoint;

import java.security.Principal;

/**
 * Security context in which an endpoint is being invoked.
 *

 * @since 2.0.0
 */
public interface SecurityContext {

	/**
	 * Empty security context.
	 */
	SecurityContext NONE = new SecurityContext() {

		@Override
		public Principal getPrincipal() {
			return null;
		}

		@Override
		public boolean isUserInRole(String role) {
			return false;
		}

	};

	/**
	 * Return the currently authenticated {@link Principal} or {@code null}.
	 * @return the principal or {@code null}
	 */
	Principal getPrincipal();

	/**
	 * Returns {@code true} if the currently authenticated user is in the given
	 * {@code role}, or false otherwise.
	 * @param role name of the role
	 * @return {@code true} if the user is in the given role
	 */
	boolean isUserInRole(String role);

}
