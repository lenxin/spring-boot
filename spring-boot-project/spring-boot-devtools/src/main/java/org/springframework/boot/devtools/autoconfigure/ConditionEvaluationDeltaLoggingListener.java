package org.springframework.boot.devtools.autoconfigure;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportMessage;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;

/**
 * An {@link ApplicationListener} that logs the delta of condition evaluation across
 * restarts.
 *

 */
class ConditionEvaluationDeltaLoggingListener
		implements ApplicationListener<ApplicationReadyEvent>, ApplicationContextAware {

	private static final ConcurrentHashMap<String, ConditionEvaluationReport> previousReports = new ConcurrentHashMap<>();

	private static final Log logger = LogFactory.getLog(ConditionEvaluationDeltaLoggingListener.class);

	private volatile ApplicationContext context;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		if (!event.getApplicationContext().equals(this.context)) {
			return;
		}
		ConditionEvaluationReport report = event.getApplicationContext().getBean(ConditionEvaluationReport.class);
		ConditionEvaluationReport previousReport = previousReports.get(event.getApplicationContext().getId());
		if (previousReport != null) {
			ConditionEvaluationReport delta = report.getDelta(previousReport);
			if (!delta.getConditionAndOutcomesBySource().isEmpty() || !delta.getExclusions().isEmpty()
					|| !delta.getUnconditionalClasses().isEmpty()) {
				if (logger.isInfoEnabled()) {
					logger.info("Condition evaluation delta:"
							+ new ConditionEvaluationReportMessage(delta, "CONDITION EVALUATION DELTA"));
				}
			}
			else {
				logger.info("Condition evaluation unchanged");
			}
		}
		previousReports.put(event.getApplicationContext().getId(), report);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.context = applicationContext;
	}

}
