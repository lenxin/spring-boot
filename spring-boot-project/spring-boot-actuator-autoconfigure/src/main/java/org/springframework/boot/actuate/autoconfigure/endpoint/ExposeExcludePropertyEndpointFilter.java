package org.springframework.boot.actuate.autoconfigure.endpoint;

import java.util.Collection;

import org.springframework.boot.actuate.autoconfigure.endpoint.expose.IncludeExcludeEndpointFilter;
import org.springframework.boot.actuate.endpoint.EndpointFilter;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.core.env.Environment;

/**
 * {@link EndpointFilter} that will filter endpoints based on {@code include} and
 * {@code exclude} patterns.
 *
 * @param <E> the endpoint type

 * @since 2.0.0
 * @deprecated since 2.2.7 in favor of {@link IncludeExcludeEndpointFilter}
 */
@Deprecated
public class ExposeExcludePropertyEndpointFilter<E extends ExposableEndpoint<?>>
		extends IncludeExcludeEndpointFilter<E> {

	public ExposeExcludePropertyEndpointFilter(Class<E> endpointType, Environment environment, String prefix,
			String... exposeDefaults) {
		super(endpointType, environment, prefix, exposeDefaults);
	}

	public ExposeExcludePropertyEndpointFilter(Class<E> endpointType, Collection<String> include,
			Collection<String> exclude, String... exposeDefaults) {
		super(endpointType, include, exclude, exposeDefaults);
	}

}
