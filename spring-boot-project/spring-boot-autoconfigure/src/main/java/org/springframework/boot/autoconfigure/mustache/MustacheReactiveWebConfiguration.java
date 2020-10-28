package org.springframework.boot.autoconfigure.mustache;

import com.samskivert.mustache.Mustache.Compiler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.web.reactive.result.view.MustacheViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.REACTIVE)
class MustacheReactiveWebConfiguration {

	@Bean
	@ConditionalOnMissingBean
	MustacheViewResolver mustacheViewResolver(Compiler mustacheCompiler, MustacheProperties mustache) {
		MustacheViewResolver resolver = new MustacheViewResolver(mustacheCompiler);
		resolver.setPrefix(mustache.getPrefix());
		resolver.setSuffix(mustache.getSuffix());
		resolver.setViewNames(mustache.getViewNames());
		resolver.setRequestContextAttribute(mustache.getRequestContextAttribute());
		resolver.setCharset(mustache.getCharsetName());
		resolver.setOrder(Ordered.LOWEST_PRECEDENCE - 10);
		return resolver;
	}

}
