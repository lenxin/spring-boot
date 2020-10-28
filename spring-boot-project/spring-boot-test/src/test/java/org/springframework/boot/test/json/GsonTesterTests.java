package org.springframework.boot.test.json;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import org.springframework.core.ResolvableType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link GsonTester}.
 *

 */
class GsonTesterTests extends AbstractJsonMarshalTesterTests {

	@Test
	void initFieldsWhenTestIsNullShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> GsonTester.initFields(null, new GsonBuilder().create()))
				.withMessageContaining("TestInstance must not be null");
	}

	@Test
	void initFieldsWhenMarshallerIsNullShouldThrowException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> GsonTester.initFields(new InitFieldsTestClass(), (Gson) null))
				.withMessageContaining("Marshaller must not be null");
	}

	@Test
	void initFieldsShouldSetNullFields() {
		InitFieldsTestClass test = new InitFieldsTestClass();
		assertThat(test.test).isNull();
		assertThat(test.base).isNull();
		GsonTester.initFields(test, new GsonBuilder().create());
		assertThat(test.test).isNotNull();
		assertThat(test.base).isNotNull();
		assertThat(test.test.getType().resolve()).isEqualTo(List.class);
		assertThat(test.test.getType().resolveGeneric()).isEqualTo(ExampleObject.class);
	}

	@Override
	protected AbstractJsonMarshalTester<Object> createTester(Class<?> resourceLoadClass, ResolvableType type) {
		return new GsonTester<>(resourceLoadClass, type, new GsonBuilder().create());
	}

	abstract static class InitFieldsBaseClass {

		public GsonTester<ExampleObject> base;

		public GsonTester<ExampleObject> baseSet = new GsonTester<>(InitFieldsBaseClass.class,
				ResolvableType.forClass(ExampleObject.class), new GsonBuilder().create());

	}

	static class InitFieldsTestClass extends InitFieldsBaseClass {

		public GsonTester<List<ExampleObject>> test;

		public GsonTester<ExampleObject> testSet = new GsonTester<>(InitFieldsBaseClass.class,
				ResolvableType.forClass(ExampleObject.class), new GsonBuilder().create());

	}

}
