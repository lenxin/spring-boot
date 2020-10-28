package org.springframework.boot.autoconfigure.condition.scan;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBeanTests.ExampleFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for a factory bean produced by a bean method with arguments on a
 * configuration class found via component scanning.
 *

 */
@Configuration(proxyBeanMethods = false)
public class ScannedFactoryBeanWithBeanMethodArgumentsConfiguration {

	@Bean
	public Foo foo() {
		return new Foo();
	}

	@Bean
	public ExampleFactoryBean exampleBeanFactoryBean(Foo foo) {
		return new ExampleFactoryBean("foo");
	}

	static class Foo {

	}

}
