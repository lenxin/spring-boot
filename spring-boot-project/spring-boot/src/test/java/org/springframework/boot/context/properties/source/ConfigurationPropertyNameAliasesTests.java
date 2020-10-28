package org.springframework.boot.context.properties.source;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link ConfigurationPropertyNameAliases}.
 *


 */
class ConfigurationPropertyNameAliasesTests {

	@Test
	void createWithStringWhenNullNameShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new ConfigurationPropertyNameAliases((String) null))
				.withMessageContaining("Name must not be null");
	}

	@Test
	void createWithStringShouldAddMapping() {
		ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases("foo", "bar", "baz");
		assertThat(aliases.getAliases(ConfigurationPropertyName.of("foo")))
				.containsExactly(ConfigurationPropertyName.of("bar"), ConfigurationPropertyName.of("baz"));
	}

	@Test
	void createWithNameShouldAddMapping() {
		ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases(
				ConfigurationPropertyName.of("foo"), ConfigurationPropertyName.of("bar"),
				ConfigurationPropertyName.of("baz"));
		assertThat(aliases.getAliases(ConfigurationPropertyName.of("foo")))
				.containsExactly(ConfigurationPropertyName.of("bar"), ConfigurationPropertyName.of("baz"));
	}

	@Test
	void addAliasesFromStringShouldAddMapping() {
		ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();
		aliases.addAliases("foo", "bar", "baz");
		assertThat(aliases.getAliases(ConfigurationPropertyName.of("foo")))
				.containsExactly(ConfigurationPropertyName.of("bar"), ConfigurationPropertyName.of("baz"));
	}

	@Test
	void addAliasesFromNameShouldAddMapping() {
		ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();
		aliases.addAliases(ConfigurationPropertyName.of("foo"), ConfigurationPropertyName.of("bar"),
				ConfigurationPropertyName.of("baz"));
		assertThat(aliases.getAliases(ConfigurationPropertyName.of("foo")))
				.containsExactly(ConfigurationPropertyName.of("bar"), ConfigurationPropertyName.of("baz"));
	}

	@Test
	void addWhenHasExistingShouldAddAdditionalMappings() {
		ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();
		aliases.addAliases("foo", "bar");
		aliases.addAliases("foo", "baz");
		assertThat(aliases.getAliases(ConfigurationPropertyName.of("foo")))
				.containsExactly(ConfigurationPropertyName.of("bar"), ConfigurationPropertyName.of("baz"));
	}

	@Test
	void getAliasesWhenNotMappedShouldReturnEmptyList() {
		ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();
		assertThat(aliases.getAliases(ConfigurationPropertyName.of("foo"))).isEmpty();
	}

	@Test
	void getAliasesWhenMappedShouldReturnMapping() {
		ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();
		aliases.addAliases("foo", "bar");
		assertThat(aliases.getAliases(ConfigurationPropertyName.of("foo")))
				.containsExactly(ConfigurationPropertyName.of("bar"));
	}

	@Test
	void getNameForAliasWhenHasMappingShouldReturnName() {
		ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();
		aliases.addAliases("foo", "bar");
		aliases.addAliases("foo", "baz");
		assertThat((Object) aliases.getNameForAlias(ConfigurationPropertyName.of("bar")))
				.isEqualTo(ConfigurationPropertyName.of("foo"));
		assertThat((Object) aliases.getNameForAlias(ConfigurationPropertyName.of("baz")))
				.isEqualTo(ConfigurationPropertyName.of("foo"));
	}

	@Test
	void getNameForAliasWhenNotMappedShouldReturnNull() {
		ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();
		aliases.addAliases("foo", "bar");
		assertThat((Object) aliases.getNameForAlias(ConfigurationPropertyName.of("baz"))).isNull();
	}

}
