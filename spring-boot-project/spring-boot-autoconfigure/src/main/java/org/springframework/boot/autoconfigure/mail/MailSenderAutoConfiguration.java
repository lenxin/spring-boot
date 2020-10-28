package org.springframework.boot.autoconfigure.mail;

import javax.activation.MimeType;
import javax.mail.internet.MimeMessage;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration.MailSenderCondition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mail.MailSender;

/**
 * {@link EnableAutoConfiguration Auto configuration} for email support.
 *



 * @since 1.2.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ MimeMessage.class, MimeType.class, MailSender.class })
@ConditionalOnMissingBean(MailSender.class)
@Conditional(MailSenderCondition.class)
@EnableConfigurationProperties(MailProperties.class)
@Import({ MailSenderJndiConfiguration.class, MailSenderPropertiesConfiguration.class })
public class MailSenderAutoConfiguration {

	/**
	 * Condition to trigger the creation of a {@link MailSender}. This kicks in if either
	 * the host or jndi name property is set.
	 */
	static class MailSenderCondition extends AnyNestedCondition {

		MailSenderCondition() {
			super(ConfigurationPhase.PARSE_CONFIGURATION);
		}

		@ConditionalOnProperty(prefix = "spring.mail", name = "host")
		static class HostProperty {

		}

		@ConditionalOnProperty(prefix = "spring.mail", name = "jndi-name")
		static class JndiNameProperty {

		}

	}

}
