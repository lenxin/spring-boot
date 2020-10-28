package org.springframework.boot.actuate.endpoint.invoke;

/**
 * Maps parameter values to the required type when invoking an endpoint.
 *

 * @since 2.0.0
 */
@FunctionalInterface
public interface ParameterValueMapper {

	/**
	 * A {@link ParameterValueMapper} that does nothing.
	 */
	ParameterValueMapper NONE = (parameter, value) -> value;

	/**
	 * Map the specified {@code input} parameter to the given {@code parameterType}.
	 * @param parameter the parameter to map
	 * @param value a parameter value
	 * @return a value suitable for that parameter
	 * @throws ParameterMappingException when a mapping failure occurs
	 */
	Object mapParameterValue(OperationParameter parameter, Object value) throws ParameterMappingException;

}
