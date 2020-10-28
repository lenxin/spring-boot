package org.springframework.boot.web.server;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link LocalServerPort @LocalServerPort}.
 *


 */
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "local.server.port=8181")
class LocalServerPortTests {

	@Value("${local.server.port}")
	private String fromValue;

	@LocalServerPort
	private String fromAnnotation;

	@Test
	void testLocalServerPortAnnotation() {
		assertThat(this.fromAnnotation).isNotNull().isEqualTo(this.fromValue);
	}

	@Configuration(proxyBeanMethods = false)
	static class Config {

	}

}
