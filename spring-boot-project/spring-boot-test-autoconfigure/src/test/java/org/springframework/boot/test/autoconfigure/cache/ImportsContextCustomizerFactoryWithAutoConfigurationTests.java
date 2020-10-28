package org.springframework.boot.test.autoconfigure.cache;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.junit.jupiter.api.Test;
import org.junit.platform.engine.discovery.DiscoverySelectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.ExampleEntity;
import org.springframework.boot.testsupport.junit.platform.Launcher;
import org.springframework.boot.testsupport.junit.platform.LauncherDiscoveryRequest;
import org.springframework.boot.testsupport.junit.platform.LauncherDiscoveryRequestBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@code ImportsContextCustomizerFactory} when used with
 * {@link ImportAutoConfiguration @ImportAutoConfiguration}.
 *


 */
class ImportsContextCustomizerFactoryWithAutoConfigurationTests {

	static ApplicationContext contextFromTest;

	@Test
	void testClassesThatHaveSameAnnotationsShareAContext() throws Throwable {
		executeTests(DataJpaTest1.class);
		ApplicationContext test1Context = contextFromTest;
		executeTests(DataJpaTest3.class);
		ApplicationContext test2Context = contextFromTest;
		assertThat(test1Context).isSameAs(test2Context);
	}

	@Test
	void testClassesThatOnlyHaveDifferingUnrelatedAnnotationsShareAContext() throws Throwable {
		executeTests(DataJpaTest1.class);
		ApplicationContext test1Context = contextFromTest;
		executeTests(DataJpaTest2.class);
		ApplicationContext test2Context = contextFromTest;
		assertThat(test1Context).isSameAs(test2Context);
	}

	@Test
	void testClassesThatOnlyHaveDifferingPropertyMappedAnnotationAttributesDoNotShareAContext() throws Throwable {
		executeTests(DataJpaTest1.class);
		ApplicationContext test1Context = contextFromTest;
		executeTests(DataJpaTest4.class);
		ApplicationContext test2Context = contextFromTest;
		assertThat(test1Context).isNotSameAs(test2Context);
	}

	private void executeTests(Class<?> testClass) throws Throwable {
		ClassLoader classLoader = testClass.getClassLoader();
		LauncherDiscoveryRequest request = new LauncherDiscoveryRequestBuilder(classLoader)
				.selectors(DiscoverySelectors.selectClass(testClass)).build();
		Launcher launcher = new Launcher(testClass.getClassLoader());
		launcher.execute(request);
	}

	@DataJpaTest
	@ContextConfiguration(classes = EmptyConfig.class)
	@Unrelated1
	static class DataJpaTest1 {

		@Autowired
		private ApplicationContext context;

		@Test
		void test() {
			contextFromTest = this.context;
		}

	}

	@DataJpaTest
	@ContextConfiguration(classes = EmptyConfig.class)
	@Unrelated2
	static class DataJpaTest2 {

		@Autowired
		private ApplicationContext context;

		@Test
		void test() {
			contextFromTest = this.context;
		}

	}

	@DataJpaTest
	@ContextConfiguration(classes = EmptyConfig.class)
	@Unrelated1
	static class DataJpaTest3 {

		@Autowired
		private ApplicationContext context;

		@Test
		void test() {
			contextFromTest = this.context;
		}

	}

	@DataJpaTest(showSql = false)
	@ContextConfiguration(classes = EmptyConfig.class)
	@Unrelated1
	static class DataJpaTest4 {

		@Autowired
		private ApplicationContext context;

		@Test
		void test() {
			contextFromTest = this.context;
		}

	}

	@Retention(RetentionPolicy.RUNTIME)
	@interface Unrelated1 {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@interface Unrelated2 {

	}

	@Configuration(proxyBeanMethods = false)
	@EntityScan(basePackageClasses = ExampleEntity.class)
	@AutoConfigurationPackage
	static class EmptyConfig {

	}

}
