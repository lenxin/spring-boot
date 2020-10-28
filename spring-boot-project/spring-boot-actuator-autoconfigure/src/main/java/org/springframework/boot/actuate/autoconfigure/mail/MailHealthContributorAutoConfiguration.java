package org.springframework.boot.actuate.autoconfigure.mail;

import java.util.Map;

import org.springframework.boot.actuate.autoconfigure.health.CompositeHealthContributorConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.mail.MailHealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link MailHealthIndicator}.
 *

 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(JavaMailSenderImpl.class)
@ConditionalOnBean(JavaMailSenderImpl.class)
@ConditionalOnEnabledHealthIndicator("mail")
@AutoConfigureAfter(MailSenderAutoConfiguration.class)
public class MailHealthContributorAutoConfiguration
		extends CompositeHealthContributorConfiguration<MailHealthIndicator, JavaMailSenderImpl> {

	@Bean
	@ConditionalOnMissingBean(name = { "mailHealthIndicator", "mailHealthContributor" })
	public HealthContributor mailHealthContributor(Map<String, JavaMailSenderImpl> mailSenders) {
		return createContributor(mailSenders);
	}

}
