package org.springframework.boot.web.servlet;

import java.io.IOException;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.annotation.WebListener;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link WebListenerHandler}.
 *

 */
class WebListenerHandlerTests {

	private final WebListenerHandler handler = new WebListenerHandler();

	private final SimpleBeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();

	@Test
	void listener() throws IOException {
		AnnotatedBeanDefinition definition = mock(AnnotatedBeanDefinition.class);
		given(definition.getBeanClassName()).willReturn(TestListener.class.getName());
		given(definition.getMetadata()).willReturn(new SimpleMetadataReaderFactory()
				.getMetadataReader(TestListener.class.getName()).getAnnotationMetadata());
		this.handler.handle(definition, this.registry);
		this.registry.getBeanDefinition(TestListener.class.getName() + "Registrar");
	}

	@WebListener
	static class TestListener implements ServletContextAttributeListener {

		@Override
		public void attributeAdded(ServletContextAttributeEvent event) {

		}

		@Override
		public void attributeRemoved(ServletContextAttributeEvent event) {

		}

		@Override
		public void attributeReplaced(ServletContextAttributeEvent event) {

		}

	}

}
