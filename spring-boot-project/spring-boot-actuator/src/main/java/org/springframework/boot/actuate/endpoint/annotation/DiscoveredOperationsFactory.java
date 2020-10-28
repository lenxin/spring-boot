package org.springframework.boot.actuate.endpoint.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.boot.actuate.endpoint.EndpointId;
import org.springframework.boot.actuate.endpoint.Operation;
import org.springframework.boot.actuate.endpoint.OperationType;
import org.springframework.boot.actuate.endpoint.invoke.OperationInvoker;
import org.springframework.boot.actuate.endpoint.invoke.OperationInvokerAdvisor;
import org.springframework.boot.actuate.endpoint.invoke.ParameterValueMapper;
import org.springframework.boot.actuate.endpoint.invoke.reflect.OperationMethod;
import org.springframework.boot.actuate.endpoint.invoke.reflect.ReflectiveOperationInvoker;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.MethodIntrospector.MetadataLookup;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;

/**
 * Factory to create an {@link Operation} for annotated methods on an
 * {@link Endpoint @Endpoint} or {@link EndpointExtension @EndpointExtension}.
 *
 * @param <O> the operation type



 */
abstract class DiscoveredOperationsFactory<O extends Operation> {

	private static final Map<OperationType, Class<? extends Annotation>> OPERATION_TYPES;

	static {
		Map<OperationType, Class<? extends Annotation>> operationTypes = new EnumMap<>(OperationType.class);
		operationTypes.put(OperationType.READ, ReadOperation.class);
		operationTypes.put(OperationType.WRITE, WriteOperation.class);
		operationTypes.put(OperationType.DELETE, DeleteOperation.class);
		OPERATION_TYPES = Collections.unmodifiableMap(operationTypes);
	}

	private final ParameterValueMapper parameterValueMapper;

	private final Collection<OperationInvokerAdvisor> invokerAdvisors;

	DiscoveredOperationsFactory(ParameterValueMapper parameterValueMapper,
			Collection<OperationInvokerAdvisor> invokerAdvisors) {
		this.parameterValueMapper = parameterValueMapper;
		this.invokerAdvisors = invokerAdvisors;
	}

	Collection<O> createOperations(EndpointId id, Object target) {
		return MethodIntrospector
				.selectMethods(target.getClass(), (MetadataLookup<O>) (method) -> createOperation(id, target, method))
				.values();
	}

	private O createOperation(EndpointId endpointId, Object target, Method method) {
		return OPERATION_TYPES.entrySet().stream()
				.map((entry) -> createOperation(endpointId, target, method, entry.getKey(), entry.getValue()))
				.filter(Objects::nonNull).findFirst().orElse(null);
	}

	private O createOperation(EndpointId endpointId, Object target, Method method, OperationType operationType,
			Class<? extends Annotation> annotationType) {
		MergedAnnotation<?> annotation = MergedAnnotations.from(method).get(annotationType);
		if (!annotation.isPresent()) {
			return null;
		}
		DiscoveredOperationMethod operationMethod = new DiscoveredOperationMethod(method, operationType,
				annotation.asAnnotationAttributes());
		OperationInvoker invoker = new ReflectiveOperationInvoker(target, operationMethod, this.parameterValueMapper);
		invoker = applyAdvisors(endpointId, operationMethod, invoker);
		return createOperation(endpointId, operationMethod, invoker);
	}

	private OperationInvoker applyAdvisors(EndpointId endpointId, OperationMethod operationMethod,
			OperationInvoker invoker) {
		if (this.invokerAdvisors != null) {
			for (OperationInvokerAdvisor advisor : this.invokerAdvisors) {
				invoker = advisor.apply(endpointId, operationMethod.getOperationType(), operationMethod.getParameters(),
						invoker);
			}
		}
		return invoker;
	}

	protected abstract O createOperation(EndpointId endpointId, DiscoveredOperationMethod operationMethod,
			OperationInvoker invoker);

}
