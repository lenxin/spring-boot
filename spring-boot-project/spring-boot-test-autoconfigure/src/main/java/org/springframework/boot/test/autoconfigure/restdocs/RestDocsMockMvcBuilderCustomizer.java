package org.springframework.boot.test.autoconfigure.restdocs;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentationConfigurer;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.mockmvc.UriConfigurer;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;

/**
 * A {@link MockMvcBuilderCustomizer} that configures Spring REST Docs.
 *

 * @since 1.5.22
 */
public class RestDocsMockMvcBuilderCustomizer implements InitializingBean, MockMvcBuilderCustomizer {

	private final RestDocsProperties properties;

	private final MockMvcRestDocumentationConfigurer delegate;

	private final RestDocumentationResultHandler resultHandler;

	RestDocsMockMvcBuilderCustomizer(RestDocsProperties properties, MockMvcRestDocumentationConfigurer delegate,
			RestDocumentationResultHandler resultHandler) {
		this.properties = properties;
		this.delegate = delegate;
		this.resultHandler = resultHandler;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		PropertyMapper map = PropertyMapper.get();
		RestDocsProperties properties = this.properties;
		UriConfigurer uri = this.delegate.uris();
		map.from(properties::getUriScheme).whenHasText().to(uri::withScheme);
		map.from(properties::getUriHost).whenHasText().to(uri::withHost);
		map.from(properties::getUriPort).whenNonNull().to(uri::withPort);
	}

	@Override
	public void customize(ConfigurableMockMvcBuilder<?> builder) {
		builder.apply(this.delegate);
		if (this.resultHandler != null) {
			builder.alwaysDo(this.resultHandler);
		}
	}

}
