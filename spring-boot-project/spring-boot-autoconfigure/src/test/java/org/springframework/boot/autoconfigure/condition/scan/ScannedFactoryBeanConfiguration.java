package org.springframework.boot.autoconfigure.condition.scan;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBeanTests.ExampleBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBeanTests.ExampleFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for a factory bean produced by a bean method on a configuration class
 * found via component scanning.
 *

 */
@Configuration(proxyBeanMethods = false)
public class ScannedFactoryBeanConfiguration {

	@Bean
	public FactoryBean<ExampleBean> exampleBeanFactoryBean() {
		return new ExampleFactoryBean("foo");
	}

}
