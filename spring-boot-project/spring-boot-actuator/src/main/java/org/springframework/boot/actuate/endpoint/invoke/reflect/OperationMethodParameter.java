package org.springframework.boot.actuate.endpoint.invoke.reflect;

import java.lang.reflect.Parameter;

import org.springframework.boot.actuate.endpoint.invoke.OperationParameter;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

/**
 * {@link OperationParameter} created from an {@link OperationMethod}.
 *

 */
class OperationMethodParameter implements OperationParameter {

	private final String name;

	private final Parameter parameter;

	/**
	 * Create a new {@link OperationMethodParameter} instance.
	 * @param name the parameter name
	 * @param parameter the parameter
	 */
	OperationMethodParameter(String name, Parameter parameter) {
		this.name = name;
		this.parameter = parameter;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Class<?> getType() {
		return this.parameter.getType();
	}

	@Override
	public boolean isMandatory() {
		return ObjectUtils.isEmpty(this.parameter.getAnnotationsByType(Nullable.class));
	}

	@Override
	public String toString() {
		return this.name + " of type " + this.parameter.getType().getName();
	}

}
