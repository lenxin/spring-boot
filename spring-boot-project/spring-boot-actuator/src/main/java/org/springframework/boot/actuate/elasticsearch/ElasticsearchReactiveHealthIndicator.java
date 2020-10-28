package org.springframework.boot.actuate.elasticsearch;

import java.util.Map;

import reactor.core.publisher.Mono;

import org.springframework.boot.actuate.health.AbstractReactiveHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * {@link HealthIndicator} for an Elasticsearch cluster using a
 * {@link ReactiveElasticsearchClient}.
 *



 * @since 2.3.2
 */
public class ElasticsearchReactiveHealthIndicator extends AbstractReactiveHealthIndicator {

	private static final ParameterizedTypeReference<Map<String, Object>> STRING_OBJECT_MAP = new ParameterizedTypeReference<Map<String, Object>>() {
	};

	private static final String RED_STATUS = "red";

	private final ReactiveElasticsearchClient client;

	public ElasticsearchReactiveHealthIndicator(ReactiveElasticsearchClient client) {
		super("Elasticsearch health check failed");
		this.client = client;
	}

	@Override
	protected Mono<Health> doHealthCheck(Health.Builder builder) {
		return this.client.execute((webClient) -> getHealth(builder, webClient));
	}

	private Mono<Health> getHealth(Health.Builder builder, WebClient webClient) {
		return webClient.get().uri("/_cluster/health/").exchangeToMono((response) -> doHealthCheck(builder, response));
	}

	private Mono<Health> doHealthCheck(Health.Builder builder, ClientResponse response) {
		if (response.statusCode().is2xxSuccessful()) {
			return response.bodyToMono(STRING_OBJECT_MAP).map((body) -> getHealth(builder, body));
		}
		builder.down();
		builder.withDetail("statusCode", response.rawStatusCode());
		builder.withDetail("reasonPhrase", response.statusCode().getReasonPhrase());
		return response.releaseBody().thenReturn(builder.build());
	}

	private Health getHealth(Health.Builder builder, Map<String, Object> body) {
		String status = (String) body.get("status");
		builder.status(RED_STATUS.equals(status) ? Status.OUT_OF_SERVICE : Status.UP);
		builder.withDetails(body);
		return builder.build();
	}

}
