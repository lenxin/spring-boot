package org.springframework.boot.test.context.bootstrap;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.test.context.BootstrapContext;
import org.springframework.test.context.CacheAwareContextLoaderDelegate;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link SpringBootTestContextBootstrapper}.
 *

 */
class SpringBootTestContextBootstrapperTests {

	@Test
	void springBootTestWithANonMockWebEnvironmentAndWebAppConfigurationFailsFast() {
		assertThatIllegalStateException()
				.isThrownBy(() -> buildTestContext(SpringBootTestNonMockWebEnvironmentAndWebAppConfiguration.class))
				.withMessageContaining("@WebAppConfiguration should only be used with "
						+ "@SpringBootTest when @SpringBootTest is configured with a mock web "
						+ "environment. Please remove @WebAppConfiguration or reconfigure @SpringBootTest.");
	}

	@Test
	void springBootTestWithAMockWebEnvironmentCanBeUsedWithWebAppConfiguration() {
		buildTestContext(SpringBootTestMockWebEnvironmentAndWebAppConfiguration.class);
	}

	@Test
	void mergedContextConfigurationWhenArgsDifferentShouldNotBeConsideredEqual() {
		TestContext context = buildTestContext(SpringBootTestArgsConfiguration.class);
		Object contextConfiguration = ReflectionTestUtils.getField(context, "mergedContextConfiguration");
		TestContext otherContext2 = buildTestContext(SpringBootTestOtherArgsConfiguration.class);
		Object otherContextConfiguration = ReflectionTestUtils.getField(otherContext2, "mergedContextConfiguration");
		assertThat(contextConfiguration).isNotEqualTo(otherContextConfiguration);
	}

	@Test
	void mergedContextConfigurationWhenArgsSameShouldBeConsideredEqual() {
		TestContext context = buildTestContext(SpringBootTestArgsConfiguration.class);
		Object contextConfiguration = ReflectionTestUtils.getField(context, "mergedContextConfiguration");
		TestContext otherContext2 = buildTestContext(SpringBootTestSameArgsConfiguration.class);
		Object otherContextConfiguration = ReflectionTestUtils.getField(otherContext2, "mergedContextConfiguration");
		assertThat(contextConfiguration).isEqualTo(otherContextConfiguration);
	}

	@Test
	void mergedContextConfigurationWhenWebEnvironmentsDifferentShouldNotBeConsideredEqual() {
		TestContext context = buildTestContext(SpringBootTestMockWebEnvironmentConfiguration.class);
		Object contextConfiguration = ReflectionTestUtils.getField(context, "mergedContextConfiguration");
		TestContext otherContext = buildTestContext(SpringBootTestDefinedPortWebEnvironmentConfiguration.class);
		Object otherContextConfiguration = ReflectionTestUtils.getField(otherContext, "mergedContextConfiguration");
		assertThat(contextConfiguration).isNotEqualTo(otherContextConfiguration);
	}

	@Test
	void mergedContextConfigurationWhenWebEnvironmentsSameShouldtBeConsideredEqual() {
		TestContext context = buildTestContext(SpringBootTestMockWebEnvironmentConfiguration.class);
		Object contextConfiguration = ReflectionTestUtils.getField(context, "mergedContextConfiguration");
		TestContext otherContext = buildTestContext(SpringBootTestAnotherMockWebEnvironmentConfiguration.class);
		Object otherContextConfiguration = ReflectionTestUtils.getField(otherContext, "mergedContextConfiguration");
		assertThat(contextConfiguration).isEqualTo(otherContextConfiguration);
	}

	@SuppressWarnings("rawtypes")
	private TestContext buildTestContext(Class<?> testClass) {
		SpringBootTestContextBootstrapper bootstrapper = new SpringBootTestContextBootstrapper();
		BootstrapContext bootstrapContext = mock(BootstrapContext.class);
		bootstrapper.setBootstrapContext(bootstrapContext);
		given((Class) bootstrapContext.getTestClass()).willReturn(testClass);
		CacheAwareContextLoaderDelegate contextLoaderDelegate = mock(CacheAwareContextLoaderDelegate.class);
		given(bootstrapContext.getCacheAwareContextLoaderDelegate()).willReturn(contextLoaderDelegate);
		return bootstrapper.buildTestContext();
	}

	@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
	@WebAppConfiguration
	static class SpringBootTestNonMockWebEnvironmentAndWebAppConfiguration {

	}

	@SpringBootTest
	@WebAppConfiguration
	static class SpringBootTestMockWebEnvironmentAndWebAppConfiguration {

	}

	@SpringBootTest(args = "--app.test=same")
	static class SpringBootTestArgsConfiguration {

	}

	@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
	static class SpringBootTestMockWebEnvironmentConfiguration {

	}

	@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
	static class SpringBootTestAnotherMockWebEnvironmentConfiguration {

	}

	@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
	static class SpringBootTestDefinedPortWebEnvironmentConfiguration {

	}

	@SpringBootTest(args = "--app.test=same")
	static class SpringBootTestSameArgsConfiguration {

	}

	@SpringBootTest(args = "--app.test=different")
	static class SpringBootTestOtherArgsConfiguration {

	}

}
