package org.springframework.boot.actuate.endpoint.web.annotation;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.actuate.endpoint.EndpointFilter;
import org.springframework.boot.actuate.endpoint.EndpointId;
import org.springframework.boot.actuate.endpoint.Operation;
import org.springframework.boot.actuate.endpoint.annotation.DiscoveredOperationMethod;
import org.springframework.boot.actuate.endpoint.annotation.EndpointDiscoverer;
import org.springframework.boot.actuate.endpoint.invoke.OperationInvoker;
import org.springframework.boot.actuate.endpoint.invoke.ParameterValueMapper;
import org.springframework.boot.actuate.endpoint.web.ExposableServletEndpoint;
import org.springframework.boot.actuate.endpoint.web.PathMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.annotation.MergedAnnotations.SearchStrategy;

/**
 * {@link EndpointDiscoverer} for {@link ExposableServletEndpoint servlet endpoints}.
 *

 * @since 2.0.0
 */
public class ServletEndpointDiscoverer extends EndpointDiscoverer<ExposableServletEndpoint, Operation>
		implements ServletEndpointsSupplier {

	private final List<PathMapper> endpointPathMappers;

	/**
	 * Create a new {@link ServletEndpointDiscoverer} instance.
	 * @param applicationContext the source application context
	 * @param endpointPathMappers the endpoint path mappers
	 * @param filters filters to apply
	 */
	public ServletEndpointDiscoverer(ApplicationContext applicationContext, List<PathMapper> endpointPathMappers,
			Collection<EndpointFilter<ExposableServletEndpoint>> filters) {
		super(applicationContext, ParameterValueMapper.NONE, Collections.emptyList(), filters);
		this.endpointPathMappers = endpointPathMappers;
	}

	@Override
	protected boolean isEndpointTypeExposed(Class<?> beanType) {
		return MergedAnnotations.from(beanType, SearchStrategy.SUPERCLASS).isPresent(ServletEndpoint.class);
	}

	@Override
	protected ExposableServletEndpoint createEndpoint(Object endpointBean, EndpointId id, boolean enabledByDefault,
			Collection<Operation> operations) {
		String rootPath = PathMapper.getRootPath(this.endpointPathMappers, id);
		return new DiscoveredServletEndpoint(this, endpointBean, id, rootPath, enabledByDefault);
	}

	@Override
	protected Operation createOperation(EndpointId endpointId, DiscoveredOperationMethod operationMethod,
			OperationInvoker invoker) {
		throw new IllegalStateException("ServletEndpoints must not declare operations");
	}

	@Override
	protected OperationKey createOperationKey(Operation operation) {
		throw new IllegalStateException("ServletEndpoints must not declare operations");
	}

}
