package org.springframework.boot.test.mock.mockito;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MockBean} with abstract class and generics.
 *

 */
@SpringBootTest(classes = AbstractMockBeanOnGenericTests.TestConfiguration.class)
abstract class AbstractMockBeanOnGenericTests<T extends AbstractMockBeanOnGenericTests.Thing<U>, U extends AbstractMockBeanOnGenericTests.Something> {

	@Autowired
	private T thing;

	@MockBean
	private U something;

	@Test
	void mockBeanShouldResolveConcreteType() {
		assertThat(this.something).isInstanceOf(SomethingImpl.class);
	}

	abstract static class Thing<T extends AbstractMockBeanOnGenericTests.Something> {

		@Autowired
		private T something;

		T getSomething() {
			return this.something;
		}

		void setSomething(T something) {
			this.something = something;
		}

	}

	static class SomethingImpl extends Something {

	}

	static class ThingImpl extends Thing<SomethingImpl> {

	}

	static class Something {

	}

	@Configuration
	static class TestConfiguration {

		@Bean
		ThingImpl thing() {
			return new ThingImpl();
		}

	}

}
