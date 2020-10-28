package org.springframework.boot.autoconfigure.admin;

import javax.management.MalformedObjectNameException;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.admin.SpringApplicationAdminMXBean;
import org.springframework.boot.admin.SpringApplicationAdminMXBeanRegistrar;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jmx.export.MBeanExporter;

/**
 * Register a JMX component that allows to administer the current application. Intended
 * for internal use only.
 *


 * @since 1.3.0
 * @see SpringApplicationAdminMXBean
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(JmxAutoConfiguration.class)
@ConditionalOnProperty(prefix = "spring.application.admin", value = "enabled", havingValue = "true",
		matchIfMissing = false)
public class SpringApplicationAdminJmxAutoConfiguration {

	/**
	 * The property to use to customize the {@code ObjectName} of the application admin
	 * mbean.
	 */
	private static final String JMX_NAME_PROPERTY = "spring.application.admin.jmx-name";

	/**
	 * The default {@code ObjectName} of the application admin mbean.
	 */
	private static final String DEFAULT_JMX_NAME = "org.springframework.boot:type=Admin,name=SpringApplication";

	@Bean
	@ConditionalOnMissingBean
	public SpringApplicationAdminMXBeanRegistrar springApplicationAdminRegistrar(
			ObjectProvider<MBeanExporter> mbeanExporters, Environment environment) throws MalformedObjectNameException {
		String jmxName = environment.getProperty(JMX_NAME_PROPERTY, DEFAULT_JMX_NAME);
		if (mbeanExporters != null) { // Make sure to not register that MBean twice
			for (MBeanExporter mbeanExporter : mbeanExporters) {
				mbeanExporter.addExcludedBean(jmxName);
			}
		}
		return new SpringApplicationAdminMXBeanRegistrar(jmxName);
	}

}
