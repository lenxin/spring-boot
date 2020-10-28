package org.springframework.boot.autoconfigure.web.servlet;

import java.io.File;
import java.security.AccessControlException;

import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ClassUtils;

/**
 * {@link TemplateAvailabilityProvider} that provides availability information for JSP
 * view templates.
 *



 * @since 2.0.0
 */
public class JspTemplateAvailabilityProvider implements TemplateAvailabilityProvider {

	@Override
	public boolean isTemplateAvailable(String view, Environment environment, ClassLoader classLoader,
			ResourceLoader resourceLoader) {
		if (ClassUtils.isPresent("org.apache.jasper.compiler.JspConfig", classLoader)) {
			String resourceName = getResourceName(view, environment);
			if (resourceLoader.getResource(resourceName).exists()) {
				return true;
			}
			try {
				return new File("src/main/webapp", resourceName).exists();
			}
			catch (AccessControlException ex) {
			}
		}
		return false;
	}

	private String getResourceName(String view, Environment environment) {
		String prefix = environment.getProperty("spring.mvc.view.prefix", WebMvcAutoConfiguration.DEFAULT_PREFIX);
		String suffix = environment.getProperty("spring.mvc.view.suffix", WebMvcAutoConfiguration.DEFAULT_SUFFIX);
		return prefix + view + suffix;
	}

}
