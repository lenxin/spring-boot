package org.springframework.boot.env;

import java.util.List;

import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.logging.DeferredLogs;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * {@link SmartApplicationListener} used to trigger {@link EnvironmentPostProcessor
 * EnvironmentPostProcessors} registered in the {@code spring.factories} file.
 *

 * @since 2.4.0
 */
public class EnvironmentPostProcessorApplicationListener implements SmartApplicationListener, Ordered {

	/**
	 * The default order for the processor.
	 */
	public static final int DEFAULT_ORDER = Ordered.HIGHEST_PRECEDENCE + 10;

	private final DeferredLogs deferredLogs;

	private int order = DEFAULT_ORDER;

	private final EnvironmentPostProcessorsFactory postProcessorsFactory;

	/**
	 * Create a new {@link EnvironmentPostProcessorApplicationListener} with
	 * {@link EnvironmentPostProcessor} classes loaded via {@code spring.factories}.
	 */
	public EnvironmentPostProcessorApplicationListener() {
		this(EnvironmentPostProcessorsFactory
				.fromSpringFactories(EnvironmentPostProcessorApplicationListener.class.getClassLoader()));
	}

	/**
	 * Create a new {@link EnvironmentPostProcessorApplicationListener} with post
	 * processors created by the given factory.
	 * @param postProcessorsFactory the post processors factory
	 */
	public EnvironmentPostProcessorApplicationListener(EnvironmentPostProcessorsFactory postProcessorsFactory) {
		this(postProcessorsFactory, new DeferredLogs());
	}

	EnvironmentPostProcessorApplicationListener(EnvironmentPostProcessorsFactory postProcessorsFactory,
			DeferredLogs deferredLogs) {
		this.postProcessorsFactory = postProcessorsFactory;
		this.deferredLogs = deferredLogs;
	}

	@Override
	public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
		return ApplicationEnvironmentPreparedEvent.class.isAssignableFrom(eventType)
				|| ApplicationPreparedEvent.class.isAssignableFrom(eventType)
				|| ApplicationFailedEvent.class.isAssignableFrom(eventType);
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ApplicationEnvironmentPreparedEvent) {
			onApplicationEnvironmentPreparedEvent((ApplicationEnvironmentPreparedEvent) event);
		}
		if (event instanceof ApplicationPreparedEvent) {
			onApplicationPreparedEvent((ApplicationPreparedEvent) event);
		}
		if (event instanceof ApplicationFailedEvent) {
			onApplicationFailedEvent((ApplicationFailedEvent) event);
		}
	}

	private void onApplicationEnvironmentPreparedEvent(ApplicationEnvironmentPreparedEvent event) {
		ConfigurableEnvironment environment = event.getEnvironment();
		SpringApplication application = event.getSpringApplication();
		for (EnvironmentPostProcessor postProcessor : getEnvironmentPostProcessors(event.getBootstrapContext())) {
			postProcessor.postProcessEnvironment(environment, application);
		}
	}

	private void onApplicationPreparedEvent(ApplicationPreparedEvent event) {
		finish();
	}

	private void onApplicationFailedEvent(ApplicationFailedEvent event) {
		finish();
	}

	private void finish() {
		this.deferredLogs.switchOverAll();
	}

	List<EnvironmentPostProcessor> getEnvironmentPostProcessors(ConfigurableBootstrapContext bootstrapContext) {
		return this.postProcessorsFactory.getEnvironmentPostProcessors(this.deferredLogs, bootstrapContext);
	}

	@Override
	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

}
