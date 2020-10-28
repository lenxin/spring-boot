package org.springframework.boot.autoconfigure.thymeleaf;

import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ClassUtils;

/**
 * {@link TemplateAvailabilityProvider} that provides availability information for
 * Thymeleaf view templates.
 *


 * @since 1.1.0
 */
public class ThymeleafTemplateAvailabilityProvider implements TemplateAvailabilityProvider {

	@Override
	public boolean isTemplateAvailable(String view, Environment environment, ClassLoader classLoader,
			ResourceLoader resourceLoader) {
		if (ClassUtils.isPresent("org.thymeleaf.spring5.SpringTemplateEngine", classLoader)) {
			String prefix = environment.getProperty("spring.thymeleaf.prefix", ThymeleafProperties.DEFAULT_PREFIX);
			String suffix = environment.getProperty("spring.thymeleaf.suffix", ThymeleafProperties.DEFAULT_SUFFIX);
			return resourceLoader.getResource(prefix + view + suffix).exists();
		}
		return false;
	}

}
