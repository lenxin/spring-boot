package org.springframework.boot.autoconfigure.jms;

import java.util.Arrays;

import javax.jms.ConnectionFactory;
import javax.naming.NamingException;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJndi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration.JndiOrPropertyCondition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jndi.JndiLocatorDelegate;
import org.springframework.util.StringUtils;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for JMS provided from JNDI.
 *

 * @since 1.2.0
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(JmsAutoConfiguration.class)
@ConditionalOnClass(JmsTemplate.class)
@ConditionalOnMissingBean(ConnectionFactory.class)
@Conditional(JndiOrPropertyCondition.class)
@EnableConfigurationProperties(JmsProperties.class)
public class JndiConnectionFactoryAutoConfiguration {

	// Keep these in sync with the condition below
	private static final String[] JNDI_LOCATIONS = { "java:/JmsXA", "java:/XAConnectionFactory" };

	@Bean
	public ConnectionFactory jmsConnectionFactory(JmsProperties properties) throws NamingException {
		JndiLocatorDelegate jndiLocatorDelegate = JndiLocatorDelegate.createDefaultResourceRefLocator();
		if (StringUtils.hasLength(properties.getJndiName())) {
			return jndiLocatorDelegate.lookup(properties.getJndiName(), ConnectionFactory.class);
		}
		return findJndiConnectionFactory(jndiLocatorDelegate);
	}

	private ConnectionFactory findJndiConnectionFactory(JndiLocatorDelegate jndiLocatorDelegate) {
		for (String name : JNDI_LOCATIONS) {
			try {
				return jndiLocatorDelegate.lookup(name, ConnectionFactory.class);
			}
			catch (NamingException ex) {
				// Swallow and continue
			}
		}
		throw new IllegalStateException(
				"Unable to find ConnectionFactory in JNDI locations " + Arrays.asList(JNDI_LOCATIONS));
	}

	/**
	 * Condition for JNDI name or a specific property.
	 */
	static class JndiOrPropertyCondition extends AnyNestedCondition {

		JndiOrPropertyCondition() {
			super(ConfigurationPhase.PARSE_CONFIGURATION);
		}

		@ConditionalOnJndi({ "java:/JmsXA", "java:/XAConnectionFactory" })
		static class Jndi {

		}

		@ConditionalOnProperty(prefix = "spring.jms", name = "jndi-name")
		static class Property {

		}

	}

}
