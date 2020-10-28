package org.springframework.boot.context.properties.scan.valid.a;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Profile;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**

 */
public class AScanConfiguration {

	@ConfigurationProperties(prefix = "a")
	static class AProperties {

	}

	@Profile("test")
	@ConfigurationProperties(prefix = "profile")
	static class MyProfileProperties {

	}

	@Conditional(TestResourceCondition.class)
	@ConfigurationProperties(prefix = "resource")
	static class MyResourceProperties {

	}

	static class TestResourceCondition implements Condition {

		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
			return context.getResourceLoader().getResource("test").exists();
		}

	}

}
