package org.springframework.boot;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.boot.testsupport.classpath.ForkedClassPath;
import org.springframework.context.support.StaticApplicationContext;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ForkedClassPath
class IgnoringXmlBeanDefinitionLoaderTests {

	@BeforeAll
	static void ignoreXml() {
		System.setProperty("spring.xml.ignore", "true");
	}

	@AfterAll
	static void enableXml() {
		System.clearProperty("spring.xml.ignore");
	}

	@Test
	void whenXmlSupportIsDisabledXmlSourcesAreRejected() {
		assertThatExceptionOfType(BeanDefinitionStoreException.class)
				.isThrownBy(() -> new BeanDefinitionLoader(new StaticApplicationContext(),
						"classpath:org/springframework/boot/sample-beans.xml").load())
				.withMessage("Cannot load XML bean definitions when XML support is disabled");
	}

}
