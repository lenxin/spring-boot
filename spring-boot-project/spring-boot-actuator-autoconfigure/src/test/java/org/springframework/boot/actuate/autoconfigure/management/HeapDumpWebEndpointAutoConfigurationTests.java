package org.springframework.boot.actuate.autoconfigure.management;

import org.junit.jupiter.api.Test;

import org.springframework.boot.actuate.management.HeapDumpWebEndpoint;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link HeapDumpWebEndpointAutoConfiguration}.
 *

 */
class HeapDumpWebEndpointAutoConfigurationTests {

	private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
			.withPropertyValues("management.endpoints.web.exposure.include:*")
			.withUserConfiguration(HeapDumpWebEndpointAutoConfiguration.class);

	@Test
	void runShouldCreateIndicator() {
		this.contextRunner.run((context) -> assertThat(context).hasSingleBean(HeapDumpWebEndpoint.class));
	}

	@Test
	void runWhenDisabledShouldNotCreateIndicator() {
		this.contextRunner.withPropertyValues("management.endpoint.heapdump.enabled:false")
				.run((context) -> assertThat(context).doesNotHaveBean(HeapDumpWebEndpoint.class));
	}

}
