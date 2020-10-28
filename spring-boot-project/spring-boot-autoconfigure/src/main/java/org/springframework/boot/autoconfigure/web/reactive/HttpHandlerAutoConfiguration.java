package org.springframework.boot.autoconfigure.web.reactive;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ContextPathCompositeHandler;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.DispatcherHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link HttpHandler}.
 *


 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ DispatcherHandler.class, HttpHandler.class })
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@ConditionalOnMissingBean(HttpHandler.class)
@AutoConfigureAfter({ WebFluxAutoConfiguration.class })
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
public class HttpHandlerAutoConfiguration {

	@Configuration(proxyBeanMethods = false)
	public static class AnnotationConfig {

		private final ApplicationContext applicationContext;

		public AnnotationConfig(ApplicationContext applicationContext) {
			this.applicationContext = applicationContext;
		}

		@Bean
		public HttpHandler httpHandler(ObjectProvider<WebFluxProperties> propsProvider) {
			HttpHandler httpHandler = WebHttpHandlerBuilder.applicationContext(this.applicationContext).build();
			WebFluxProperties properties = propsProvider.getIfAvailable();
			if (properties != null && StringUtils.hasText(properties.getBasePath())) {
				Map<String, HttpHandler> handlersMap = Collections.singletonMap(properties.getBasePath(), httpHandler);
				return new ContextPathCompositeHandler(handlersMap);
			}
			return httpHandler;
		}

	}

}
