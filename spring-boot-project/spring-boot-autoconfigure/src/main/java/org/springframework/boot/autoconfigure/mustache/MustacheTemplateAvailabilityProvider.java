package org.springframework.boot.autoconfigure.mustache;

import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ClassUtils;

/**
 * {@link TemplateAvailabilityProvider} that provides availability information for
 * Mustache view templates.
 *


 * @since 1.2.2
 */
public class MustacheTemplateAvailabilityProvider implements TemplateAvailabilityProvider {

	@Override
	public boolean isTemplateAvailable(String view, Environment environment, ClassLoader classLoader,
			ResourceLoader resourceLoader) {
		if (ClassUtils.isPresent("com.samskivert.mustache.Template", classLoader)) {
			String prefix = environment.getProperty("spring.mustache.prefix", MustacheProperties.DEFAULT_PREFIX);
			String suffix = environment.getProperty("spring.mustache.suffix", MustacheProperties.DEFAULT_SUFFIX);
			return resourceLoader.getResource(prefix + view + suffix).exists();
		}
		return false;
	}

}
