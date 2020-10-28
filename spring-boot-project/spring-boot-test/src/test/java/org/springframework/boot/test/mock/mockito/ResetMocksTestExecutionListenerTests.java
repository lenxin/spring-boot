package org.springframework.boot.test.mock.mockito;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.example.ExampleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link ResetMocksTestExecutionListener}.
 *


 */
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
class ResetMocksTestExecutionListenerTests {

	@Autowired
	private ApplicationContext context;

	@Test
	void test001() {
		given(getMock("none").greeting()).willReturn("none");
		given(getMock("before").greeting()).willReturn("before");
		given(getMock("after").greeting()).willReturn("after");
	}

	@Test
	void test002() {
		assertThat(getMock("none").greeting()).isEqualTo("none");
		assertThat(getMock("before").greeting()).isNull();
		assertThat(getMock("after").greeting()).isNull();
	}

	ExampleService getMock(String name) {
		return this.context.getBean(name, ExampleService.class);
	}

	@Configuration(proxyBeanMethods = false)
	static class Config {

		@Bean
		ExampleService before(MockitoBeans mockedBeans) {
			ExampleService mock = mock(ExampleService.class, MockReset.before());
			mockedBeans.add(mock);
			return mock;
		}

		@Bean
		ExampleService after(MockitoBeans mockedBeans) {
			ExampleService mock = mock(ExampleService.class, MockReset.after());
			mockedBeans.add(mock);
			return mock;
		}

		@Bean
		ExampleService none(MockitoBeans mockedBeans) {
			ExampleService mock = mock(ExampleService.class);
			mockedBeans.add(mock);
			return mock;
		}

		@Bean
		@Lazy
		ExampleService fail() {
			// gh-5870
			throw new RuntimeException();
		}

		@Bean
		BrokenFactoryBean brokenFactoryBean() {
			// gh-7270
			return new BrokenFactoryBean();
		}

	}

	static class BrokenFactoryBean implements FactoryBean<String> {

		@Override
		public String getObject() {
			throw new IllegalStateException();
		}

		@Override
		public Class<?> getObjectType() {
			return String.class;
		}

		@Override
		public boolean isSingleton() {
			return true;
		}

	}

}
