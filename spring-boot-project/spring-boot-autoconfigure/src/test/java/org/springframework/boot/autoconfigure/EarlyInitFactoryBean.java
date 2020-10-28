package org.springframework.boot.autoconfigure;

import org.springframework.beans.factory.FactoryBean;

public class EarlyInitFactoryBean implements FactoryBean<String> {

	private String propertyFromConfig;

	public void setPropertyFromConfig(String propertyFromConfig) {
		this.propertyFromConfig = propertyFromConfig;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public Class<?> getObjectType() {
		return null;
	}

	@Override
	public String getObject() throws Exception {
		return this.propertyFromConfig;
	}

}
