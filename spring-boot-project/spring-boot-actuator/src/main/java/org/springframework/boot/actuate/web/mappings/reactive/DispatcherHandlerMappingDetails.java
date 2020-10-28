package org.springframework.boot.actuate.web.mappings.reactive;

import org.springframework.boot.actuate.web.mappings.HandlerMethodDescription;
import org.springframework.web.reactive.DispatcherHandler;

/**
 * Details of a {@link DispatcherHandler} mapping.
 *

 * @since 2.0.0
 */
public class DispatcherHandlerMappingDetails {

	private HandlerMethodDescription handlerMethod;

	private HandlerFunctionDescription handlerFunction;

	private RequestMappingConditionsDescription requestMappingConditions;

	public HandlerMethodDescription getHandlerMethod() {
		return this.handlerMethod;
	}

	void setHandlerMethod(HandlerMethodDescription handlerMethod) {
		this.handlerMethod = handlerMethod;
	}

	public HandlerFunctionDescription getHandlerFunction() {
		return this.handlerFunction;
	}

	void setHandlerFunction(HandlerFunctionDescription handlerFunction) {
		this.handlerFunction = handlerFunction;
	}

	public RequestMappingConditionsDescription getRequestMappingConditions() {
		return this.requestMappingConditions;
	}

	void setRequestMappingConditions(RequestMappingConditionsDescription requestMappingConditions) {
		this.requestMappingConditions = requestMappingConditions;
	}

}
