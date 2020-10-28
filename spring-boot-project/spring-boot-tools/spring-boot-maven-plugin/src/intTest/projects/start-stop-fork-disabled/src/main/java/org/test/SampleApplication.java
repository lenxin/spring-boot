package org.test;

import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * This sample app simulates the JMX Mbean that is exposed by the Spring Boot application.
 */
public class SampleApplication {

	private static final Object lock = new Object();

	public static void main(String[] args) throws Exception {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName name = new ObjectName(
				"org.springframework.boot:type=Admin,name=SpringApplication");
		SpringApplicationAdmin mbean = new SpringApplicationAdmin();
		mbs.registerMBean(mbean, name);

		// Flag the app as ready
		mbean.ready = true;

		int waitAttempts = 0;
		while (!mbean.shutdownInvoked) {
			if (waitAttempts > 10) {
				throw new IllegalStateException(
						"Shutdown should have been invoked by now");
			}
			synchronized (lock) {
				lock.wait(250);
			}
			waitAttempts++;
		}
	}

	public interface SpringApplicationAdminMXBean {

		boolean isReady();

		void shutdown();

	}

	static class SpringApplicationAdmin implements SpringApplicationAdminMXBean {

		private boolean ready;

		private boolean shutdownInvoked;

		@Override
		public boolean isReady() {
			System.out.println("isReady: " + this.ready);
			return this.ready;
		}

		@Override
		public void shutdown() {
			this.shutdownInvoked = true;
			System.out.println("Shutdown requested");
		}

	}

}
