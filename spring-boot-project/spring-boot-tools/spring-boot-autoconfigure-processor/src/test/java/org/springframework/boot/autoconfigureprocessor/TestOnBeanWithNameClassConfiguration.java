package org.springframework.boot.autoconfigureprocessor;

/**
 * Test configuration with an annotated class.
 *

 */
@TestConditionalOnBean(name = "test", type = "java.io.OutputStream")
public class TestOnBeanWithNameClassConfiguration {

}
