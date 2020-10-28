package org.springframework.boot.autoconfigure.webservices.client;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.webservices.client.WebServiceTemplateBuilder;
import org.springframework.boot.webservices.client.WebServiceTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link WebServiceTemplate}.
 *

 * @since 2.1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ WebServiceTemplate.class, Unmarshaller.class, Marshaller.class })
public class WebServiceTemplateAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public WebServiceTemplateBuilder webServiceTemplateBuilder(
			ObjectProvider<WebServiceTemplateCustomizer> webServiceTemplateCustomizers) {
		WebServiceTemplateBuilder builder = new WebServiceTemplateBuilder();
		List<WebServiceTemplateCustomizer> customizers = webServiceTemplateCustomizers.orderedStream()
				.collect(Collectors.toList());
		if (!customizers.isEmpty()) {
			builder = builder.customizers(customizers);
		}
		return builder;
	}

}
