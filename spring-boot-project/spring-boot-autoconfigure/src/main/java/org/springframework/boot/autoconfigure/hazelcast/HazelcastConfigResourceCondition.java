package org.springframework.boot.autoconfigure.hazelcast;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ResourceCondition;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.Assert;

/**
 * {@link SpringBootCondition} used to check if the Hazelcast configuration is available.
 * This either kicks in if a default configuration has been found or if configurable
 * property referring to the resource to use has been set.
 *



 * @since 1.3.0
 */
public abstract class HazelcastConfigResourceCondition extends ResourceCondition {

	protected static final String HAZELCAST_CONFIG_PROPERTY = "spring.hazelcast.config";

	private final String configSystemProperty;

	protected HazelcastConfigResourceCondition(String configSystemProperty, String... resourceLocations) {
		super("Hazelcast", HAZELCAST_CONFIG_PROPERTY, resourceLocations);
		Assert.notNull(configSystemProperty, "ConfigSystemProperty must not be null");
		this.configSystemProperty = configSystemProperty;
	}

	@Override
	protected ConditionOutcome getResourceOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
		if (System.getProperty(this.configSystemProperty) != null) {
			return ConditionOutcome.match(
					startConditionMessage().because("System property '" + this.configSystemProperty + "' is set."));
		}
		return super.getResourceOutcome(context, metadata);
	}

}
