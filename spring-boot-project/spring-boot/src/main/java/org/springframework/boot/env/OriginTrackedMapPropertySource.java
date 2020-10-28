package org.springframework.boot.env;

import java.util.Map;

import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginLookup;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.core.env.MapPropertySource;

/**
 * {@link OriginLookup} backed by a {@link Map} containing {@link OriginTrackedValue
 * OriginTrackedValues}.
 *


 * @since 2.0.0
 * @see OriginTrackedValue
 */
public final class OriginTrackedMapPropertySource extends MapPropertySource implements OriginLookup<String> {

	private final boolean immutable;

	/**
	 * Create a new {@link OriginTrackedMapPropertySource} instance.
	 * @param name the property source name
	 * @param source the underlying map source
	 */
	@SuppressWarnings("rawtypes")
	public OriginTrackedMapPropertySource(String name, Map source) {
		this(name, source, false);
	}

	/**
	 * Create a new {@link OriginTrackedMapPropertySource} instance.
	 * @param name the property source name
	 * @param source the underlying map source
	 * @param immutable if the underlying source is immutable and guaranteed not to change
	 * @since 2.2.0
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public OriginTrackedMapPropertySource(String name, Map source, boolean immutable) {
		super(name, source);
		this.immutable = immutable;
	}

	@Override
	public Object getProperty(String name) {
		Object value = super.getProperty(name);
		if (value instanceof OriginTrackedValue) {
			return ((OriginTrackedValue) value).getValue();
		}
		return value;
	}

	@Override
	public Origin getOrigin(String name) {
		Object value = super.getProperty(name);
		if (value instanceof OriginTrackedValue) {
			return ((OriginTrackedValue) value).getOrigin();
		}
		return null;
	}

	@Override
	public boolean isImmutable() {
		return this.immutable;
	}

}
