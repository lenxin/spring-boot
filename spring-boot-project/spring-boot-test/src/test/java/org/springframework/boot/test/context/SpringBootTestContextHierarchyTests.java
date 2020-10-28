package org.springframework.boot.test.context;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTestContextHierarchyTests.ChildConfiguration;
import org.springframework.boot.test.context.SpringBootTestContextHierarchyTests.ParentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

/**
 * Tests for {@link SpringBootTest @SpringBootTest} and
 * {@link ContextHierarchy @ContextHierarchy}.
 *

 */
@SpringBootTest
@ContextHierarchy({ @ContextConfiguration(classes = ParentConfiguration.class),
		@ContextConfiguration(classes = ChildConfiguration.class) })
class SpringBootTestContextHierarchyTests {

	@Test
	void contextLoads() {

	}

	@Configuration(proxyBeanMethods = false)
	static class ParentConfiguration {

		@Bean
		MyBean myBean() {
			return new MyBean();
		}

	}

	@Configuration(proxyBeanMethods = false)
	static class ChildConfiguration {

		ChildConfiguration(MyBean myBean) {

		}

	}

	static class MyBean {

	}

}
