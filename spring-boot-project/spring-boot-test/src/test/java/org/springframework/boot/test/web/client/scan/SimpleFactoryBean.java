package org.springframework.boot.test.web.client.scan;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * A simple factory bean with no generics. Used to test early initialization doesn't
 * occur.
 *

 */
@Component
@SuppressWarnings("rawtypes")
public class SimpleFactoryBean implements FactoryBean {

	private static boolean isInitializedEarly = false;

	public SimpleFactoryBean() {
		isInitializedEarly = true;
		throw new RuntimeException();
	}

	@Autowired
	public SimpleFactoryBean(ApplicationContext context) {
		if (isInitializedEarly) {
			throw new RuntimeException();
		}
	}

	@Override
	public Object getObject() {
		return new Object();
	}

	@Override
	public Class<?> getObjectType() {
		return Object.class;
	}

}
