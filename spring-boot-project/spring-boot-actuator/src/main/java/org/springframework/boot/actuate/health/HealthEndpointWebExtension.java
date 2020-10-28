package org.springframework.boot.actuate.health;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.Selector.Match;
import org.springframework.boot.actuate.endpoint.http.ApiVersion;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.endpoint.web.annotation.EndpointWebExtension;

/**
 * {@link EndpointWebExtension @EndpointWebExtension} for the {@link HealthEndpoint}.
 *








 * @since 2.0.0
 */
@EndpointWebExtension(endpoint = HealthEndpoint.class)
public class HealthEndpointWebExtension extends HealthEndpointSupport<HealthContributor, HealthComponent> {

	private static final String[] NO_PATH = {};

	/**
	 * Create a new {@link HealthEndpointWebExtension} instance.
	 * @param registry the health contributor registry
	 * @param groups the health endpoint groups
	 */
	public HealthEndpointWebExtension(HealthContributorRegistry registry, HealthEndpointGroups groups) {
		super(registry, groups);
	}

	@ReadOperation
	public WebEndpointResponse<HealthComponent> health(ApiVersion apiVersion, SecurityContext securityContext) {
		return health(apiVersion, securityContext, false, NO_PATH);
	}

	@ReadOperation
	public WebEndpointResponse<HealthComponent> health(ApiVersion apiVersion, SecurityContext securityContext,
			@Selector(match = Match.ALL_REMAINING) String... path) {
		return health(apiVersion, securityContext, false, path);
	}

	public WebEndpointResponse<HealthComponent> health(ApiVersion apiVersion, SecurityContext securityContext,
			boolean showAll, String... path) {
		HealthResult<HealthComponent> result = getHealth(apiVersion, securityContext, showAll, path);
		if (result == null) {
			return (Arrays.equals(path, NO_PATH))
					? new WebEndpointResponse<>(DEFAULT_HEALTH, WebEndpointResponse.STATUS_OK)
					: new WebEndpointResponse<>(WebEndpointResponse.STATUS_NOT_FOUND);
		}
		HealthComponent health = result.getHealth();
		HealthEndpointGroup group = result.getGroup();
		int statusCode = group.getHttpCodeStatusMapper().getStatusCode(health.getStatus());
		return new WebEndpointResponse<>(health, statusCode);
	}

	@Override
	protected HealthComponent getHealth(HealthContributor contributor, boolean includeDetails) {
		return ((HealthIndicator) contributor).getHealth(includeDetails);
	}

	@Override
	protected HealthComponent aggregateContributions(ApiVersion apiVersion, Map<String, HealthComponent> contributions,
			StatusAggregator statusAggregator, boolean showComponents, Set<String> groupNames) {
		return getCompositeHealth(apiVersion, contributions, statusAggregator, showComponents, groupNames);
	}

}
