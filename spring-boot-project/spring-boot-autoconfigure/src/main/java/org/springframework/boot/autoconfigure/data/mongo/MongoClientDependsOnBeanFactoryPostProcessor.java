package org.springframework.boot.autoconfigure.data.mongo;

import com.mongodb.client.MongoClient;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.AbstractDependsOnBeanFactoryPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;

/**
 * {@link BeanFactoryPostProcessor} to automatically set up the recommended
 * {@link BeanDefinition#setDependsOn(String[]) dependsOn} configuration for Mongo clients
 * when used embedded Mongo.
 *

 * @since 1.3.0
 */
@Order(Ordered.LOWEST_PRECEDENCE)
public class MongoClientDependsOnBeanFactoryPostProcessor extends AbstractDependsOnBeanFactoryPostProcessor {

	/**
	 * Creates a new {@code MongoClientDependsOnBeanFactoryPostProcessor} that will set up
	 * dependencies upon beans with the given types.
	 * @param dependsOn types of the beans to depend upon
	 */
	public MongoClientDependsOnBeanFactoryPostProcessor(Class<?>... dependsOn) {
		super(MongoClient.class, MongoClientFactoryBean.class, dependsOn);
	}

}
