package org.springframework.boot.test.json;

import java.util.List;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.junit.jupiter.api.Test;

import org.springframework.core.ResolvableType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link JsonbTester}.
 *

 */
class JsonbTesterTests extends AbstractJsonMarshalTesterTests {

	@Test
	void initFieldsWhenTestIsNullShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> JsonbTester.initFields(null, JsonbBuilder.create()))
				.withMessageContaining("TestInstance must not be null");
	}

	@Test
	void initFieldsWhenMarshallerIsNullShouldThrowException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> JsonbTester.initFields(new InitFieldsTestClass(), (Jsonb) null))
				.withMessageContaining("Marshaller must not be null");
	}

	@Test
	void initFieldsShouldSetNullFields() {
		InitFieldsTestClass test = new InitFieldsTestClass();
		assertThat(test.test).isNull();
		assertThat(test.base).isNull();
		JsonbTester.initFields(test, JsonbBuilder.create());
		assertThat(test.test).isNotNull();
		assertThat(test.base).isNotNull();
		assertThat(test.test.getType().resolve()).isEqualTo(List.class);
		assertThat(test.test.getType().resolveGeneric()).isEqualTo(ExampleObject.class);
	}

	@Override
	protected AbstractJsonMarshalTester<Object> createTester(Class<?> resourceLoadClass, ResolvableType type) {
		return new JsonbTester<>(resourceLoadClass, type, JsonbBuilder.create());
	}

	abstract static class InitFieldsBaseClass {

		public JsonbTester<ExampleObject> base;

		public JsonbTester<ExampleObject> baseSet = new JsonbTester<>(InitFieldsBaseClass.class,
				ResolvableType.forClass(ExampleObject.class), JsonbBuilder.create());

	}

	static class InitFieldsTestClass extends InitFieldsBaseClass {

		public JsonbTester<List<ExampleObject>> test;

		public JsonbTester<ExampleObject> testSet = new JsonbTester<>(InitFieldsBaseClass.class,
				ResolvableType.forClass(ExampleObject.class), JsonbBuilder.create());

	}

}
