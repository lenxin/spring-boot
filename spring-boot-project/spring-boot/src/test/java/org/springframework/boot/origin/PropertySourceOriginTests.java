package org.springframework.boot.origin;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

/**
 * Tests for {@link PropertySourceOrigin}.
 *

 */
class PropertySourceOriginTests {

	@Test
	void createWhenPropertySourceIsNullShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new PropertySourceOrigin(null, "name"))
				.withMessageContaining("PropertySource must not be null");
	}

	@Test
	void createWhenPropertyNameIsNullShouldThrowException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new PropertySourceOrigin(mock(PropertySource.class), null))
				.withMessageContaining("PropertyName must not be empty");
	}

	@Test
	void createWhenPropertyNameIsEmptyShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new PropertySourceOrigin(mock(PropertySource.class), ""))
				.withMessageContaining("PropertyName must not be empty");
	}

	@Test
	void getPropertySourceShouldReturnPropertySource() {
		MapPropertySource propertySource = new MapPropertySource("test", new HashMap<>());
		PropertySourceOrigin origin = new PropertySourceOrigin(propertySource, "foo");
		assertThat(origin.getPropertySource()).isEqualTo(propertySource);
	}

	@Test
	void getPropertyNameShouldReturnPropertyName() {
		MapPropertySource propertySource = new MapPropertySource("test", new HashMap<>());
		PropertySourceOrigin origin = new PropertySourceOrigin(propertySource, "foo");
		assertThat(origin.getPropertyName()).isEqualTo("foo");
	}

	@Test
	void toStringShouldShowDetails() {
		MapPropertySource propertySource = new MapPropertySource("test", new HashMap<>());
		PropertySourceOrigin origin = new PropertySourceOrigin(propertySource, "foo");
		assertThat(origin.toString()).isEqualTo("\"foo\" from property source \"test\"");
	}

	@Test
	@SuppressWarnings("unchecked")
	void getWhenPropertySourceSupportsOriginLookupShouldReturnOrigin() {
		Origin origin = mock(Origin.class);
		PropertySource<?> propertySource = mock(PropertySource.class,
				withSettings().extraInterfaces(OriginLookup.class));
		OriginLookup<String> originCapablePropertySource = (OriginLookup<String>) propertySource;
		given(originCapablePropertySource.getOrigin("foo")).willReturn(origin);
		assertThat(PropertySourceOrigin.get(propertySource, "foo")).isSameAs(origin);
	}

	@Test
	void getWhenPropertySourceSupportsOriginLookupButNoOriginShouldWrap() {
		PropertySource<?> propertySource = mock(PropertySource.class,
				withSettings().extraInterfaces(OriginLookup.class));
		assertThat(PropertySourceOrigin.get(propertySource, "foo")).isInstanceOf(PropertySourceOrigin.class);
	}

	@Test
	void getWhenPropertySourceIsNotOriginAwareShouldWrap() {
		MapPropertySource propertySource = new MapPropertySource("test", new HashMap<>());
		PropertySourceOrigin origin = new PropertySourceOrigin(propertySource, "foo");
		assertThat(origin.getPropertySource()).isEqualTo(propertySource);
		assertThat(origin.getPropertyName()).isEqualTo("foo");
	}

}
