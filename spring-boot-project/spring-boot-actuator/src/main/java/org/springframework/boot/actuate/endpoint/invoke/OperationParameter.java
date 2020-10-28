package org.springframework.boot.actuate.endpoint.invoke;

/**
 * A single operation parameter.
 *

 * @since 2.0.0
 */
public interface OperationParameter {

	/**
	 * Returns the parameter name.
	 * @return the name
	 */
	String getName();

	/**
	 * Returns the parameter type.
	 * @return the type
	 */
	Class<?> getType();

	/**
	 * Return if the parameter is mandatory (does not accept null values).
	 * @return if the parameter is mandatory
	 */
	boolean isMandatory();

}
