package org.springframework.boot.autoconfigure.mustache;

import javax.annotation.PostConstruct;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Mustache.Collector;
import com.samskivert.mustache.Mustache.TemplateLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.template.TemplateLocation;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Mustache.
 *


 * @since 1.2.2
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(Mustache.class)
@EnableConfigurationProperties(MustacheProperties.class)
@Import({ MustacheServletWebConfiguration.class, MustacheReactiveWebConfiguration.class })
public class MustacheAutoConfiguration {

	private static final Log logger = LogFactory.getLog(MustacheAutoConfiguration.class);

	private final MustacheProperties mustache;

	private final ApplicationContext applicationContext;

	public MustacheAutoConfiguration(MustacheProperties mustache, ApplicationContext applicationContext) {
		this.mustache = mustache;
		this.applicationContext = applicationContext;
	}

	@PostConstruct
	public void checkTemplateLocationExists() {
		if (this.mustache.isCheckTemplateLocation()) {
			TemplateLocation location = new TemplateLocation(this.mustache.getPrefix());
			if (!location.exists(this.applicationContext) && logger.isWarnEnabled()) {
				logger.warn("Cannot find template location: " + location
						+ " (please add some templates, check your Mustache configuration, or set spring.mustache."
						+ "check-template-location=false)");
			}
		}
	}

	@Bean
	@ConditionalOnMissingBean
	public Mustache.Compiler mustacheCompiler(TemplateLoader mustacheTemplateLoader, Environment environment) {
		return Mustache.compiler().withLoader(mustacheTemplateLoader).withCollector(collector(environment));
	}

	private Collector collector(Environment environment) {
		MustacheEnvironmentCollector collector = new MustacheEnvironmentCollector();
		collector.setEnvironment(environment);
		return collector;
	}

	@Bean
	@ConditionalOnMissingBean(TemplateLoader.class)
	public MustacheResourceTemplateLoader mustacheTemplateLoader() {
		MustacheResourceTemplateLoader loader = new MustacheResourceTemplateLoader(this.mustache.getPrefix(),
				this.mustache.getSuffix());
		loader.setCharset(this.mustache.getCharsetName());
		return loader;
	}

}
