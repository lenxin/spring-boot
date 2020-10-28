package org.springframework.boot.actuate.endpoint;

import java.util.Map;

import org.springframework.boot.actuate.endpoint.http.ApiVersion;
import org.springframework.boot.actuate.endpoint.invoke.OperationInvoker;
import org.springframework.util.Assert;

/**
 * The context for the {@link OperationInvoker invocation of an operation}.
 *


 * @since 2.0.0
 */
public class InvocationContext {

	private final SecurityContext securityContext;

	private final Map<String, Object> arguments;

	private final ApiVersion apiVersion;

	/**
	 * Creates a new context for an operation being invoked by the given
	 * {@code securityContext} with the given available {@code arguments}.
	 * @param securityContext the current security context. Never {@code null}
	 * @param arguments the arguments available to the operation. Never {@code null}
	 */
	public InvocationContext(SecurityContext securityContext, Map<String, Object> arguments) {
		this(null, securityContext, arguments);
	}

	/**
	 * Creates a new context for an operation being invoked by the given
	 * {@code securityContext} with the given available {@code arguments}.
	 * @param apiVersion the API version or {@code null} to use the latest
	 * @param securityContext the current security context. Never {@code null}
	 * @param arguments the arguments available to the operation. Never {@code null}
	 * @since 2.2.0
	 */
	public InvocationContext(ApiVersion apiVersion, SecurityContext securityContext, Map<String, Object> arguments) {
		Assert.notNull(securityContext, "SecurityContext must not be null");
		Assert.notNull(arguments, "Arguments must not be null");
		this.apiVersion = (apiVersion != null) ? apiVersion : ApiVersion.LATEST;
		this.securityContext = securityContext;
		this.arguments = arguments;
	}

	/**
	 * Return the API version in use.
	 * @return the apiVersion the API version
	 * @since 2.2.0
	 */
	public ApiVersion getApiVersion() {
		return this.apiVersion;
	}

	/**
	 * Return the security context to use for the invocation.
	 * @return the security context
	 */
	public SecurityContext getSecurityContext() {
		return this.securityContext;
	}

	/**
	 * Return the invocation arguments.
	 * @return the arguments
	 */
	public Map<String, Object> getArguments() {
		return this.arguments;
	}

}
