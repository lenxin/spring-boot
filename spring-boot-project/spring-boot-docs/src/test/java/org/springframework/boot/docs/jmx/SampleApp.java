package org.springframework.boot.docs.jmx;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;

/**
 * A sample {@link SpringBootConfiguration @ConfigurationProperties} that only enables JMX
 * auto-configuration.
 *

 */
@SpringBootConfiguration
@ImportAutoConfiguration(JmxAutoConfiguration.class)
public class SampleApp {

}
