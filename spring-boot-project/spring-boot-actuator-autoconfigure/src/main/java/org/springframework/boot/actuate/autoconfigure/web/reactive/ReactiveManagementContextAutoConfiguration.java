package org.springframework.boot.actuate.autoconfigure.web.reactive;

import reactor.core.publisher.Flux;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Reactive-specific management
 * context concerns.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(Flux.class)
@ConditionalOnWebApplication(type = Type.REACTIVE)
public class ReactiveManagementContextAutoConfiguration {

	@Bean
	public ReactiveManagementContextFactory reactiveWebChildContextFactory() {
		return new ReactiveManagementContextFactory();
	}

}
