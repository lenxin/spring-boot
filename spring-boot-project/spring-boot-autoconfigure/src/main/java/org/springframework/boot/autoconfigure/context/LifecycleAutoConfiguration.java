package org.springframework.boot.autoconfigure.context;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.DefaultLifecycleProcessor;

/**
 * {@link EnableAutoConfiguration Auto-configuration} relating to the application
 * context's lifecycle.
 *

 * @since 2.3.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(LifecycleProperties.class)
public class LifecycleAutoConfiguration {

	@Bean(name = AbstractApplicationContext.LIFECYCLE_PROCESSOR_BEAN_NAME)
	@ConditionalOnMissingBean(name = AbstractApplicationContext.LIFECYCLE_PROCESSOR_BEAN_NAME,
			search = SearchStrategy.CURRENT)
	public DefaultLifecycleProcessor defaultLifecycleProcessor(LifecycleProperties properties) {
		DefaultLifecycleProcessor lifecycleProcessor = new DefaultLifecycleProcessor();
		lifecycleProcessor.setTimeoutPerShutdownPhase(properties.getTimeoutPerShutdownPhase().toMillis());
		return lifecycleProcessor;
	}

}
