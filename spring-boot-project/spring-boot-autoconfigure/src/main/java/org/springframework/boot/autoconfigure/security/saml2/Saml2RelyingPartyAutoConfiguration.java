package org.springframework.boot.autoconfigure.security.saml2;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Security's SAML 2.0
 * authentication support.
 *

 * @since 2.2.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RelyingPartyRegistrationRepository.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Import({ Saml2RelyingPartyRegistrationConfiguration.class, Saml2LoginConfiguration.class })
@AutoConfigureBefore(SecurityAutoConfiguration.class)
@EnableConfigurationProperties(Saml2RelyingPartyProperties.class)
public class Saml2RelyingPartyAutoConfiguration {

}
