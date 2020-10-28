package org.springframework.boot.test.mock.mockito;

import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.mockito.mock.MockCreationSettings;

import org.springframework.boot.test.mock.mockito.example.ExampleExtraInterface;
import org.springframework.boot.test.mock.mockito.example.ExampleService;
import org.springframework.core.ResolvableType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link MockDefinition}.
 *

 */
class MockDefinitionTests {

	private static final ResolvableType EXAMPLE_SERVICE_TYPE = ResolvableType.forClass(ExampleService.class);

	@Test
	void classToMockMustNotBeNull() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new MockDefinition(null, null, null, null, false, null, null))
				.withMessageContaining("TypeToMock must not be null");
	}

	@Test
	void createWithDefaults() {
		MockDefinition definition = new MockDefinition(null, EXAMPLE_SERVICE_TYPE, null, null, false, null, null);
		assertThat(definition.getName()).isNull();
		assertThat(definition.getTypeToMock()).isEqualTo(EXAMPLE_SERVICE_TYPE);
		assertThat(definition.getExtraInterfaces()).isEmpty();
		assertThat(definition.getAnswer()).isEqualTo(Answers.RETURNS_DEFAULTS);
		assertThat(definition.isSerializable()).isFalse();
		assertThat(definition.getReset()).isEqualTo(MockReset.AFTER);
		assertThat(definition.getQualifier()).isNull();
	}

	@Test
	void createExplicit() {
		QualifierDefinition qualifier = mock(QualifierDefinition.class);
		MockDefinition definition = new MockDefinition("name", EXAMPLE_SERVICE_TYPE,
				new Class<?>[] { ExampleExtraInterface.class }, Answers.RETURNS_SMART_NULLS, true, MockReset.BEFORE,
				qualifier);
		assertThat(definition.getName()).isEqualTo("name");
		assertThat(definition.getTypeToMock()).isEqualTo(EXAMPLE_SERVICE_TYPE);
		assertThat(definition.getExtraInterfaces()).containsExactly(ExampleExtraInterface.class);
		assertThat(definition.getAnswer()).isEqualTo(Answers.RETURNS_SMART_NULLS);
		assertThat(definition.isSerializable()).isTrue();
		assertThat(definition.getReset()).isEqualTo(MockReset.BEFORE);
		assertThat(definition.isProxyTargetAware()).isFalse();
		assertThat(definition.getQualifier()).isEqualTo(qualifier);
	}

	@Test
	void createMock() {
		MockDefinition definition = new MockDefinition("name", EXAMPLE_SERVICE_TYPE,
				new Class<?>[] { ExampleExtraInterface.class }, Answers.RETURNS_SMART_NULLS, true, MockReset.BEFORE,
				null);
		ExampleService mock = definition.createMock();
		MockCreationSettings<?> settings = Mockito.mockingDetails(mock).getMockCreationSettings();
		assertThat(mock).isInstanceOf(ExampleService.class);
		assertThat(mock).isInstanceOf(ExampleExtraInterface.class);
		assertThat(settings.getMockName().toString()).isEqualTo("name");
		assertThat(settings.getDefaultAnswer()).isEqualTo(Answers.RETURNS_SMART_NULLS);
		assertThat(settings.isSerializable()).isTrue();
		assertThat(MockReset.get(mock)).isEqualTo(MockReset.BEFORE);
	}

}
