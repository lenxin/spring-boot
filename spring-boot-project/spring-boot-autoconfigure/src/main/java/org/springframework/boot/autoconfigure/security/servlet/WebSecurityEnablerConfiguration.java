package org.springframework.boot.autoconfigure.security.servlet;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Adds the{@link EnableWebSecurity @EnableWebSecurity} annotation if Spring Security is
 * on the classpath. This will make sure that the annotation is present with default
 * security auto-configuration and also if the user adds custom security and forgets to
 * add the annotation. If {@link EnableWebSecurity @EnableWebSecurity} has already been
 * added or if a bean with name {@value BeanIds#SPRING_SECURITY_FILTER_CHAIN} has been
 * configured by the user, this will back-off.
 *

 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingBean(name = BeanIds.SPRING_SECURITY_FILTER_CHAIN)
@ConditionalOnClass(EnableWebSecurity.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableWebSecurity
class WebSecurityEnablerConfiguration {

}
