package org.springframework.boot.test.json;

import org.assertj.core.api.AssertProvider;

import org.springframework.core.ResolvableType;
import org.springframework.util.Assert;

/**
 * Object content usually created from {@link AbstractJsonMarshalTester}. Generally used
 * only to {@link AssertProvider provide} {@link ObjectContentAssert} to AssertJ
 * {@code assertThat} calls.
 *
 * @param <T> the content type

 * @since 1.4.0
 */
public final class ObjectContent<T> implements AssertProvider<ObjectContentAssert<T>> {

	private final ResolvableType type;

	private final T object;

	/**
	 * Create a new {@link ObjectContent} instance.
	 * @param type the type under test (or {@code null} if not known)
	 * @param object the actual object content
	 */
	public ObjectContent(ResolvableType type, T object) {
		Assert.notNull(object, "Object must not be null");
		this.type = type;
		this.object = object;
	}

	@Override
	public ObjectContentAssert<T> assertThat() {
		return new ObjectContentAssert<>(this.object);
	}

	/**
	 * Return the actual object content.
	 * @return the object content
	 */
	public T getObject() {
		return this.object;
	}

	@Override
	public String toString() {
		String createdFrom = (this.type != null) ? " created from " + this.type : "";
		return "ObjectContent " + this.object + createdFrom;
	}

}
