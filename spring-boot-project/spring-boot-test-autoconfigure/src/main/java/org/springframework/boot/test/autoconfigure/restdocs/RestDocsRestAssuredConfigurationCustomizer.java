package org.springframework.boot.test.autoconfigure.restdocs;

import org.springframework.restdocs.restassured3.RestAssuredRestDocumentationConfigurer;

/**
 * A customizer for {@link RestAssuredRestDocumentationConfigurer}. If a
 * {@code RestDocsRestAssuredConfigurationCustomizer} bean is found in the application
 * context it will be {@link #customize called} to customize the
 * {@code RestAssuredRestDocumentationConfigurer} before it is applied. Intended for use
 * only when the attributes on {@link AutoConfigureRestDocs @AutoConfigureRestDocs} do not
 * provide sufficient customization.
 *

 * @since 2.0.0
 */
@FunctionalInterface
public interface RestDocsRestAssuredConfigurationCustomizer {

	/**
	 * Customize the given {@code configurer}.
	 * @param configurer the configurer
	 */
	void customize(RestAssuredRestDocumentationConfigurer configurer);

}
