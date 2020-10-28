package org.springframework.boot.actuate.elasticsearch;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.util.StreamUtils;

/**
 * {@link HealthIndicator} for an Elasticsearch cluster using a {@link RestClient}.
 *



 * @since 2.1.1
 */
public class ElasticsearchRestHealthIndicator extends AbstractHealthIndicator {

	private static final String RED_STATUS = "red";

	private final RestClient client;

	private final JsonParser jsonParser;

	public ElasticsearchRestHealthIndicator(RestHighLevelClient client) {
		this(client.getLowLevelClient());
	}

	public ElasticsearchRestHealthIndicator(RestClient client) {
		super("Elasticsearch health check failed");
		this.client = client;
		this.jsonParser = JsonParserFactory.getJsonParser();
	}

	@Override
	protected void doHealthCheck(Health.Builder builder) throws Exception {
		Response response = this.client.performRequest(new Request("GET", "/_cluster/health/"));
		StatusLine statusLine = response.getStatusLine();
		if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
			builder.down();
			builder.withDetail("statusCode", statusLine.getStatusCode());
			builder.withDetail("reasonPhrase", statusLine.getReasonPhrase());
			return;
		}
		try (InputStream inputStream = response.getEntity().getContent()) {
			doHealthCheck(builder, StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8));
		}
	}

	private void doHealthCheck(Health.Builder builder, String json) {
		Map<String, Object> response = this.jsonParser.parseMap(json);
		String status = (String) response.get("status");
		if (RED_STATUS.equals(status)) {
			builder.outOfService();
		}
		else {
			builder.up();
		}
		builder.withDetails(response);
	}

}
