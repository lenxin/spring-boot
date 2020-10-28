package org.springframework.boot.autoconfigure.condition.config.first;

import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Sample auto-configuration for {@link ConditionEvaluationReport} tests.
 *

 */
@Configuration("autoConfigOne")
@ConditionalOnProperty("sample.first")
public class SampleAutoConfiguration {

	@Bean
	public String one() {
		return "one";
	}

}
