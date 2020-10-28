package org.springframework.boot.autoconfigure.hateoas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.server.mvc.TypeConstrainedMappingJackson2HttpMessageConverter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * Configuration for {@link HttpMessageConverter HttpMessageConverters} when hypermedia is
 * enabled.
 *

 * @since 1.3.0
 */
@Configuration(proxyBeanMethods = false)
public class HypermediaHttpMessageConverterConfiguration {

	@Bean
	@ConditionalOnProperty(prefix = "spring.hateoas", name = "use-hal-as-default-json-media-type",
			matchIfMissing = true)
	public static HalMessageConverterSupportedMediaTypesCustomizer halMessageConverterSupportedMediaTypeCustomizer() {
		return new HalMessageConverterSupportedMediaTypesCustomizer();
	}

	/**
	 * Updates any {@link TypeConstrainedMappingJackson2HttpMessageConverter}s to support
	 * {@code application/json} in addition to {@code application/hal+json}. Cannot be a
	 * {@link BeanPostProcessor} as processing must be performed after
	 * {@code Jackson2ModuleRegisteringBeanPostProcessor} has registered the converter and
	 * it is unordered.
	 */
	private static class HalMessageConverterSupportedMediaTypesCustomizer implements BeanFactoryAware {

		private volatile BeanFactory beanFactory;

		@PostConstruct
		void configureHttpMessageConverters() {
			if (this.beanFactory instanceof ListableBeanFactory) {
				configureHttpMessageConverters(((ListableBeanFactory) this.beanFactory)
						.getBeansOfType(RequestMappingHandlerAdapter.class).values());
			}
		}

		private void configureHttpMessageConverters(Collection<RequestMappingHandlerAdapter> handlerAdapters) {
			for (RequestMappingHandlerAdapter handlerAdapter : handlerAdapters) {
				for (HttpMessageConverter<?> messageConverter : handlerAdapter.getMessageConverters()) {
					configureHttpMessageConverter(messageConverter);
				}
			}
		}

		private void configureHttpMessageConverter(HttpMessageConverter<?> converter) {
			if (converter instanceof TypeConstrainedMappingJackson2HttpMessageConverter) {
				List<MediaType> supportedMediaTypes = new ArrayList<>(converter.getSupportedMediaTypes());
				if (!supportedMediaTypes.contains(MediaType.APPLICATION_JSON)) {
					supportedMediaTypes.add(MediaType.APPLICATION_JSON);
				}
				((AbstractHttpMessageConverter<?>) converter).setSupportedMediaTypes(supportedMediaTypes);
			}
		}

		@Override
		public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
			this.beanFactory = beanFactory;
		}

	}

}
