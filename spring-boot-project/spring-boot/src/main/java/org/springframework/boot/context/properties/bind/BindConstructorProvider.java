package org.springframework.boot.context.properties.bind;

import java.lang.reflect.Constructor;

/**
 * Strategy interface used to determine a specific constructor to use when binding.
 *

 * @since 2.2.1
 */
@FunctionalInterface
public interface BindConstructorProvider {

	/**
	 * Default {@link BindConstructorProvider} implementation that only returns a value
	 * when there's a single constructor and when the bindable has no existing value.
	 */
	BindConstructorProvider DEFAULT = new DefaultBindConstructorProvider();

	/**
	 * Return the bind constructor to use for the given bindable, or {@code null} if
	 * constructor binding is not supported.
	 * @param bindable the bindable to check
	 * @param isNestedConstructorBinding if this binding is nested within a constructor
	 * binding
	 * @return the bind constructor or {@code null}
	 */
	Constructor<?> getBindConstructor(Bindable<?> bindable, boolean isNestedConstructorBinding);

}
