package org.springframework.boot.env;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginTrackedValue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link OriginTrackedMapPropertySource}.
 *


 */
class OriginTrackedMapPropertySourceTests {

	private Map<String, Object> map = new LinkedHashMap<>();

	private OriginTrackedMapPropertySource source = new OriginTrackedMapPropertySource("test", this.map);

	private Origin origin = mock(Origin.class);

	@Test
	void getPropertyWhenMissingShouldReturnNull() {
		assertThat(this.source.getProperty("test")).isNull();
	}

	@Test
	void getPropertyWhenNonTrackedShouldReturnValue() {
		this.map.put("test", "foo");
		assertThat(this.source.getProperty("test")).isEqualTo("foo");
	}

	@Test
	void getPropertyWhenTrackedShouldReturnValue() {
		this.map.put("test", OriginTrackedValue.of("foo", this.origin));
		assertThat(this.source.getProperty("test")).isEqualTo("foo");
	}

	@Test
	void getPropertyOriginWhenMissingShouldReturnNull() {
		assertThat(this.source.getOrigin("test")).isNull();
	}

	@Test
	void getPropertyOriginWhenNonTrackedShouldReturnNull() {
		this.map.put("test", "foo");
		assertThat(this.source.getOrigin("test")).isNull();
	}

	@Test
	void getPropertyOriginWhenTrackedShouldReturnOrigin() {
		this.map.put("test", OriginTrackedValue.of("foo", this.origin));
		assertThat(this.source.getOrigin("test")).isEqualTo(this.origin);
	}

}
