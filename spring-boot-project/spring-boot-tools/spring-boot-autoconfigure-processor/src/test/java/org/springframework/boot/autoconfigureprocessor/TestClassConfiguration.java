package org.springframework.boot.autoconfigureprocessor;

import org.springframework.boot.autoconfigureprocessor.TestConditionalOnWebApplication.Type;

/**
 * Test configuration with an annotated class.
 *

 */
@TestConditionalOnClass(name = { "org.springframework.foo", "java.io.InputStream" },
		value = TestClassConfiguration.Nested.class)
@TestConditionalOnBean(type = "java.io.OutputStream")
@TestConditionalOnSingleCandidate(type = "java.io.OutputStream")
@TestConditionalOnWebApplication(type = Type.SERVLET)
public class TestClassConfiguration {

	@TestAutoConfigureOrder
	static class Nested {

	}

}
