package org.springframework.boot.test.context;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SpringBootTest @SpringBootTest} finding XML config.
 *

 */
@DirtiesContext
@SpringBootTest
class SpringBootTestXmlConventionConfigurationTests {

	@Autowired
	private String foo;

	@Test
	void xmlConfigLoaded() {
		assertThat(this.foo).isEqualTo("World");
	}

}
