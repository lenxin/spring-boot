package org.springframework.boot.actuate.autoconfigure.metrics.export;

import org.springframework.boot.actuate.autoconfigure.OnEndpointElementCondition;
import org.springframework.context.annotation.Condition;

/**
 * {@link Condition} that checks if a metrics exporter is enabled.
 *

 */
class OnMetricsExportEnabledCondition extends OnEndpointElementCondition {

	protected OnMetricsExportEnabledCondition() {
		super("management.metrics.export.", ConditionalOnEnabledMetricsExport.class);
	}

}
