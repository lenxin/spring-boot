package org.springframework.boot.autoconfigure.data.ldap;

import javax.naming.ldap.LdapContext;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.data.ldap.repository.support.LdapRepositoryFactoryBean;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Data's LDAP Repositories.
 *

 * @since 1.5.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ LdapContext.class, LdapRepository.class })
@ConditionalOnProperty(prefix = "spring.data.ldap.repositories", name = "enabled", havingValue = "true",
		matchIfMissing = true)
@ConditionalOnMissingBean(LdapRepositoryFactoryBean.class)
@Import(LdapRepositoriesRegistrar.class)
public class LdapRepositoriesAutoConfiguration {

}
