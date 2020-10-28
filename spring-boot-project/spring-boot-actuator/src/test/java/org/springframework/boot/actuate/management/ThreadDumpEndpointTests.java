package org.springframework.boot.actuate.management;

import java.lang.Thread.State;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ThreadDumpEndpoint}.
 *


 */
class ThreadDumpEndpointTests {

	@Test
	void dumpThreads() {
		assertThat(new ThreadDumpEndpoint().threadDump().getThreads().size()).isGreaterThan(0);
	}

	@Test
	void dumpThreadsAsText() throws InterruptedException {
		Object contendedMonitor = new Object();
		Object monitor = new Object();
		CountDownLatch latch = new CountDownLatch(1);
		Thread awaitCountDownLatchThread = new Thread(() -> {
			try {
				latch.await();
			}
			catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}, "Awaiting CountDownLatch");
		awaitCountDownLatchThread.start();
		Thread contendedMonitorThread = new Thread(() -> {
			synchronized (contendedMonitor) {
				// Intentionally empty
			}
		}, "Waiting for monitor");
		Thread waitOnMonitorThread = new Thread(() -> {
			synchronized (monitor) {
				try {
					monitor.wait();
				}
				catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}
		}, "Waiting on monitor");
		waitOnMonitorThread.start();
		ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
		Lock writeLock = readWriteLock.writeLock();
		new Thread(() -> {
			writeLock.lock();
			try {
				latch.await();
			}
			catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			finally {
				writeLock.unlock();
			}
		}, "Holding write lock").start();
		while (writeLock.tryLock()) {
			writeLock.unlock();
		}
		awaitState(waitOnMonitorThread, State.WAITING);
		awaitState(awaitCountDownLatchThread, State.WAITING);
		String threadDump;
		synchronized (contendedMonitor) {
			contendedMonitorThread.start();
			awaitState(contendedMonitorThread, State.BLOCKED);
			threadDump = new ThreadDumpEndpoint().textThreadDump();
		}
		latch.countDown();
		synchronized (monitor) {
			monitor.notifyAll();
		}
		assertThat(threadDump)
				.containsPattern(String.format("\t- parking to wait for <[0-9a-z]+> \\(a %s\\$Sync\\)",
						CountDownLatch.class.getName().replace(".", "\\.")))
				.contains(String.format("\t- locked <%s> (a java.lang.Object)", hexIdentityHashCode(contendedMonitor)))
				.contains(String.format("\t- waiting to lock <%s> (a java.lang.Object) owned by \"%s\" t@%d",
						hexIdentityHashCode(contendedMonitor), Thread.currentThread().getName(),
						Thread.currentThread().getId()))
				.contains(String.format("\t- waiting on <%s> (a java.lang.Object)", hexIdentityHashCode(monitor)))
				.containsPattern(
						String.format("Locked ownable synchronizers:%n\t- Locked <[0-9a-z]+> \\(a %s\\$NonfairSync\\)",
								ReentrantReadWriteLock.class.getName().replace(".", "\\.")));
	}

	private String hexIdentityHashCode(Object object) {
		return Integer.toHexString(System.identityHashCode(object));
	}

	private void awaitState(Thread thread, State state) throws InterruptedException {
		while (thread.getState() != state) {
			Thread.sleep(50);
		}
	}

}
