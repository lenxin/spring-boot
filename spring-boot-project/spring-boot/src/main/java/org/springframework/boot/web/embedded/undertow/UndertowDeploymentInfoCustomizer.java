package org.springframework.boot.web.embedded.undertow;

import io.undertow.servlet.api.DeploymentInfo;

/**
 * Callback interface that can be used to customize an Undertow {@link DeploymentInfo}.
 *

 * @since 2.0.0
 * @see UndertowServletWebServerFactory
 */
@FunctionalInterface
public interface UndertowDeploymentInfoCustomizer {

	/**
	 * Customize the deployment info.
	 * @param deploymentInfo the {@code DeploymentInfo} to customize
	 */
	void customize(DeploymentInfo deploymentInfo);

}
