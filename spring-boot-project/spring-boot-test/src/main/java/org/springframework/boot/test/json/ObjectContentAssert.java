package org.springframework.boot.test.json;

import java.util.Map;

import org.assertj.core.api.AbstractMapAssert;
import org.assertj.core.api.AbstractObjectArrayAssert;
import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.assertj.core.internal.Objects;

/**
 * AssertJ {@link Assert} for {@link ObjectContent}.
 *
 * @param <A> the actual type

 * @since 1.4.0
 */
public class ObjectContentAssert<A> extends AbstractObjectAssert<ObjectContentAssert<A>, A> {

	protected ObjectContentAssert(A actual) {
		super(actual, ObjectContentAssert.class);
	}

	/**
	 * Verifies that the actual value is an array, and returns an array assertion, to
	 * allow chaining of array-specific assertions from this call.
	 * @return an array assertion object
	 */
	public AbstractObjectArrayAssert<?, Object> asArray() {
		Objects.instance().assertIsInstanceOf(this.info, this.actual, Object[].class);
		return Assertions.assertThat((Object[]) this.actual);
	}

	/**
	 * Verifies that the actual value is a map, and returns a map assertion, to allow
	 * chaining of map-specific assertions from this call.
	 * @return a map assertion object
	 */
	@SuppressWarnings("unchecked")
	public AbstractMapAssert<?, ?, Object, Object> asMap() {
		Objects.instance().assertIsInstanceOf(this.info, this.actual, Map.class);
		return Assertions.assertThat((Map<Object, Object>) this.actual);
	}

}
