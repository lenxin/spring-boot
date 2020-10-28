package org.springframework.boot.actuate.metrics.web.jetty;

import java.util.Collections;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.jetty.JettyServerThreadPoolMetrics;
import org.eclipse.jetty.util.thread.ThreadPool;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.embedded.jetty.JettyWebServer;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;

/**
 * Binds {@link JettyServerThreadPoolMetrics} in response to the
 * {@link ApplicationStartedEvent}.
 *

 * @since 2.1.0
 */
public class JettyServerThreadPoolMetricsBinder implements ApplicationListener<ApplicationStartedEvent> {

	private final MeterRegistry meterRegistry;

	private final Iterable<Tag> tags;

	public JettyServerThreadPoolMetricsBinder(MeterRegistry meterRegistry) {
		this(meterRegistry, Collections.emptyList());
	}

	public JettyServerThreadPoolMetricsBinder(MeterRegistry meterRegistry, Iterable<Tag> tags) {
		this.meterRegistry = meterRegistry;
		this.tags = tags;
	}

	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		ApplicationContext applicationContext = event.getApplicationContext();
		ThreadPool threadPool = findThreadPool(applicationContext);
		if (threadPool != null) {
			new JettyServerThreadPoolMetrics(threadPool, this.tags).bindTo(this.meterRegistry);
		}
	}

	private ThreadPool findThreadPool(ApplicationContext applicationContext) {
		if (applicationContext instanceof WebServerApplicationContext) {
			WebServer webServer = ((WebServerApplicationContext) applicationContext).getWebServer();
			if (webServer instanceof JettyWebServer) {
				return ((JettyWebServer) webServer).getServer().getThreadPool();
			}
		}
		return null;
	}

}
