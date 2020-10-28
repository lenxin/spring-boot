package org.springframework.boot.docs.test.autoconfigure.restdocs.webclient;

import org.springframework.boot.test.autoconfigure.restdocs.RestDocsWebTestClientConfigurationCustomizer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentationConfigurer;

public class AdvancedConfigurationExample {

	// tag::configuration[]
	@TestConfiguration(proxyBeanMethods = false)
	public static class CustomizationConfiguration implements RestDocsWebTestClientConfigurationCustomizer {

		@Override
		public void customize(WebTestClientRestDocumentationConfigurer configurer) {
			configurer.snippets().withEncoding("UTF-8");
		}

	}
	// end::configuration[]

}
