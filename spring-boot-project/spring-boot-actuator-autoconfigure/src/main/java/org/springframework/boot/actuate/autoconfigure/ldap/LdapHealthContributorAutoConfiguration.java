package org.springframework.boot.actuate.autoconfigure.ldap;

import java.util.Map;

import org.springframework.boot.actuate.autoconfigure.health.CompositeHealthContributorConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.ldap.LdapHealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapOperations;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for {@link LdapHealthIndicator}.
 *


 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(LdapOperations.class)
@ConditionalOnBean(LdapOperations.class)
@ConditionalOnEnabledHealthIndicator("ldap")
@AutoConfigureAfter(LdapAutoConfiguration.class)
public class LdapHealthContributorAutoConfiguration
		extends CompositeHealthContributorConfiguration<LdapHealthIndicator, LdapOperations> {

	@Bean
	@ConditionalOnMissingBean(name = { "ldapHealthIndicator", "ldapHealthContributor" })
	public HealthContributor ldapHealthContributor(Map<String, LdapOperations> ldapOperations) {
		return createContributor(ldapOperations);
	}

}
