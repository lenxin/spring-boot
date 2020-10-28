package org.springframework.boot.logging;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.boot.logging.DeferredLog.Line;
import org.springframework.boot.logging.DeferredLog.Lines;

/**
 * A {@link DeferredLogFactory} implementation that manages a collection
 * {@link DeferredLog} instances.
 *

 * @since 2.4.0
 */
public class DeferredLogs implements DeferredLogFactory {

	private final Lines lines = new Lines();

	private final List<DeferredLog> loggers = new ArrayList<>();

	/**
	 * Create a new {@link DeferredLog} for the given destination.
	 * @param destination the ultimate log destination
	 * @return a deferred log instance that will switch to the destination when
	 * appropriate.
	 */
	@Override
	public Log getLog(Class<?> destination) {
		return getLog(() -> LogFactory.getLog(destination));
	}

	/**
	 * Create a new {@link DeferredLog} for the given destination.
	 * @param destination the ultimate log destination
	 * @return a deferred log instance that will switch to the destination when
	 * appropriate.
	 */
	@Override
	public Log getLog(Log destination) {
		return getLog(() -> destination);
	}

	/**
	 * Create a new {@link DeferredLog} for the given destination.
	 * @param destination the ultimate log destination
	 * @return a deferred log instance that will switch to the destination when
	 * appropriate.
	 */
	@Override
	public Log getLog(Supplier<Log> destination) {
		synchronized (this.lines) {
			DeferredLog logger = new DeferredLog(destination, this.lines);
			this.loggers.add(logger);
			return logger;
		}
	}

	/**
	 * Switch over all deferred logs to their supplied destination.
	 */
	public void switchOverAll() {
		synchronized (this.lines) {
			for (Line line : this.lines) {
				DeferredLog.logTo(line.getDestination(), line.getLevel(), line.getMessage(), line.getThrowable());
			}
			for (DeferredLog logger : this.loggers) {
				logger.switchOver();
			}
			this.lines.clear();
		}

	}

}
