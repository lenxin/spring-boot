package org.springframework.boot.context.properties.source;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for {@link FilteredIterableConfigurationPropertiesSource}.
 *


 */
class FilteredIterableConfigurationPropertiesSourceTests extends FilteredConfigurationPropertiesSourceTests {

	@Test
	void iteratorShouldFilterNames() {
		MockConfigurationPropertySource source = (MockConfigurationPropertySource) createTestSource();
		IterableConfigurationPropertySource filtered = source.filter(this::noBrackets);
		assertThat(filtered.iterator()).toIterable().extracting(ConfigurationPropertyName::toString)
				.containsExactly("a", "b", "c");
	}

	@Override
	protected ConfigurationPropertySource convertSource(MockConfigurationPropertySource source) {
		return source;
	}

	@Test
	void containsDescendantOfShouldUseContents() {
		MockConfigurationPropertySource source = new MockConfigurationPropertySource();
		source.put("foo.bar.baz", "1");
		source.put("foo.bar[0]", "1");
		source.put("faf.bar[0]", "1");
		IterableConfigurationPropertySource filtered = source.filter(this::noBrackets);
		assertThat(filtered.containsDescendantOf(ConfigurationPropertyName.of("foo")))
				.isEqualTo(ConfigurationPropertyState.PRESENT);
		assertThat(filtered.containsDescendantOf(ConfigurationPropertyName.of("faf")))
				.isEqualTo(ConfigurationPropertyState.ABSENT);
	}

	private boolean noBrackets(ConfigurationPropertyName name) {
		return !name.toString().contains("[");
	}

}
