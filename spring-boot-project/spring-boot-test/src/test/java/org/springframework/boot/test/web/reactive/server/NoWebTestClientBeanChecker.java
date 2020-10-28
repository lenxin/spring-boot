package org.springframework.boot.test.web.reactive.server;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link ImportSelector} to check no {@link WebTestClient} definition is registered when
 * config classes are processed.
 */
class NoWebTestClientBeanChecker implements ImportSelector, BeanFactoryAware {

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		assertThat(BeanFactoryUtils.beanNamesForTypeIncludingAncestors((ListableBeanFactory) beanFactory,
				WebTestClient.class)).isEmpty();
	}

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		return new String[0];
	}

}
