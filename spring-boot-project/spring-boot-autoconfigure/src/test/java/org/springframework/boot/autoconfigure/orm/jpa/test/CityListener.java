package org.springframework.boot.autoconfigure.orm.jpa.test;

import javax.persistence.PostLoad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

public class CityListener {

	private ConfigurableBeanFactory beanFactory;

	public CityListener() {
	}

	@Autowired
	public CityListener(ConfigurableBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@PostLoad
	public void postLoad(City city) {
		if (this.beanFactory != null) {
			this.beanFactory.registerSingleton(City.class.getName(), city);
		}
	}

}
