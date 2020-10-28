package org.springframework.boot.autoconfigure.aop;

import org.junit.jupiter.api.Test;

import org.springframework.aop.config.AopConfigUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.testsupport.classpath.ClassPathExclusions;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link AopAutoConfiguration} without AspectJ.
 *

 */
@ClassPathExclusions("aspectjweaver*.jar")
class NonAspectJAopAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(AopAutoConfiguration.class));

	@Test
	void whenAspectJIsAbsentAndProxyTargetClassIsEnabledProxyCreatorBeanIsDefined() {
		this.contextRunner.run((context) -> {
			BeanDefinition autoProxyCreator = context.getBeanFactory()
					.getBeanDefinition(AopConfigUtils.AUTO_PROXY_CREATOR_BEAN_NAME);
			assertThat(autoProxyCreator.getPropertyValues().get("proxyTargetClass")).isEqualTo(Boolean.TRUE);
		});
	}

	@Test
	void whenAspectJIsAbsentAndProxyTargetClassIsDisabledNoProxyCreatorBeanIsDefined() {
		this.contextRunner.withPropertyValues("spring.aop.proxy-target-class:false")
				.run((context) -> assertThat(context).doesNotHaveBean(AopConfigUtils.AUTO_PROXY_CREATOR_BEAN_NAME));
	}

}
