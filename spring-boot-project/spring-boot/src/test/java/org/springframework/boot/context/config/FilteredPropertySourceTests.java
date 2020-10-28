package org.springframework.boot.context.config;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link FilteredPropertySource}.
 *

 */
@Deprecated
class FilteredPropertySourceTests {

	@Test
	void applyWhenHasNoSourceShouldRunOperation() {
		ConfigurableEnvironment environment = new MockEnvironment();
		TestOperation operation = new TestOperation();
		FilteredPropertySource.apply(environment, "test", Collections.emptySet(), operation);
		assertThat(operation.isCalled()).isTrue();
		assertThat(operation.getOriginal()).isNull();
	}

	@Test
	void applyWhenHasSourceShouldRunWithReplacedSource() {
		ConfigurableEnvironment environment = new MockEnvironment();
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("regular", "regularValue");
		map.put("filtered", "filteredValue");
		PropertySource<?> propertySource = new MapPropertySource("test", map);
		environment.getPropertySources().addFirst(propertySource);
		TestOperation operation = new TestOperation(() -> {
			assertThat(environment.containsProperty("regular")).isTrue();
			assertThat(environment.containsProperty("filtered")).isFalse();
		});
		FilteredPropertySource.apply(environment, "test", Collections.singleton("filtered"), operation);
		assertThat(operation.isCalled()).isTrue();
		assertThat(operation.getOriginal()).isSameAs(propertySource);
		assertThat(environment.getPropertySources().get("test")).isSameAs(propertySource);

	}

	static class TestOperation implements Consumer<PropertySource<?>> {

		private boolean called;

		private PropertySource<?> original;

		private Runnable operation;

		TestOperation() {
			this(null);
		}

		TestOperation(Runnable operation) {
			this.operation = operation;
		}

		@Override
		public void accept(PropertySource<?> original) {
			this.called = true;
			this.original = original;
			if (this.operation != null) {
				this.operation.run();
			}
		}

		boolean isCalled() {
			return this.called;
		}

		PropertySource<?> getOriginal() {
			return this.original;
		}

	}

}
