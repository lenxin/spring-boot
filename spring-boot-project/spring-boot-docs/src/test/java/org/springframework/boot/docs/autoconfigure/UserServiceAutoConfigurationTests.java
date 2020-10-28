package org.springframework.boot.docs.autoconfigure;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link UserServiceAutoConfiguration}.
 *

 */
class UserServiceAutoConfigurationTests {

	// tag::runner[]
	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(UserServiceAutoConfiguration.class));

	// end::runner[]

	// tag::test-env[]
	@Test
	void serviceNameCanBeConfigured() {
		this.contextRunner.withPropertyValues("user.name=test123").run((context) -> {
			assertThat(context).hasSingleBean(UserService.class);
			assertThat(context.getBean(UserService.class).getName()).isEqualTo("test123");
		});
	}
	// end::test-env[]

	// tag::test-classloader[]
	@Test
	void serviceIsIgnoredIfLibraryIsNotPresent() {
		this.contextRunner.withClassLoader(new FilteredClassLoader(UserService.class))
				.run((context) -> assertThat(context).doesNotHaveBean("userService"));
	}
	// end::test-classloader[]

	// tag::test-user-config[]
	@Test
	void defaultServiceBacksOff() {
		this.contextRunner.withUserConfiguration(UserConfiguration.class).run((context) -> {
			assertThat(context).hasSingleBean(UserService.class);
			assertThat(context).getBean("myUserService").isSameAs(context.getBean(UserService.class));
		});
	}

	@Configuration(proxyBeanMethods = false)
	static class UserConfiguration {

		@Bean
		UserService myUserService() {
			return new UserService("mine");
		}

	}
	// end::test-user-config[]

}
