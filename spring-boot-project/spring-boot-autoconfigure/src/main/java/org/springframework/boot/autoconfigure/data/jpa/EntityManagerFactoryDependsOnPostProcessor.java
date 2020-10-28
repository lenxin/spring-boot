package org.springframework.boot.autoconfigure.data.jpa;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.AbstractDependsOnBeanFactoryPostProcessor;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;

/**
 * {@link BeanFactoryPostProcessor} that can be used to dynamically declare that all
 * {@link EntityManagerFactory} beans should "depend on" one or more specific beans.
 *





 * @since 1.1.0
 * @see BeanDefinition#setDependsOn(String[])
 */
public class EntityManagerFactoryDependsOnPostProcessor extends AbstractDependsOnBeanFactoryPostProcessor {

	/**
	 * Creates a new {@code EntityManagerFactoryDependsOnPostProcessor} that will set up
	 * dependencies upon beans with the given names.
	 * @param dependsOn names of the beans to depend upon
	 */
	public EntityManagerFactoryDependsOnPostProcessor(String... dependsOn) {
		super(EntityManagerFactory.class, AbstractEntityManagerFactoryBean.class, dependsOn);
	}

	/**
	 * Creates a new {@code EntityManagerFactoryDependsOnPostProcessor} that will set up
	 * dependencies upon beans with the given types.
	 * @param dependsOn types of the beans to depend upon
	 * @since 2.1.8
	 */
	public EntityManagerFactoryDependsOnPostProcessor(Class<?>... dependsOn) {
		super(EntityManagerFactory.class, AbstractEntityManagerFactoryBean.class, dependsOn);
	}

}
