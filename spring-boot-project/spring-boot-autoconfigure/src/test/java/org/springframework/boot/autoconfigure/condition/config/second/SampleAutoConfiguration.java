package org.springframework.boot.autoconfigure.condition.config.second;

import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Sample auto-configuration for {@link ConditionEvaluationReport} tests.
 *

 */
@Configuration("autoConfigTwo")
@ConditionalOnProperty("sample.second")
public class SampleAutoConfiguration {

	@Bean
	public String two() {
		return "two";
	}

}
