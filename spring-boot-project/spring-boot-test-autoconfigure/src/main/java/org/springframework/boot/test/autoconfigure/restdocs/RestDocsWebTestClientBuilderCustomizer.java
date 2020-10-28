package org.springframework.boot.test.autoconfigure.restdocs;

import org.springframework.boot.test.web.reactive.server.WebTestClientBuilderCustomizer;
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentationConfigurer;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.StringUtils;

/**
 * A {@link WebTestClientBuilderCustomizer} that configures Spring REST Docs.
 *


 */
class RestDocsWebTestClientBuilderCustomizer implements WebTestClientBuilderCustomizer {

	private final RestDocsProperties properties;

	private final WebTestClientRestDocumentationConfigurer delegate;

	RestDocsWebTestClientBuilderCustomizer(RestDocsProperties properties,
			WebTestClientRestDocumentationConfigurer delegate) {
		this.properties = properties;
		this.delegate = delegate;
	}

	@Override
	public void customize(WebTestClient.Builder builder) {
		customizeBaseUrl(builder);
		builder.filter(this.delegate);
	}

	private void customizeBaseUrl(WebTestClient.Builder builder) {
		String scheme = this.properties.getUriScheme();
		String host = this.properties.getUriHost();
		String baseUrl = (StringUtils.hasText(scheme) ? scheme : "http") + "://"
				+ (StringUtils.hasText(host) ? host : "localhost");
		Integer port = this.properties.getUriPort();
		if (!isStandardPort(scheme, port)) {
			baseUrl += ":" + port;
		}
		builder.baseUrl(baseUrl);
	}

	private boolean isStandardPort(String scheme, Integer port) {
		if (port == null) {
			return true;
		}
		return ("http".equals(scheme) && port == 80) || ("https".equals(scheme) && port == 443);
	}

}
