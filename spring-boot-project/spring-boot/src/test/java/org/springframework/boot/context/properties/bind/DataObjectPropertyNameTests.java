package org.springframework.boot.context.properties.bind;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DataObjectPropertyName}.
 *


 */
class DataObjectPropertyNameTests {

	@Test
	void toDashedCaseConvertsValue() {
		assertThat(DataObjectPropertyName.toDashedForm("Foo")).isEqualTo("foo");
		assertThat(DataObjectPropertyName.toDashedForm("foo")).isEqualTo("foo");
		assertThat(DataObjectPropertyName.toDashedForm("fooBar")).isEqualTo("foo-bar");
		assertThat(DataObjectPropertyName.toDashedForm("foo_bar")).isEqualTo("foo-bar");
		assertThat(DataObjectPropertyName.toDashedForm("_foo_bar")).isEqualTo("-foo-bar");
		assertThat(DataObjectPropertyName.toDashedForm("foo_Bar")).isEqualTo("foo-bar");
	}

	@Test
	void toDashedFormWhenContainsIndexedAddsNoDashToIndex() throws Exception {
		assertThat(DataObjectPropertyName.toDashedForm("test[fooBar]")).isEqualTo("test[fooBar]");
		assertThat(DataObjectPropertyName.toDashedForm("testAgain[fooBar]")).isEqualTo("test-again[fooBar]");
	}

}
