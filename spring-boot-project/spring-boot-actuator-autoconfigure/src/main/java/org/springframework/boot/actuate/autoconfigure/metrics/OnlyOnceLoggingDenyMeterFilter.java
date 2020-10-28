package org.springframework.boot.actuate.autoconfigure.metrics;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import io.micrometer.core.instrument.Meter.Id;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.config.MeterFilterReply;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.util.Assert;

/**
 * {@link MeterFilter} to log only once a warning message and deny a {@link Id Meter.Id}.
 *


 * @since 2.0.5
 */
public final class OnlyOnceLoggingDenyMeterFilter implements MeterFilter {

	private static final Log logger = LogFactory.getLog(OnlyOnceLoggingDenyMeterFilter.class);

	private final AtomicBoolean alreadyWarned = new AtomicBoolean();

	private final Supplier<String> message;

	public OnlyOnceLoggingDenyMeterFilter(Supplier<String> message) {
		Assert.notNull(message, "Message must not be null");
		this.message = message;
	}

	@Override
	public MeterFilterReply accept(Id id) {
		if (logger.isWarnEnabled() && this.alreadyWarned.compareAndSet(false, true)) {
			logger.warn(this.message.get());
		}
		return MeterFilterReply.DENY;
	}

}
