package org.springframework.boot.test.web.client;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link ImportSelector} to check no {@link TestRestTemplate} definition is registered
 * when config classes are processed.
 */
class NoTestRestTemplateBeanChecker implements ImportSelector, BeanFactoryAware {

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		assertThat(BeanFactoryUtils.beanNamesForTypeIncludingAncestors((ListableBeanFactory) beanFactory,
				TestRestTemplate.class)).isEmpty();
	}

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		return new String[0];
	}

}
