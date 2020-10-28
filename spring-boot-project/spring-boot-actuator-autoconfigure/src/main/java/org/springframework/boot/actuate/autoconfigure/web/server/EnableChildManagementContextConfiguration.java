package org.springframework.boot.actuate.autoconfigure.web.server;

import org.springframework.boot.actuate.autoconfigure.web.ManagementContextType;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class used to enable configuration of a child management context.
 *

 */
@Configuration(proxyBeanMethods = false)
@EnableManagementContext(ManagementContextType.CHILD)
class EnableChildManagementContextConfiguration {

}
