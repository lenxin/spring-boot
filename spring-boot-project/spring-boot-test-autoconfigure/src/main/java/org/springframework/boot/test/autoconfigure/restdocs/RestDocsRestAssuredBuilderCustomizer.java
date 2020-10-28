package org.springframework.boot.test.autoconfigure.restdocs;

import io.restassured.specification.RequestSpecification;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.util.StringUtils;

/**
 * A customizer that configures Spring REST Docs with REST Assured.
 *

 */
class RestDocsRestAssuredBuilderCustomizer implements InitializingBean {

	private final RestDocsProperties properties;

	private final RequestSpecification delegate;

	RestDocsRestAssuredBuilderCustomizer(RestDocsProperties properties, RequestSpecification delegate) {
		this.properties = properties;
		this.delegate = delegate;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		PropertyMapper map = PropertyMapper.get();
		String host = this.properties.getUriHost();
		map.from(this.properties::getUriScheme)
				.when((scheme) -> StringUtils.hasText(scheme) && StringUtils.hasText(host))
				.to((scheme) -> this.delegate.baseUri(scheme + "://" + host));
		map.from(this.properties::getUriPort).whenNonNull().to(this.delegate::port);
	}

}
