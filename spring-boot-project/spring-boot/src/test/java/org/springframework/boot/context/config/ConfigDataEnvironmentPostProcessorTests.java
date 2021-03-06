package org.springframework.boot.context.config;

import java.util.Set;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.boot.DefaultBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

/**
 * Tests for {@link ConfigDataEnvironmentPostProcessor}.
 *


 */
@ExtendWith(MockitoExtension.class)
class ConfigDataEnvironmentPostProcessorTests {

	private StandardEnvironment environment = new StandardEnvironment();

	private SpringApplication application = new SpringApplication();

	@Mock
	private ConfigDataEnvironment configDataEnvironment;

	@Spy
	private ConfigDataEnvironmentPostProcessor postProcessor = new ConfigDataEnvironmentPostProcessor(Supplier::get,
			new DefaultBootstrapContext());

	@Captor
	private ArgumentCaptor<Set<String>> additionalProfilesCaptor;

	@Captor
	private ArgumentCaptor<ResourceLoader> resourceLoaderCaptor;

	@Test
	@SuppressWarnings("deprecation")
	void defaultOrderMatchesDeprecatedListener() {
		assertThat(ConfigDataEnvironmentPostProcessor.ORDER).isEqualTo(ConfigFileApplicationListener.DEFAULT_ORDER);
	}

	@Test
	void postProcessEnvironmentWhenNoLoaderCreatesDefaultLoaderInstance() {
		willReturn(this.configDataEnvironment).given(this.postProcessor).getConfigDataEnvironment(any(), any(), any());
		this.postProcessor.postProcessEnvironment(this.environment, this.application);
		verify(this.postProcessor).getConfigDataEnvironment(any(), this.resourceLoaderCaptor.capture(), any());
		verify(this.configDataEnvironment).processAndApply();
		assertThat(this.resourceLoaderCaptor.getValue()).isInstanceOf(DefaultResourceLoader.class);
	}

	@Test
	void postProcessEnvironmentWhenCustomLoaderUsesSpecifiedLoaderInstance() {
		ResourceLoader resourceLoader = mock(ResourceLoader.class);
		this.application.setResourceLoader(resourceLoader);
		willReturn(this.configDataEnvironment).given(this.postProcessor).getConfigDataEnvironment(any(), any(), any());
		this.postProcessor.postProcessEnvironment(this.environment, this.application);
		verify(this.postProcessor).getConfigDataEnvironment(any(), this.resourceLoaderCaptor.capture(), any());
		verify(this.configDataEnvironment).processAndApply();
		assertThat(this.resourceLoaderCaptor.getValue()).isSameAs(resourceLoader);
	}

	@Test
	void postProcessEnvironmentWhenHasAdditionalProfilesOnSpringApplicationUsesAdditionalProfiles() {
		this.application.setAdditionalProfiles("dev");
		willReturn(this.configDataEnvironment).given(this.postProcessor).getConfigDataEnvironment(any(), any(), any());
		this.postProcessor.postProcessEnvironment(this.environment, this.application);
		verify(this.postProcessor).getConfigDataEnvironment(any(), any(), this.additionalProfilesCaptor.capture());
		verify(this.configDataEnvironment).processAndApply();
		assertThat(this.additionalProfilesCaptor.getValue()).containsExactly("dev");
	}

	@Test
	void postProcessEnvironmentWhenUseLegacyProcessingSwitchesToLegacyMethod() {
		ConfigDataEnvironmentPostProcessor.LegacyConfigFileApplicationListener legacyListener = mock(
				ConfigDataEnvironmentPostProcessor.LegacyConfigFileApplicationListener.class);
		willThrow(new UseLegacyConfigProcessingException(null)).given(this.postProcessor)
				.getConfigDataEnvironment(any(), any(), any());
		willReturn(legacyListener).given(this.postProcessor).getLegacyListener();
		this.postProcessor.postProcessEnvironment(this.environment, this.application);
		verifyNoInteractions(this.configDataEnvironment);
		verify(legacyListener).addPropertySources(eq(this.environment), any(DefaultResourceLoader.class));
	}

	@Test
	void applyToAppliesPostProcessing() {
		int before = this.environment.getPropertySources().size();
		ConfigDataEnvironmentPostProcessor.applyTo(this.environment, null, null, "dev");
		assertThat(this.environment.getPropertySources().size()).isGreaterThan(before);
		assertThat(this.environment.getActiveProfiles()).containsExactly("dev");
	}

}
