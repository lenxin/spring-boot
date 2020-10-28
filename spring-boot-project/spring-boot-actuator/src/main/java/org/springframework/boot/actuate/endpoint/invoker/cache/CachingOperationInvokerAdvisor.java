package org.springframework.boot.actuate.endpoint.invoker.cache;

import java.util.function.Function;

import org.springframework.boot.actuate.endpoint.EndpointId;
import org.springframework.boot.actuate.endpoint.OperationType;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.boot.actuate.endpoint.http.ApiVersion;
import org.springframework.boot.actuate.endpoint.invoke.OperationInvoker;
import org.springframework.boot.actuate.endpoint.invoke.OperationInvokerAdvisor;
import org.springframework.boot.actuate.endpoint.invoke.OperationParameter;
import org.springframework.boot.actuate.endpoint.invoke.OperationParameters;

/**
 * {@link OperationInvokerAdvisor} to optionally provide result caching support.
 *

 * @since 2.0.0
 */
public class CachingOperationInvokerAdvisor implements OperationInvokerAdvisor {

	private final Function<EndpointId, Long> endpointIdTimeToLive;

	public CachingOperationInvokerAdvisor(Function<EndpointId, Long> endpointIdTimeToLive) {
		this.endpointIdTimeToLive = endpointIdTimeToLive;
	}

	@Override
	public OperationInvoker apply(EndpointId endpointId, OperationType operationType, OperationParameters parameters,
			OperationInvoker invoker) {
		if (operationType == OperationType.READ && !hasMandatoryParameter(parameters)) {
			Long timeToLive = this.endpointIdTimeToLive.apply(endpointId);
			if (timeToLive != null && timeToLive > 0) {
				return new CachingOperationInvoker(invoker, timeToLive);
			}
		}
		return invoker;
	}

	private boolean hasMandatoryParameter(OperationParameters parameters) {
		for (OperationParameter parameter : parameters) {
			if (parameter.isMandatory() && !ApiVersion.class.isAssignableFrom(parameter.getType())
					&& !SecurityContext.class.isAssignableFrom(parameter.getType())) {
				return true;
			}
		}
		return false;
	}

}
