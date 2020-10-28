package org.springframework.boot.autoconfigure.web.reactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.web.reactive.config.ResourceChainRegistration;
import org.springframework.web.reactive.config.ResourceHandlerRegistration;
import org.springframework.web.reactive.resource.EncodedResourceResolver;
import org.springframework.web.reactive.resource.ResourceResolver;
import org.springframework.web.reactive.resource.VersionResourceResolver;

/**
 * {@link ResourceHandlerRegistrationCustomizer} used by auto-configuration to customize
 * the resource chain.
 *

 */
class ResourceChainResourceHandlerRegistrationCustomizer implements ResourceHandlerRegistrationCustomizer {

	@Autowired
	private ResourceProperties resourceProperties = new ResourceProperties();

	@Override
	public void customize(ResourceHandlerRegistration registration) {
		ResourceProperties.Chain properties = this.resourceProperties.getChain();
		configureResourceChain(properties, registration.resourceChain(properties.isCache()));
	}

	@SuppressWarnings("deprecation")
	private void configureResourceChain(ResourceProperties.Chain properties, ResourceChainRegistration chain) {
		ResourceProperties.Strategy strategy = properties.getStrategy();
		if (properties.isCompressed()) {
			chain.addResolver(new EncodedResourceResolver());
		}
		if (strategy.getFixed().isEnabled() || strategy.getContent().isEnabled()) {
			chain.addResolver(getVersionResourceResolver(strategy));
		}
		if (properties.isHtmlApplicationCache()) {
			chain.addTransformer(new org.springframework.web.reactive.resource.AppCacheManifestTransformer());
		}
	}

	private ResourceResolver getVersionResourceResolver(ResourceProperties.Strategy properties) {
		VersionResourceResolver resolver = new VersionResourceResolver();
		if (properties.getFixed().isEnabled()) {
			String version = properties.getFixed().getVersion();
			String[] paths = properties.getFixed().getPaths();
			resolver.addFixedVersionStrategy(version, paths);
		}
		if (properties.getContent().isEnabled()) {
			String[] paths = properties.getContent().getPaths();
			resolver.addContentVersionStrategy(paths);
		}
		return resolver;
	}

}
