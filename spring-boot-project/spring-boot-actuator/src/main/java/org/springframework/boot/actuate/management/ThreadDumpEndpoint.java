package org.springframework.boot.actuate.management;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

/**
 * {@link Endpoint @Endpoint} to expose thread info.
 *


 * @since 2.0.0
 */
@Endpoint(id = "threaddump")
public class ThreadDumpEndpoint {

	private final PlainTextThreadDumpFormatter plainTextFormatter = new PlainTextThreadDumpFormatter();

	@ReadOperation
	public ThreadDumpDescriptor threadDump() {
		return getFormattedThreadDump(ThreadDumpDescriptor::new);
	}

	@ReadOperation(produces = "text/plain;charset=UTF-8")
	public String textThreadDump() {
		return getFormattedThreadDump(this.plainTextFormatter::format);
	}

	private <T> T getFormattedThreadDump(Function<ThreadInfo[], T> formatter) {
		return formatter.apply(ManagementFactory.getThreadMXBean().dumpAllThreads(true, true));
	}

	/**
	 * A description of a thread dump. Primarily intended for serialization to JSON.
	 */
	public static final class ThreadDumpDescriptor {

		private final List<ThreadInfo> threads;

		private ThreadDumpDescriptor(ThreadInfo[] threads) {
			this.threads = Arrays.asList(threads);
		}

		public List<ThreadInfo> getThreads() {
			return this.threads;
		}

	}

}
