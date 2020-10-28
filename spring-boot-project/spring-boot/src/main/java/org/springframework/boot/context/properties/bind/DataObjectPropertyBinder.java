package org.springframework.boot.context.properties.bind;

/**
 * Binder that can be used by {@link DataObjectBinder} implementations to bind the data
 * object properties.
 *


 */
interface DataObjectPropertyBinder {

	/**
	 * Bind the given property.
	 * @param propertyName the property name (in lowercase dashed form, e.g.
	 * {@code first-name})
	 * @param target the target bindable
	 * @return the bound value or {@code null}
	 */
	Object bindProperty(String propertyName, Bindable<?> target);

}
