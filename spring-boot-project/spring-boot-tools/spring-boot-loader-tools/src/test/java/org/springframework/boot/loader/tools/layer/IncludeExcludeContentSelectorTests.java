package org.springframework.boot.loader.tools.layer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.boot.loader.tools.Layer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link IncludeExcludeContentSelector}.
 *


 */
class IncludeExcludeContentSelectorTests {

	private static final Layer LAYER = new Layer("test");

	@Test
	void createWhenLayerIsNullThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(
				() -> new IncludeExcludeContentSelector<>(null, Collections.emptyList(), Collections.emptyList()))
				.withMessage("Layer must not be null");
	}

	@Test
	void createWhenFactoryIsNullThrowsException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new IncludeExcludeContentSelector<>(LAYER, null, null, null))
				.withMessage("FilterFactory must not be null");
	}

	@Test
	void getLayerReturnsLayer() {
		IncludeExcludeContentSelector<?> selector = new IncludeExcludeContentSelector<>(LAYER, null, null);
		assertThat(selector.getLayer()).isEqualTo(LAYER);
	}

	@Test
	void containsWhenEmptyIncludesAndEmptyExcludesReturnsTrue() {
		List<String> includes = Arrays.asList();
		List<String> excludes = Arrays.asList();
		IncludeExcludeContentSelector<String> selector = new IncludeExcludeContentSelector<>(LAYER, includes, excludes,
				TestContentsFilter::new);
		assertThat(selector.contains("A")).isTrue();
	}

	@Test
	void containsWhenNullIncludesAndEmptyExcludesReturnsTrue() {
		List<String> includes = null;
		List<String> excludes = null;
		IncludeExcludeContentSelector<String> selector = new IncludeExcludeContentSelector<>(LAYER, includes, excludes,
				TestContentsFilter::new);
		assertThat(selector.contains("A")).isTrue();
	}

	@Test
	void containsWhenEmptyIncludesAndNotExcludedReturnsTrue() {
		List<String> includes = Arrays.asList();
		List<String> excludes = Arrays.asList("B");
		IncludeExcludeContentSelector<String> selector = new IncludeExcludeContentSelector<>(LAYER, includes, excludes,
				TestContentsFilter::new);
		assertThat(selector.contains("A")).isTrue();
	}

	@Test
	void containsWhenEmptyIncludesAndExcludedReturnsFalse() {
		List<String> includes = Arrays.asList();
		List<String> excludes = Arrays.asList("A");
		IncludeExcludeContentSelector<String> selector = new IncludeExcludeContentSelector<>(LAYER, includes, excludes,
				TestContentsFilter::new);
		assertThat(selector.contains("A")).isFalse();
	}

	@Test
	void containsWhenIncludedAndEmptyExcludesReturnsTrue() {
		List<String> includes = Arrays.asList("A", "B");
		List<String> excludes = Arrays.asList();
		IncludeExcludeContentSelector<String> selector = new IncludeExcludeContentSelector<>(LAYER, includes, excludes,
				TestContentsFilter::new);
		assertThat(selector.contains("B")).isTrue();
	}

	@Test
	void containsWhenIncludedAndNotExcludedReturnsTrue() {
		List<String> includes = Arrays.asList("A", "B");
		List<String> excludes = Arrays.asList("C", "D");
		IncludeExcludeContentSelector<String> selector = new IncludeExcludeContentSelector<>(LAYER, includes, excludes,
				TestContentsFilter::new);
		assertThat(selector.contains("B")).isTrue();
	}

	@Test
	void containsWhenIncludedAndExcludedReturnsFalse() {
		List<String> includes = Arrays.asList("A", "B");
		List<String> excludes = Arrays.asList("C", "D");
		IncludeExcludeContentSelector<String> selector = new IncludeExcludeContentSelector<>(LAYER, includes, excludes,
				TestContentsFilter::new);
		assertThat(selector.contains("C")).isFalse();
	}

	/**
	 * {@link ContentFilter} used for testing.
	 */
	static class TestContentsFilter implements ContentFilter<String> {

		private final String match;

		TestContentsFilter(String match) {
			this.match = match;
		}

		@Override
		public boolean matches(String item) {
			return this.match.equals(item);
		}

	}

}
