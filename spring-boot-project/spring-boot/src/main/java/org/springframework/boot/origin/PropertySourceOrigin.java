package org.springframework.boot.origin;

import org.springframework.core.env.PropertySource;
import org.springframework.util.Assert;

/**
 * {@link Origin} from a {@link PropertySource}.
 *

 * @since 2.0.0
 */
public class PropertySourceOrigin implements Origin {

	private final PropertySource<?> propertySource;

	private final String propertyName;

	/**
	 * Create a new {@link PropertySourceOrigin} instance.
	 * @param propertySource the property source
	 * @param propertyName the name from the property source
	 */
	public PropertySourceOrigin(PropertySource<?> propertySource, String propertyName) {
		Assert.notNull(propertySource, "PropertySource must not be null");
		Assert.hasLength(propertyName, "PropertyName must not be empty");
		this.propertySource = propertySource;
		this.propertyName = propertyName;
	}

	/**
	 * Return the origin {@link PropertySource}.
	 * @return the origin property source
	 */
	public PropertySource<?> getPropertySource() {
		return this.propertySource;
	}

	/**
	 * Return the property name that was used when obtaining the original value from the
	 * {@link #getPropertySource() property source}.
	 * @return the origin property name
	 */
	public String getPropertyName() {
		return this.propertyName;
	}

	@Override
	public String toString() {
		return "\"" + this.propertyName + "\" from property source \"" + this.propertySource.getName() + "\"";
	}

	/**
	 * Get an {@link Origin} for the given {@link PropertySource} and
	 * {@code propertyName}. Will either return an {@link OriginLookup} result or a
	 * {@link PropertySourceOrigin}.
	 * @param propertySource the origin property source
	 * @param name the property name
	 * @return the property origin
	 */
	public static Origin get(PropertySource<?> propertySource, String name) {
		Origin origin = OriginLookup.getOrigin(propertySource, name);
		return (origin != null) ? origin : new PropertySourceOrigin(propertySource, name);
	}

}
