package org.springframework.boot.test.json;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.core.ResolvableType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link JacksonTester}.
 *

 */
class JacksonTesterTests extends AbstractJsonMarshalTesterTests {

	@Test
	void initFieldsWhenTestIsNullShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> JacksonTester.initFields(null, new ObjectMapper()))
				.withMessageContaining("TestInstance must not be null");
	}

	@Test
	void initFieldsWhenMarshallerIsNullShouldThrowException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> JacksonTester.initFields(new InitFieldsTestClass(), (ObjectMapper) null))
				.withMessageContaining("Marshaller must not be null");
	}

	@Test
	void initFieldsShouldSetNullFields() {
		InitFieldsTestClass test = new InitFieldsTestClass();
		assertThat(test.test).isNull();
		assertThat(test.base).isNull();
		JacksonTester.initFields(test, new ObjectMapper());
		assertThat(test.test).isNotNull();
		assertThat(test.base).isNotNull();
		assertThat(test.test.getType().resolve()).isEqualTo(List.class);
		assertThat(test.test.getType().resolveGeneric()).isEqualTo(ExampleObject.class);
	}

	@Override
	protected AbstractJsonMarshalTester<Object> createTester(Class<?> resourceLoadClass, ResolvableType type) {
		return new JacksonTester<>(resourceLoadClass, type, new ObjectMapper());
	}

	abstract static class InitFieldsBaseClass {

		public JacksonTester<ExampleObject> base;

		public JacksonTester<ExampleObject> baseSet = new JacksonTester<>(InitFieldsBaseClass.class,
				ResolvableType.forClass(ExampleObject.class), new ObjectMapper());

	}

	static class InitFieldsTestClass extends InitFieldsBaseClass {

		public JacksonTester<List<ExampleObject>> test;

		public JacksonTester<ExampleObject> testSet = new JacksonTester<>(InitFieldsBaseClass.class,
				ResolvableType.forClass(ExampleObject.class), new ObjectMapper());

	}

}
