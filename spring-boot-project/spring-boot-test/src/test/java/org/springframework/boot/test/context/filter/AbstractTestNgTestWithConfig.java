package org.springframework.boot.test.context.filter;

import org.testng.annotations.Test;

import org.springframework.context.annotation.Configuration;

@Test
public abstract class AbstractTestNgTestWithConfig {

	@Configuration(proxyBeanMethods = false)
	static class Config {

	}

}
