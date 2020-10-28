package org.springframework.boot.autoconfigure.data;

import java.util.Locale;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * {@link SpringBootCondition} for controlling what type of Spring Data repositories are
 * auto-configured.
 *

 */
class OnRepositoryTypeCondition extends SpringBootCondition {

	@Override
	public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Map<String, Object> attributes = metadata.getAnnotationAttributes(ConditionalOnRepositoryType.class.getName(),
				true);
		RepositoryType configuredType = getTypeProperty(context.getEnvironment(), (String) attributes.get("store"));
		RepositoryType requiredType = (RepositoryType) attributes.get("type");
		ConditionMessage.Builder message = ConditionMessage.forCondition(ConditionalOnRepositoryType.class);
		if (configuredType == requiredType || configuredType == RepositoryType.AUTO) {
			return ConditionOutcome
					.match(message.because("configured type of '" + configuredType.name() + "' matched required type"));
		}
		return ConditionOutcome.noMatch(message.because("configured type (" + configuredType.name()
				+ ") did not match required type (" + requiredType.name() + ")"));
	}

	private RepositoryType getTypeProperty(Environment environment, String store) {
		return RepositoryType
				.valueOf(environment.getProperty(String.format("spring.data.%s.repositories.type", store), "auto")
						.toUpperCase(Locale.ENGLISH));
	}

}
