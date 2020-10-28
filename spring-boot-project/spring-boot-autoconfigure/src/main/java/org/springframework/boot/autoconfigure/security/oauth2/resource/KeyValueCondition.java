package org.springframework.boot.autoconfigure.security.oauth2.resource;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

/**
 * Condition for creating a jwt decoder using a public key value.
 *

 * @since 2.2.0
 */
public class KeyValueCondition extends SpringBootCondition {

	@Override
	public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
		ConditionMessage.Builder message = ConditionMessage.forCondition("Public Key Value Condition");
		Environment environment = context.getEnvironment();
		String publicKeyLocation = environment
				.getProperty("spring.security.oauth2.resourceserver.jwt.public-key-location");
		if (!StringUtils.hasText(publicKeyLocation)) {
			return ConditionOutcome.noMatch(message.didNotFind("public-key-location property").atAll());
		}
		String issuerUri = environment.getProperty("spring.security.oauth2.resourceserver.jwt.issuer-uri");
		String jwkSetUri = environment.getProperty("spring.security.oauth2.resourceserver.jwt.jwk-set-uri");
		if (StringUtils.hasText(jwkSetUri)) {
			return ConditionOutcome.noMatch(message.found("jwk-set-uri property").items(jwkSetUri));
		}
		if (StringUtils.hasText(issuerUri)) {
			return ConditionOutcome.noMatch(message.found("issuer-uri property").items(issuerUri));
		}
		return ConditionOutcome.match(message.foundExactly("public key location property"));
	}

}
