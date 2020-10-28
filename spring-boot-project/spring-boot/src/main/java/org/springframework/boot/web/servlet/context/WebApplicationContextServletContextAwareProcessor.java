package org.springframework.boot.web.servlet.context;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.springframework.util.Assert;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.support.ServletContextAwareProcessor;

/**
 * Variant of {@link ServletContextAwareProcessor} for use with a
 * {@link ConfigurableWebApplicationContext}. Can be used when registering the processor
 * can occur before the {@link ServletContext} or {@link ServletConfig} have been
 * initialized.
 *

 * @since 1.0.0
 */
public class WebApplicationContextServletContextAwareProcessor extends ServletContextAwareProcessor {

	private final ConfigurableWebApplicationContext webApplicationContext;

	public WebApplicationContextServletContextAwareProcessor(ConfigurableWebApplicationContext webApplicationContext) {
		Assert.notNull(webApplicationContext, "WebApplicationContext must not be null");
		this.webApplicationContext = webApplicationContext;
	}

	@Override
	protected ServletContext getServletContext() {
		ServletContext servletContext = this.webApplicationContext.getServletContext();
		return (servletContext != null) ? servletContext : super.getServletContext();
	}

	@Override
	protected ServletConfig getServletConfig() {
		ServletConfig servletConfig = this.webApplicationContext.getServletConfig();
		return (servletConfig != null) ? servletConfig : super.getServletConfig();
	}

}
