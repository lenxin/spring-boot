package org.springframework.boot.web.error;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Options controlling the contents of {@code ErrorAttributes}.
 *


 * @since 2.3.0
 */
public final class ErrorAttributeOptions {

	private final Set<Include> includes;

	private ErrorAttributeOptions(Set<Include> includes) {
		this.includes = includes;
	}

	/**
	 * Get the option for including the specified attribute in the error response.
	 * @param include error attribute to get
	 * @return {@code true} if the {@code Include} attribute is included in the error
	 * response, {@code false} otherwise
	 */
	public boolean isIncluded(Include include) {
		return this.includes.contains(include);
	}

	/**
	 * Get all options for including attributes in the error response.
	 * @return {@code true} if the {@code Include} attribute is included in the error
	 * response, {@code false} otherwise
	 */
	public Set<Include> getIncludes() {
		return this.includes;
	}

	/**
	 * Return an {@code ErrorAttributeOptions} that includes the specified attribute
	 * {@link Include} options.
	 * @param includes error attributes to include
	 * @return an {@code ErrorAttributeOptions}
	 */
	public ErrorAttributeOptions including(Include... includes) {
		EnumSet<Include> updated = (this.includes.isEmpty()) ? EnumSet.noneOf(Include.class)
				: EnumSet.copyOf(this.includes);
		updated.addAll(Arrays.asList(includes));
		return new ErrorAttributeOptions(Collections.unmodifiableSet(updated));
	}

	/**
	 * Return an {@code ErrorAttributeOptions} that excludes the specified attribute
	 * {@link Include} options.
	 * @param excludes error attributes to exclude
	 * @return an {@code ErrorAttributeOptions}
	 */
	public ErrorAttributeOptions excluding(Include... excludes) {
		EnumSet<Include> updated = EnumSet.copyOf(this.includes);
		updated.removeAll(Arrays.asList(excludes));
		return new ErrorAttributeOptions(Collections.unmodifiableSet(updated));
	}

	/**
	 * Create an {@code ErrorAttributeOptions} with defaults.
	 * @return an {@code ErrorAttributeOptions}
	 */
	public static ErrorAttributeOptions defaults() {
		return of();
	}

	/**
	 * Create an {@code ErrorAttributeOptions} that includes the specified attribute
	 * {@link Include} options.
	 * @param includes error attributes to include
	 * @return an {@code ErrorAttributeOptions}
	 */
	public static ErrorAttributeOptions of(Include... includes) {
		return of(Arrays.asList(includes));
	}

	/**
	 * Create an {@code ErrorAttributeOptions} that includes the specified attribute
	 * {@link Include} options.
	 * @param includes error attributes to include
	 * @return an {@code ErrorAttributeOptions}
	 */
	public static ErrorAttributeOptions of(Collection<Include> includes) {
		return new ErrorAttributeOptions(
				(includes.isEmpty()) ? Collections.emptySet() : Collections.unmodifiableSet(EnumSet.copyOf(includes)));
	}

	/**
	 * Error attributes that can be included in an error response.
	 */
	public enum Include {

		/**
		 * Include the exception class name attribute.
		 */
		EXCEPTION,

		/**
		 * Include the stack trace attribute.
		 */
		STACK_TRACE,

		/**
		 * Include the message attribute.
		 */
		MESSAGE,

		/**
		 * Include the binding errors attribute.
		 */
		BINDING_ERRORS

	}

}
