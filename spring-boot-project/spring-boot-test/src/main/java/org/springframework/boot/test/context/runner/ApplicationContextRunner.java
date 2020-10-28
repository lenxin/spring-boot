package org.springframework.boot.test.context.runner;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.boot.context.annotation.Configurations;
import org.springframework.boot.test.context.assertj.AssertableApplicationContext;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * An {@link AbstractApplicationContextRunner ApplicationContext runner} for a standard,
 * non-web environment {@link ConfigurableApplicationContext}.
 * <p>
 * See {@link AbstractApplicationContextRunner} for details.
 *



 * @since 2.0.0
 */
public class ApplicationContextRunner extends
		AbstractApplicationContextRunner<ApplicationContextRunner, ConfigurableApplicationContext, AssertableApplicationContext> {

	/**
	 * Create a new {@link ApplicationContextRunner} instance using an
	 * {@link AnnotationConfigApplicationContext} as the underlying source.
	 */
	public ApplicationContextRunner() {
		this(AnnotationConfigApplicationContext::new);
	}

	/**
	 * Create a new {@link ApplicationContextRunner} instance using the specified
	 * {@code contextFactory} as the underlying source.
	 * @param contextFactory a supplier that returns a new instance on each call
	 */
	public ApplicationContextRunner(Supplier<ConfigurableApplicationContext> contextFactory) {
		super(contextFactory);
	}

	private ApplicationContextRunner(Supplier<ConfigurableApplicationContext> contextFactory,
			boolean allowBeanDefinitionOverriding,
			List<ApplicationContextInitializer<? super ConfigurableApplicationContext>> initializers,
			TestPropertyValues environmentProperties, TestPropertyValues systemProperties, ClassLoader classLoader,
			ApplicationContext parent, List<BeanRegistration<?>> beanRegistrations,
			List<Configurations> configurations) {
		super(contextFactory, allowBeanDefinitionOverriding, initializers, environmentProperties, systemProperties,
				classLoader, parent, beanRegistrations, configurations);
	}

	@Override
	protected ApplicationContextRunner newInstance(Supplier<ConfigurableApplicationContext> contextFactory,
			boolean allowBeanDefinitionOverriding,
			List<ApplicationContextInitializer<? super ConfigurableApplicationContext>> initializers,
			TestPropertyValues environmentProperties, TestPropertyValues systemProperties, ClassLoader classLoader,
			ApplicationContext parent, List<BeanRegistration<?>> beanRegistrations,
			List<Configurations> configurations) {
		return new ApplicationContextRunner(contextFactory, allowBeanDefinitionOverriding, initializers,
				environmentProperties, systemProperties, classLoader, parent, beanRegistrations, configurations);
	}

}
