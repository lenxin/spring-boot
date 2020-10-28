package org.springframework.boot.autoconfigure.web;

import org.springframework.beans.factory.annotation.Value;

/**
 * Configuration properties for web error handling.
 *




 * @since 1.3.0
 */
public class ErrorProperties {

	/**
	 * Path of the error controller.
	 */
	@Value("${error.path:/error}")
	private String path = "/error";

	/**
	 * Include the "exception" attribute.
	 */
	private boolean includeException;

	/**
	 * When to include the "trace" attribute.
	 */
	private IncludeStacktrace includeStacktrace = IncludeStacktrace.NEVER;

	/**
	 * When to include "message" attribute.
	 */
	private IncludeAttribute includeMessage = IncludeAttribute.NEVER;

	/**
	 * When to include "errors" attribute.
	 */
	private IncludeAttribute includeBindingErrors = IncludeAttribute.NEVER;

	private final Whitelabel whitelabel = new Whitelabel();

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isIncludeException() {
		return this.includeException;
	}

	public void setIncludeException(boolean includeException) {
		this.includeException = includeException;
	}

	public IncludeStacktrace getIncludeStacktrace() {
		return this.includeStacktrace;
	}

	public void setIncludeStacktrace(IncludeStacktrace includeStacktrace) {
		this.includeStacktrace = includeStacktrace;
	}

	public IncludeAttribute getIncludeMessage() {
		return this.includeMessage;
	}

	public void setIncludeMessage(IncludeAttribute includeMessage) {
		this.includeMessage = includeMessage;
	}

	public IncludeAttribute getIncludeBindingErrors() {
		return this.includeBindingErrors;
	}

	public void setIncludeBindingErrors(IncludeAttribute includeBindingErrors) {
		this.includeBindingErrors = includeBindingErrors;
	}

	public Whitelabel getWhitelabel() {
		return this.whitelabel;
	}

	/**
	 * Include Stacktrace attribute options.
	 */
	public enum IncludeStacktrace {

		/**
		 * Never add stacktrace information.
		 */
		NEVER,

		/**
		 * Always add stacktrace information.
		 */
		ALWAYS,

		/**
		 * Add error attribute when the appropriate request parameter is "true".
		 */
		ON_PARAM,

		/**
		 * Add stacktrace information when the "trace" request parameter is "true".
		 */
		@Deprecated // since 2.3.0 in favor of {@link #ON_PARAM}
		ON_TRACE_PARAM;

	}

	/**
	 * Include error attributes options.
	 */
	public enum IncludeAttribute {

		/**
		 * Never add error attribute.
		 */
		NEVER,

		/**
		 * Always add error attribute.
		 */
		ALWAYS,

		/**
		 * Add error attribute when the appropriate request parameter is "true".
		 */
		ON_PARAM

	}

	public static class Whitelabel {

		/**
		 * Whether to enable the default error page displayed in browsers in case of a
		 * server error.
		 */
		private boolean enabled = true;

		public boolean isEnabled() {
			return this.enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

	}

}
