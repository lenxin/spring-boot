package org.springframework.boot;

import java.util.Collections;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.mock.env.MockPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

/**
 * Tests for {@link DefaultPropertiesPropertySource}.
 *

 */
@ExtendWith(MockitoExtension.class)
class DefaultPropertiesPropertySourceTests {

	@Mock
	private Consumer<DefaultPropertiesPropertySource> action;

	@Captor
	private ArgumentCaptor<DefaultPropertiesPropertySource> captor;

	@Test
	void nameIsDefaultProperties() {
		assertThat(DefaultPropertiesPropertySource.NAME).isEqualTo("defaultProperties");
	}

	@Test
	void createCreatesSource() {
		DefaultPropertiesPropertySource propertySource = new DefaultPropertiesPropertySource(
				Collections.singletonMap("spring", "boot"));
		assertThat(propertySource.getName()).isEqualTo("defaultProperties");
		assertThat(propertySource.getProperty("spring")).isEqualTo("boot");
	}

	@Test
	void hasMatchingNameWhenNameMatchesReturnsTrue() {
		MockPropertySource propertySource = new MockPropertySource("defaultProperties");
		assertThat(DefaultPropertiesPropertySource.hasMatchingName(propertySource)).isTrue();
	}

	@Test
	void hasMatchingNameWhenNameDoesNotMatchReturnsFalse() {
		MockPropertySource propertySource = new MockPropertySource("normalProperties");
		assertThat(DefaultPropertiesPropertySource.hasMatchingName(propertySource)).isFalse();
	}

	@Test
	void hasMatchingNameWhenPropertySourceIsNullReturnsFalse() {
		assertThat(DefaultPropertiesPropertySource.hasMatchingName(null)).isFalse();
	}

	@Test
	void ifNotEmptyWhenNullDoesNotCallAction() {
		DefaultPropertiesPropertySource.ifNotEmpty(null, this.action);
		verifyNoInteractions(this.action);
	}

	@Test
	void ifNotEmptyWhenEmptyDoesNotCallAction() {
		DefaultPropertiesPropertySource.ifNotEmpty(Collections.emptyMap(), this.action);
		verifyNoInteractions(this.action);
	}

	@Test
	void ifNotEmptyHasValueCallsAction() {
		DefaultPropertiesPropertySource.ifNotEmpty(Collections.singletonMap("spring", "boot"), this.action);
		verify(this.action).accept(this.captor.capture());
		assertThat(this.captor.getValue().getProperty("spring")).isEqualTo("boot");
	}

	@Test
	void moveToEndWhenNotPresentDoesNothing() {
		MockEnvironment environment = new MockEnvironment();
		DefaultPropertiesPropertySource.moveToEnd(environment);
	}

	@Test
	void moveToEndWhenPresentMovesToEnd() {
		MockEnvironment environment = new MockEnvironment();
		MutablePropertySources propertySources = environment.getPropertySources();
		propertySources.addLast(new DefaultPropertiesPropertySource(Collections.singletonMap("spring", "boot")));
		propertySources.addLast(new MockPropertySource("test"));
		DefaultPropertiesPropertySource.moveToEnd(environment);
		String[] names = propertySources.stream().map(PropertySource::getName).toArray(String[]::new);
		assertThat(names).containsExactly(MockPropertySource.MOCK_PROPERTIES_PROPERTY_SOURCE_NAME, "test",
				DefaultPropertiesPropertySource.NAME);
	}

}
