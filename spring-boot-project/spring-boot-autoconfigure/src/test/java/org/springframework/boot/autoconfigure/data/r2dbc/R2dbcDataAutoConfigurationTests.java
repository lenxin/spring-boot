package org.springframework.boot.autoconfigure.data.r2dbc;

import org.junit.jupiter.api.Test;

import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link R2dbcDataAutoConfiguration}.
 *

 */
class R2dbcDataAutoConfigurationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(R2dbcAutoConfiguration.class, R2dbcDataAutoConfiguration.class));

	@Test
	void r2dbcEntityTemplateIsConfigured() {
		this.contextRunner.run((context) -> assertThat(context).hasSingleBean(R2dbcEntityTemplate.class));
	}

}
