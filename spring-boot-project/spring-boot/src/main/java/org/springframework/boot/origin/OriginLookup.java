package org.springframework.boot.origin;

/**
 * An interface that may be implemented by an object that can lookup {@link Origin}
 * information from a given key. Can be used to add origin support to existing classes.
 *
 * @param <K> the lookup key type

 * @since 2.0.0
 */
@FunctionalInterface
public interface OriginLookup<K> {

	/**
	 * Return the origin of the given key or {@code null} if the origin cannot be
	 * determined.
	 * @param key the key to lookup
	 * @return the origin of the key or {@code null}
	 */
	Origin getOrigin(K key);

	/**
	 * Return {@code true} if this lookup is immutable and has contents that will never
	 * change.
	 * @return if the lookup is immutable
	 * @since 2.2.0
	 */
	default boolean isImmutable() {
		return false;
	}

	/**
	 * Attempt to lookup the origin from the given source. If the source is not a
	 * {@link OriginLookup} or if an exception occurs during lookup then {@code null} is
	 * returned.
	 * @param source the source object
	 * @param key the key to lookup
	 * @param <K> the key type
	 * @return an {@link Origin} or {@code null}
	 */
	@SuppressWarnings("unchecked")
	static <K> Origin getOrigin(Object source, K key) {
		if (!(source instanceof OriginLookup)) {
			return null;
		}
		try {
			return ((OriginLookup<K>) source).getOrigin(key);
		}
		catch (Throwable ex) {
			return null;
		}
	}

}
