package org.springframework.boot.origin;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link Origin}.
 *

 */
class OriginTests {

	@Test
	void getParentWhenDefaultIsNull() {
		Origin origin = new Origin() {
		};
		assertThat(origin.getParent()).isNull();
	}

	@Test
	void fromWhenSourceIsNullReturnsNull() {
		assertThat(Origin.from(null)).isNull();
	}

	@Test
	void fromWhenSourceIsRegularObjectReturnsNull() {
		Object source = new Object();
		assertThat(Origin.from(source)).isNull();
	}

	@Test
	void fromWhenSourceIsOriginReturnsSource() {
		Origin origin = mock(Origin.class);
		assertThat(Origin.from(origin)).isEqualTo(origin);
	}

	@Test
	void fromWhenSourceIsOriginProviderReturnsProvidedOrigin() {
		Origin origin = mock(Origin.class);
		OriginProvider originProvider = mock(OriginProvider.class);
		given(originProvider.getOrigin()).willReturn(origin);
		assertThat(Origin.from(origin)).isEqualTo(origin);
	}

	@Test
	void fromWhenSourceIsThrowableUsesCause() {
		Origin origin = mock(Origin.class);
		Exception exception = new RuntimeException(new TestException(origin, null));
		assertThat(Origin.from(exception)).isEqualTo(origin);
	}

	@Test
	void fromWhenSourceIsThrowableAndOriginProviderThatReturnsNullUsesCause() {
		Origin origin = mock(Origin.class);
		Exception exception = new TestException(null, new TestException(origin, null));
		assertThat(Origin.from(exception)).isEqualTo(origin);
	}

	@Test
	void parentsFromWhenSourceIsNullReturnsEmptyList() {
		assertThat(Origin.parentsFrom(null)).isEmpty();
	}

	@Test
	void parentsFromReturnsParents() {
		Origin o1 = MockOrigin.of("1");
		Origin o2 = MockOrigin.of("2", o1);
		Origin o3 = MockOrigin.of("3", o2);
		List<Origin> parents = Origin.parentsFrom(o3);
		assertThat(parents).containsExactly(o2, o1);
	}

	static class TestException extends RuntimeException implements OriginProvider {

		private final Origin origin;

		TestException(Origin origin, Throwable cause) {
			super(cause);
			this.origin = origin;
		}

		@Override
		public Origin getOrigin() {
			return this.origin;
		}

	}

}
