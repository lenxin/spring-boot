package org.springframework.boot.actuate.health;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link NamedContributorsMapAdapter}.
 *

 */
class NamedContributorsMapAdapterTests {

	@Test
	void createWhenMapIsNullThrowsException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new TestNamedContributorsMapAdapter<>(null, Function.identity()))
				.withMessage("Map must not be null");
	}

	@Test
	void createWhenValueAdapterIsNullThrowsException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new TestNamedContributorsMapAdapter<>(Collections.emptyMap(), null))
				.withMessage("ValueAdapter must not be null");
	}

	@Test
	void createWhenMapContainsNullValueThrowsException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new TestNamedContributorsMapAdapter<>(Collections.singletonMap("test", null),
						Function.identity()))
				.withMessage("Map must not contain null values");
	}

	@Test
	void createWhenMapContainsNullKeyThrowsException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new TestNamedContributorsMapAdapter<>(Collections.singletonMap(null, "test"),
						Function.identity()))
				.withMessage("Map must not contain null keys");
	}

	@Test
	void iterateReturnsAdaptedEntries() {
		TestNamedContributorsMapAdapter<String> adapter = createAdapter();
		Iterator<NamedContributor<String>> iterator = adapter.iterator();
		NamedContributor<String> one = iterator.next();
		NamedContributor<String> two = iterator.next();
		assertThat(iterator.hasNext()).isFalse();
		assertThat(one.getName()).isEqualTo("one");
		assertThat(one.getContributor()).isEqualTo("eno");
		assertThat(two.getName()).isEqualTo("two");
		assertThat(two.getContributor()).isEqualTo("owt");
	}

	@Test
	void getContributorReturnsAdaptedEntry() {
		TestNamedContributorsMapAdapter<String> adapter = createAdapter();
		assertThat(adapter.getContributor("one")).isEqualTo("eno");
		assertThat(adapter.getContributor("two")).isEqualTo("owt");
	}

	@Test
	void getContributorWhenNotInMapReturnsNull() {
		TestNamedContributorsMapAdapter<String> adapter = createAdapter();
		assertThat(adapter.getContributor("missing")).isNull();
	}

	private TestNamedContributorsMapAdapter<String> createAdapter() {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("one", "one");
		map.put("two", "two");
		TestNamedContributorsMapAdapter<String> adapter = new TestNamedContributorsMapAdapter<>(map, this::reverse);
		return adapter;
	}

	private String reverse(CharSequence charSequence) {
		return new StringBuilder(charSequence).reverse().toString();
	}

	static class TestNamedContributorsMapAdapter<V> extends NamedContributorsMapAdapter<V, String> {

		TestNamedContributorsMapAdapter(Map<String, V> map, Function<V, ? extends String> valueAdapter) {
			super(map, valueAdapter);
		}

	}

}
