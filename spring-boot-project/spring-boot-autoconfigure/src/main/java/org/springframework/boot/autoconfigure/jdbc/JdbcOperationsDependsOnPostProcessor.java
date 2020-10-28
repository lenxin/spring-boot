package org.springframework.boot.autoconfigure.jdbc;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.AbstractDependsOnBeanFactoryPostProcessor;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * {@link BeanFactoryPostProcessor} that can be used to dynamically declare that all
 * {@link JdbcOperations} beans should "depend on" one or more specific beans.
 *





 * @since 2.0.4
 * @see BeanDefinition#setDependsOn(String[])
 */
public class JdbcOperationsDependsOnPostProcessor extends AbstractDependsOnBeanFactoryPostProcessor {

	/**
	 * Creates a new {@code JdbcOperationsDependsOnPostProcessor} that will set up
	 * dependencies upon beans with the given names.
	 * @param dependsOn names of the beans to depend upon
	 */
	public JdbcOperationsDependsOnPostProcessor(String... dependsOn) {
		super(JdbcOperations.class, dependsOn);
	}

	/**
	 * Creates a new {@code JdbcOperationsDependsOnPostProcessor} that will set up
	 * dependencies upon beans with the given types.
	 * @param dependsOn types of the beans to depend upon
	 * @since 2.1.8
	 */
	public JdbcOperationsDependsOnPostProcessor(Class<?>... dependsOn) {
		super(JdbcOperations.class, dependsOn);
	}

}
