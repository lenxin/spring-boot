package org.springframework.boot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sampleconfig.MyComponentInPackageWithoutDot;

import org.springframework.boot.sampleconfig.MyComponent;
import org.springframework.boot.sampleconfig.MyNamedComponent;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link BeanDefinitionLoader}.
 *


 */
class BeanDefinitionLoaderTests {

	private StaticApplicationContext registry;

	@BeforeEach
	void setup() {
		this.registry = new StaticApplicationContext();
	}

	@AfterEach
	void cleanUp() {
		this.registry.close();
	}

	@Test
	void loadClass() {
		BeanDefinitionLoader loader = new BeanDefinitionLoader(this.registry, MyComponent.class);
		assertThat(load(loader)).isEqualTo(1);
		assertThat(this.registry.containsBean("myComponent")).isTrue();
	}

	@Test
	void anonymousClassNotLoaded() {
		MyComponent myComponent = new MyComponent() {

		};
		BeanDefinitionLoader loader = new BeanDefinitionLoader(this.registry, myComponent.getClass());
		assertThat(load(loader)).isEqualTo(0);
	}

	@Test
	void loadJsr330Class() {
		BeanDefinitionLoader loader = new BeanDefinitionLoader(this.registry, MyNamedComponent.class);
		assertThat(load(loader)).isEqualTo(1);
		assertThat(this.registry.containsBean("myNamedComponent")).isTrue();
	}

	@Test
	void loadXmlResource() {
		ClassPathResource resource = new ClassPathResource("sample-beans.xml", getClass());
		BeanDefinitionLoader loader = new BeanDefinitionLoader(this.registry, resource);
		assertThat(load(loader)).isEqualTo(1);
		assertThat(this.registry.containsBean("myXmlComponent")).isTrue();

	}

	@Test
	void loadGroovyResource() {
		ClassPathResource resource = new ClassPathResource("sample-beans.groovy", getClass());
		BeanDefinitionLoader loader = new BeanDefinitionLoader(this.registry, resource);
		assertThat(load(loader)).isEqualTo(1);
		assertThat(this.registry.containsBean("myGroovyComponent")).isTrue();

	}

	@Test
	void loadGroovyResourceWithNamespace() {
		ClassPathResource resource = new ClassPathResource("sample-namespace.groovy", getClass());
		BeanDefinitionLoader loader = new BeanDefinitionLoader(this.registry, resource);
		assertThat(load(loader)).isEqualTo(1);
		assertThat(this.registry.containsBean("myGroovyComponent")).isTrue();

	}

	@Test
	void loadPackage() {
		BeanDefinitionLoader loader = new BeanDefinitionLoader(this.registry, MyComponent.class.getPackage());
		assertThat(load(loader)).isEqualTo(2);
		assertThat(this.registry.containsBean("myComponent")).isTrue();
		assertThat(this.registry.containsBean("myNamedComponent")).isTrue();
	}

	@Test
	void loadClassName() {
		BeanDefinitionLoader loader = new BeanDefinitionLoader(this.registry, MyComponent.class.getName());
		assertThat(load(loader)).isEqualTo(1);
		assertThat(this.registry.containsBean("myComponent")).isTrue();
	}

	@Test
	void loadResourceName() {
		BeanDefinitionLoader loader = new BeanDefinitionLoader(this.registry,
				"classpath:org/springframework/boot/sample-beans.xml");
		assertThat(load(loader)).isEqualTo(1);
		assertThat(this.registry.containsBean("myXmlComponent")).isTrue();
	}

	@Test
	void loadGroovyName() {
		BeanDefinitionLoader loader = new BeanDefinitionLoader(this.registry,
				"classpath:org/springframework/boot/sample-beans.groovy");
		assertThat(load(loader)).isEqualTo(1);
		assertThat(this.registry.containsBean("myGroovyComponent")).isTrue();
	}

	@Test
	void loadPackageName() {
		BeanDefinitionLoader loader = new BeanDefinitionLoader(this.registry, MyComponent.class.getPackage().getName());
		assertThat(load(loader)).isEqualTo(2);
		assertThat(this.registry.containsBean("myComponent")).isTrue();
		assertThat(this.registry.containsBean("myNamedComponent")).isTrue();
	}

	@Test
	void loadPackageNameWithoutDot() {
		// See gh-6126
		BeanDefinitionLoader loader = new BeanDefinitionLoader(this.registry,
				MyComponentInPackageWithoutDot.class.getPackage().getName());
		int loaded = load(loader);
		assertThat(loaded).isEqualTo(1);
		assertThat(this.registry.containsBean("myComponentInPackageWithoutDot")).isTrue();
	}

	@Test
	void loadPackageAndClassDoesNotDoubleAdd() {
		BeanDefinitionLoader loader = new BeanDefinitionLoader(this.registry, MyComponent.class.getPackage(),
				MyComponent.class);
		assertThat(load(loader)).isEqualTo(2);
		assertThat(this.registry.containsBean("myComponent")).isTrue();
		assertThat(this.registry.containsBean("myNamedComponent")).isTrue();
	}

	private int load(BeanDefinitionLoader loader) {
		int beans = this.registry.getBeanDefinitionCount();
		loader.load();
		return this.registry.getBeanDefinitionCount() - beans;
	}

}
