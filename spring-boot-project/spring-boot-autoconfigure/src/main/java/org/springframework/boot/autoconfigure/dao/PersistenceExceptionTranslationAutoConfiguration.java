package org.springframework.boot.autoconfigure.dao;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring's persistence exception
 * translation.
 *



 * @since 1.2.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(PersistenceExceptionTranslationPostProcessor.class)
public class PersistenceExceptionTranslationAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "spring.dao.exceptiontranslation", name = "enabled", matchIfMissing = true)
	public static PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor(
			Environment environment) {
		PersistenceExceptionTranslationPostProcessor postProcessor = new PersistenceExceptionTranslationPostProcessor();
		boolean proxyTargetClass = environment.getProperty("spring.aop.proxy-target-class", Boolean.class,
				Boolean.TRUE);
		postProcessor.setProxyTargetClass(proxyTargetClass);
		return postProcessor;
	}

}
