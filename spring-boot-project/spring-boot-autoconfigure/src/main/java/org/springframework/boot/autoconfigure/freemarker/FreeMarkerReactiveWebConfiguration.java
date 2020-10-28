package org.springframework.boot.autoconfigure.freemarker;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfig;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerViewResolver;

/**
 * Configuration for FreeMarker when used in a reactive web context.
 *


 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@AutoConfigureAfter(WebFluxAutoConfiguration.class)
class FreeMarkerReactiveWebConfiguration extends AbstractFreeMarkerConfiguration {

	FreeMarkerReactiveWebConfiguration(FreeMarkerProperties properties) {
		super(properties);
	}

	@Bean
	@ConditionalOnMissingBean(FreeMarkerConfig.class)
	FreeMarkerConfigurer freeMarkerConfigurer() {
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		applyProperties(configurer);
		return configurer;
	}

	@Bean
	freemarker.template.Configuration freeMarkerConfiguration(FreeMarkerConfig configurer) {
		return configurer.getConfiguration();
	}

	@Bean
	@ConditionalOnMissingBean(name = "freeMarkerViewResolver")
	@ConditionalOnProperty(name = "spring.freemarker.enabled", matchIfMissing = true)
	FreeMarkerViewResolver freeMarkerViewResolver() {
		FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
		resolver.setPrefix(getProperties().getPrefix());
		resolver.setSuffix(getProperties().getSuffix());
		resolver.setRequestContextAttribute(getProperties().getRequestContextAttribute());
		resolver.setViewNames(getProperties().getViewNames());
		return resolver;
	}

}
