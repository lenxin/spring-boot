package org.springframework.boot.actuate.autoconfigure.endpoint;

import java.time.Duration;
import java.util.function.Function;

import org.springframework.boot.actuate.endpoint.EndpointId;
import org.springframework.boot.actuate.endpoint.invoker.cache.CachingOperationInvokerAdvisor;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;

/**
 * Function for use with {@link CachingOperationInvokerAdvisor} that extracts caching
 * time-to-live from a {@link PropertyResolver resolved property}.
 *


 */
class EndpointIdTimeToLivePropertyFunction implements Function<EndpointId, Long> {

	private static final Bindable<Duration> DURATION = Bindable.of(Duration.class);

	private final Environment environment;

	/**
	 * Create a new instance with the {@link PropertyResolver} to use.
	 * @param environment the environment
	 */
	EndpointIdTimeToLivePropertyFunction(Environment environment) {
		this.environment = environment;
	}

	@Override
	public Long apply(EndpointId endpointId) {
		String name = String.format("management.endpoint.%s.cache.time-to-live", endpointId.toLowerCaseString());
		BindResult<Duration> duration = Binder.get(this.environment).bind(name, DURATION);
		return duration.map(Duration::toMillis).orElse(null);
	}

}
