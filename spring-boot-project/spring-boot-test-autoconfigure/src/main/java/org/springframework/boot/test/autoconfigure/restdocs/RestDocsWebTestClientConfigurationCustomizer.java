package org.springframework.boot.test.autoconfigure.restdocs;

import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentationConfigurer;

/**
 * A customizer for {@link WebTestClientRestDocumentationConfigurer}. If a
 * {@code RestDocsWebTestClientConfigurationCustomizer} bean is found in the application
 * context it will be {@link #customize called} to customize the
 * {@code WebTestClientRestDocumentationConfigurer} before it is applied. Intended for use
 * only when the attributes on {@link AutoConfigureRestDocs @AutoConfigureRestDocs} do not
 * provide sufficient customization.
 *

 * @since 2.0.0
 */
@FunctionalInterface
public interface RestDocsWebTestClientConfigurationCustomizer {

	/**
	 * Customize the given {@code configurer}.
	 * @param configurer the configurer
	 */
	void customize(WebTestClientRestDocumentationConfigurer configurer);

}
