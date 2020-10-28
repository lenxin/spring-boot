package org.springframework.boot.web.servlet;

import java.util.Map;

import javax.servlet.annotation.WebListener;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

/**
 * Handler for {@link WebListener @WebListener}-annotated classes.
 *

 */
class WebListenerHandler extends ServletComponentHandler {

	WebListenerHandler() {
		super(WebListener.class);
	}

	@Override
	protected void doHandle(Map<String, Object> attributes, AnnotatedBeanDefinition beanDefinition,
			BeanDefinitionRegistry registry) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder
				.rootBeanDefinition(ServletComponentWebListenerRegistrar.class);
		builder.addConstructorArgValue(beanDefinition.getBeanClassName());
		registry.registerBeanDefinition(beanDefinition.getBeanClassName() + "Registrar", builder.getBeanDefinition());
	}

	static class ServletComponentWebListenerRegistrar implements WebListenerRegistrar {

		private final String listenerClassName;

		ServletComponentWebListenerRegistrar(String listenerClassName) {
			this.listenerClassName = listenerClassName;
		}

		@Override
		public void register(WebListenerRegistry registry) {
			registry.addWebListeners(this.listenerClassName);
		}

	}

}
