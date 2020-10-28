package org.springframework.boot.autoconfigure.orm.jpa;

import java.util.Map;

/**
 * Callback interface that can be implemented by beans wishing to customize the Hibernate
 * properties before it is used by an auto-configured {@code EntityManagerFactory}.
 *

 * @since 2.0.0
 */
@FunctionalInterface
public interface HibernatePropertiesCustomizer {

	/**
	 * Customize the specified JPA vendor properties.
	 * @param hibernateProperties the JPA vendor properties to customize
	 */
	void customize(Map<String, Object> hibernateProperties);

}
