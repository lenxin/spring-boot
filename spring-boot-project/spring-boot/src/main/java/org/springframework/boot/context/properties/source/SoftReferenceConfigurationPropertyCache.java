package org.springframework.boot.context.properties.source;

import java.lang.ref.SoftReference;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * Simple cache that uses a {@link SoftReference} to cache a value for as long as
 * possible.
 *
 * @param <T> the value type

 */
class SoftReferenceConfigurationPropertyCache<T> implements ConfigurationPropertyCaching {

	private static final Duration UNLIMITED = Duration.ZERO;

	private final boolean neverExpire;

	private volatile Duration timeToLive;

	private volatile SoftReference<T> value = new SoftReference<>(null);

	private volatile Instant lastAccessed = now();

	SoftReferenceConfigurationPropertyCache(boolean neverExpire) {
		this.neverExpire = neverExpire;
	}

	@Override
	public void enable() {
		this.timeToLive = UNLIMITED;
	}

	@Override
	public void disable() {
		this.timeToLive = null;
	}

	@Override
	public void setTimeToLive(Duration timeToLive) {
		this.timeToLive = (timeToLive == null || timeToLive.isZero()) ? null : timeToLive;
	}

	@Override
	public void clear() {
		this.lastAccessed = null;
	}

	/**
	 * Get an value from the cache, creating it if necessary.
	 * @param factory a factory used to create the item if there is no reference to it.
	 * @param refreshAction action called to refresh the value if it has expired
	 * @return the value from the cache
	 */
	T get(Supplier<T> factory, UnaryOperator<T> refreshAction) {
		T value = getValue();
		if (value == null) {
			value = refreshAction.apply(factory.get());
			setValue(value);
		}
		else if (hasExpired()) {
			value = refreshAction.apply(value);
			setValue(value);
		}
		if (!this.neverExpire) {
			this.lastAccessed = now();
		}
		return value;
	}

	private boolean hasExpired() {
		if (this.neverExpire) {
			return false;
		}
		Duration timeToLive = this.timeToLive;
		Instant lastAccessed = this.lastAccessed;
		if (timeToLive == null || lastAccessed == null) {
			return true;
		}
		return !UNLIMITED.equals(timeToLive) && now().isAfter(lastAccessed.plus(timeToLive));
	}

	protected Instant now() {
		return Instant.now();
	}

	protected T getValue() {
		return this.value.get();
	}

	protected void setValue(T value) {
		this.value = new SoftReference<>(value);
	}

}
