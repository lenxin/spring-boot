package org.springframework.boot.autoconfigure.session;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.SessionRepository;

/**
 * No-op session configuration used to disable Spring Session using the environment.
 *

 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingBean(SessionRepository.class)
@Conditional(ServletSessionCondition.class)
class NoOpSessionConfiguration {

}
