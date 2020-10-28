package org.springframework.boot.autoconfigure.session;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Base class for Servlet and reactive session conditions.
 *




 */
abstract class AbstractSessionCondition extends SpringBootCondition {

	private final WebApplicationType webApplicationType;

	protected AbstractSessionCondition(WebApplicationType webApplicationType) {
		this.webApplicationType = webApplicationType;
	}

	@Override
	public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
		ConditionMessage.Builder message = ConditionMessage.forCondition("Session Condition");
		Environment environment = context.getEnvironment();
		StoreType required = SessionStoreMappings.getType(this.webApplicationType,
				((AnnotationMetadata) metadata).getClassName());
		if (!environment.containsProperty("spring.session.store-type")) {
			return ConditionOutcome.match(message.didNotFind("property", "properties")
					.items(ConditionMessage.Style.QUOTE, "spring.session.store-type"));
		}
		try {
			Binder binder = Binder.get(environment);
			return binder.bind("spring.session.store-type", StoreType.class)
					.map((t) -> new ConditionOutcome(t == required,
							message.found("spring.session.store-type property").items(t)))
					.orElse(ConditionOutcome.noMatch(message.didNotFind("spring.session.store-type property").atAll()));
		}
		catch (BindException ex) {
			return ConditionOutcome.noMatch(message.found("invalid spring.session.store-type property").atAll());
		}
	}

}
