package org.springframework.boot.docs.test.autoconfigure.restdocs.restassured;

import org.springframework.boot.test.autoconfigure.restdocs.RestDocsRestAssuredConfigurationCustomizer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.restdocs.restassured3.RestAssuredRestDocumentationConfigurer;
import org.springframework.restdocs.templates.TemplateFormats;

public class AdvancedConfigurationExample {

	// tag::configuration[]
	@TestConfiguration(proxyBeanMethods = false)
	public static class CustomizationConfiguration implements RestDocsRestAssuredConfigurationCustomizer {

		@Override
		public void customize(RestAssuredRestDocumentationConfigurer configurer) {
			configurer.snippets().withTemplateFormat(TemplateFormats.markdown());
		}

	}
	// end::configuration[]

}
