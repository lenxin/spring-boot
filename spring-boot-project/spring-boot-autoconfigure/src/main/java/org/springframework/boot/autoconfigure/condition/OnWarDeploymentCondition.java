package org.springframework.boot.autoconfigure.condition;

import javax.servlet.ServletContext;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.web.context.WebApplicationContext;

/**
 * {@link Condition} that checks if the application is running as a traditional war
 * deployment.
 *

 */
class OnWarDeploymentCondition extends SpringBootCondition {

	@Override
	public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
		ResourceLoader resourceLoader = context.getResourceLoader();
		if (resourceLoader instanceof WebApplicationContext) {
			WebApplicationContext applicationContext = (WebApplicationContext) resourceLoader;
			ServletContext servletContext = applicationContext.getServletContext();
			if (servletContext != null) {
				return ConditionOutcome.match("Application is deployed as a WAR file.");
			}
		}
		return ConditionOutcome.noMatch(ConditionMessage.forCondition(ConditionalOnWarDeployment.class)
				.because("the application is not deployed as a WAR file."));
	}

}
