package org.springframework.boot.test.autoconfigure.webservices.client;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.webservices.client.WebServiceTemplateAutoConfiguration;
import org.springframework.boot.webservices.client.WebServiceTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.client.core.WebServiceTemplate;

/**
 * Auto-configuration for a web-client {@link WebServiceTemplate}. Used when
 * {@link AutoConfigureWebServiceClient#registerWebServiceTemplate()} is {@code true}.
 *

 * @since 2.3.0
 * @see AutoConfigureWebServiceClient
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "spring.test.webservice.client", name = "register-web-service-template")
@AutoConfigureAfter(WebServiceTemplateAutoConfiguration.class)
@ConditionalOnClass(WebServiceTemplate.class)
@ConditionalOnBean(WebServiceTemplateBuilder.class)
public class WebServiceClientTemplateAutoConfiguration {

	@Bean
	public WebServiceTemplate webServiceTemplate(WebServiceTemplateBuilder builder) {
		return builder.build();
	}

}
