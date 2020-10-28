package org.springframework.boot.context.properties.source;

import org.springframework.util.Assert;

/**
 * Exception thrown when a configuration property value is invalid.
 *

 * @since 2.0.0
 */
@SuppressWarnings("serial")
public class InvalidConfigurationPropertyValueException extends RuntimeException {

	private final String name;

	private final Object value;

	private final String reason;

	/**
	 * Creates a new instance for the specified property {@code name} and {@code value},
	 * including a {@code reason} why the value is invalid.
	 * @param name the name of the property in canonical format
	 * @param value the value of the property, can be {@code null}
	 * @param reason a human-readable text that describes why the reason is invalid.
	 * Starts with an upper-case and ends with a dot. Several sentences and carriage
	 * returns are allowed.
	 */
	public InvalidConfigurationPropertyValueException(String name, Object value, String reason) {
		super("Property " + name + " with value '" + value + "' is invalid: " + reason);
		Assert.notNull(name, "Name must not be null");
		this.name = name;
		this.value = value;
		this.reason = reason;
	}

	/**
	 * Return the name of the property.
	 * @return the property name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Return the invalid value, can be {@code null}.
	 * @return the invalid value
	 */
	public Object getValue() {
		return this.value;
	}

	/**
	 * Return the reason why the value is invalid.
	 * @return the reason
	 */
	public String getReason() {
		return this.reason;
	}

}
