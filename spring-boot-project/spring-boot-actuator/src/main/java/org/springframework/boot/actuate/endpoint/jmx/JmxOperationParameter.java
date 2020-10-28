package org.springframework.boot.actuate.endpoint.jmx;

/**
 * Describes the parameters of an operation on a JMX endpoint.
 *


 * @since 2.0.0
 */
public interface JmxOperationParameter {

	/**
	 * Return the name of the operation parameter.
	 * @return the name of the parameter
	 */
	String getName();

	/**
	 * Return the type of the operation parameter.
	 * @return the type
	 */
	Class<?> getType();

	/**
	 * Return the description of the parameter or {@code null} if none is available.
	 * @return the description or {@code null}
	 */
	String getDescription();

}
