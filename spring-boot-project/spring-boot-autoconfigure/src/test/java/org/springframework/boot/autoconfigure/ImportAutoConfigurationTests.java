package org.springframework.boot.autoconfigure;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ImportAutoConfiguration @ImportAutoConfiguration}.
 *

 */
class ImportAutoConfigurationTests {

	@Test
	void multipleAnnotationsShouldMergeCorrectly() {
		assertThat(getImportedConfigBeans(Config.class)).containsExactly("ConfigA", "ConfigB", "ConfigC", "ConfigD");
		assertThat(getImportedConfigBeans(AnotherConfig.class)).containsExactly("ConfigA", "ConfigB", "ConfigC",
				"ConfigD");
	}

	@Test
	void classesAsAnAlias() {
		assertThat(getImportedConfigBeans(AnotherConfigUsingClasses.class)).containsExactly("ConfigA", "ConfigB",
				"ConfigC", "ConfigD");
	}

	@Test
	void excluding() {
		assertThat(getImportedConfigBeans(ExcludingConfig.class)).containsExactly("ConfigA", "ConfigB", "ConfigD");
	}

	@Test
	void excludeAppliedGlobally() {
		assertThat(getImportedConfigBeans(ExcludeDConfig.class, ImportADConfig.class)).containsExactly("ConfigA");
	}

	@Test
	void excludeWithRedundancy() {
		assertThat(getImportedConfigBeans(ExcludeADConfig.class, ExcludeDConfig.class, ImportADConfig.class)).isEmpty();
	}

	private List<String> getImportedConfigBeans(Class<?>... config) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(config);
		String shortName = ClassUtils.getShortName(ImportAutoConfigurationTests.class);
		int beginIndex = shortName.length() + 1;
		List<String> orderedConfigBeans = new ArrayList<>();
		for (String bean : context.getBeanDefinitionNames()) {
			if (bean.contains("$Config")) {
				String shortBeanName = ClassUtils.getShortName(bean);
				orderedConfigBeans.add(shortBeanName.substring(beginIndex));
			}
		}
		context.close();
		return orderedConfigBeans;
	}

	@ImportAutoConfiguration({ ConfigD.class, ConfigB.class })
	@MetaImportAutoConfiguration
	static class Config {

	}

	@MetaImportAutoConfiguration
	@ImportAutoConfiguration({ ConfigB.class, ConfigD.class })
	static class AnotherConfig {

	}

	@MetaImportAutoConfiguration
	@ImportAutoConfiguration(classes = { ConfigB.class, ConfigD.class })
	static class AnotherConfigUsingClasses {

	}

	@ImportAutoConfiguration(classes = { ConfigD.class, ConfigB.class }, exclude = ConfigC.class)
	@MetaImportAutoConfiguration
	static class ExcludingConfig {

	}

	@ImportAutoConfiguration(classes = { ConfigA.class, ConfigD.class })
	static class ImportADConfig {

	}

	@ImportAutoConfiguration(exclude = { ConfigA.class, ConfigD.class })
	static class ExcludeADConfig {

	}

	@ImportAutoConfiguration(exclude = ConfigD.class)
	static class ExcludeDConfig {

	}

	@Retention(RetentionPolicy.RUNTIME)
	@ImportAutoConfiguration({ ConfigC.class, ConfigA.class })
	@interface MetaImportAutoConfiguration {

	}

	@Configuration(proxyBeanMethods = false)
	static class ConfigA {

	}

	@Configuration(proxyBeanMethods = false)
	@AutoConfigureAfter(ConfigA.class)
	static class ConfigB {

	}

	@Configuration(proxyBeanMethods = false)
	@AutoConfigureAfter(ConfigB.class)
	static class ConfigC {

	}

	@Configuration(proxyBeanMethods = false)
	@AutoConfigureAfter(ConfigC.class)
	static class ConfigD {

	}

}
