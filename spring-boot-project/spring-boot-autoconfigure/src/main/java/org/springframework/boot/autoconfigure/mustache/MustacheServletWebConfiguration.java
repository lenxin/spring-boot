package org.springframework.boot.autoconfigure.mustache;

import com.samskivert.mustache.Mustache.Compiler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.SERVLET)
class MustacheServletWebConfiguration {

	@Bean
	@ConditionalOnMissingBean
	MustacheViewResolver mustacheViewResolver(Compiler mustacheCompiler, MustacheProperties mustache) {
		MustacheViewResolver resolver = new MustacheViewResolver(mustacheCompiler);
		mustache.applyToMvcViewResolver(resolver);
		resolver.setCharset(mustache.getCharsetName());
		resolver.setOrder(Ordered.LOWEST_PRECEDENCE - 10);
		return resolver;
	}

}
