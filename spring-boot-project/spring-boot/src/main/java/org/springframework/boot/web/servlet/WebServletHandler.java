package org.springframework.boot.web.servlet;

import java.util.Map;

import javax.servlet.MultipartConfigElement;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.util.StringUtils;

/**
 * Handler for {@link WebServlet @WebServlet}-annotated classes.
 *

 */
class WebServletHandler extends ServletComponentHandler {

	WebServletHandler() {
		super(WebServlet.class);
	}

	@Override
	public void doHandle(Map<String, Object> attributes, AnnotatedBeanDefinition beanDefinition,
			BeanDefinitionRegistry registry) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(ServletRegistrationBean.class);
		builder.addPropertyValue("asyncSupported", attributes.get("asyncSupported"));
		builder.addPropertyValue("initParameters", extractInitParameters(attributes));
		builder.addPropertyValue("loadOnStartup", attributes.get("loadOnStartup"));
		String name = determineName(attributes, beanDefinition);
		builder.addPropertyValue("name", name);
		builder.addPropertyValue("servlet", beanDefinition);
		builder.addPropertyValue("urlMappings", extractUrlPatterns(attributes));
		builder.addPropertyValue("multipartConfig", determineMultipartConfig(beanDefinition));
		registry.registerBeanDefinition(name, builder.getBeanDefinition());
	}

	private String determineName(Map<String, Object> attributes, BeanDefinition beanDefinition) {
		return (String) (StringUtils.hasText((String) attributes.get("name")) ? attributes.get("name")
				: beanDefinition.getBeanClassName());
	}

	private MultipartConfigElement determineMultipartConfig(AnnotatedBeanDefinition beanDefinition) {
		Map<String, Object> attributes = beanDefinition.getMetadata()
				.getAnnotationAttributes(MultipartConfig.class.getName());
		if (attributes == null) {
			return null;
		}
		return new MultipartConfigElement((String) attributes.get("location"), (Long) attributes.get("maxFileSize"),
				(Long) attributes.get("maxRequestSize"), (Integer) attributes.get("fileSizeThreshold"));
	}

}
