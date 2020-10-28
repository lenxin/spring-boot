package org.springframework.boot.test.autoconfigure.properties;

/**
 * Enum used to control when {@link PropertyMapping @PropertyMapping} is skipped.
 *

 * @since 1.4.0
 */
public enum SkipPropertyMapping {

	/**
	 * Skip mapping the property.
	 */
	YES,

	/**
	 * Skip mapping the property when the default attribute value is specified.
	 */
	ON_DEFAULT_VALUE,

	/**
	 * Don't skip mapping the property.
	 */
	NO

}
