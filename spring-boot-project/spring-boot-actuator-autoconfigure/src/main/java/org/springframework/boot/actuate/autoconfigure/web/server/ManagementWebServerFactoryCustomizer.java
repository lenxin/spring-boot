package org.springframework.boot.actuate.autoconfigure.web.server;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.util.LambdaSafe;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.Ssl;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.core.Ordered;

/**
 * {@link WebServerFactoryCustomizer} that customizes the {@link WebServerFactory} used to
 * create the management context's web server.
 *
 * @param <T> the type of web server factory to customize

 * @since 2.0.0
 */
public abstract class ManagementWebServerFactoryCustomizer<T extends ConfigurableWebServerFactory>
		implements WebServerFactoryCustomizer<T>, Ordered {

	private final ListableBeanFactory beanFactory;

	private final Class<? extends WebServerFactoryCustomizer<?>>[] customizerClasses;

	@SafeVarargs
	@SuppressWarnings("varargs")
	protected ManagementWebServerFactoryCustomizer(ListableBeanFactory beanFactory,
			Class<? extends WebServerFactoryCustomizer<?>>... customizerClasses) {
		this.beanFactory = beanFactory;
		this.customizerClasses = customizerClasses;
	}

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public final void customize(T factory) {
		ManagementServerProperties managementServerProperties = BeanFactoryUtils
				.beanOfTypeIncludingAncestors(this.beanFactory, ManagementServerProperties.class);
		// Customize as per the parent context first (so e.g. the access logs go to
		// the same place)
		customizeSameAsParentContext(factory);
		// Then reset the error pages
		factory.setErrorPages(Collections.emptySet());
		// and add the management-specific bits
		ServerProperties serverProperties = BeanFactoryUtils.beanOfTypeIncludingAncestors(this.beanFactory,
				ServerProperties.class);
		customize(factory, managementServerProperties, serverProperties);
	}

	private void customizeSameAsParentContext(T factory) {
		List<WebServerFactoryCustomizer<?>> customizers = Arrays.stream(this.customizerClasses).map(this::getCustomizer)
				.filter(Objects::nonNull).collect(Collectors.toList());
		invokeCustomizers(factory, customizers);
	}

	private WebServerFactoryCustomizer<?> getCustomizer(
			Class<? extends WebServerFactoryCustomizer<?>> customizerClass) {
		try {
			return BeanFactoryUtils.beanOfTypeIncludingAncestors(this.beanFactory, customizerClass);
		}
		catch (NoSuchBeanDefinitionException ex) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private void invokeCustomizers(T factory, List<WebServerFactoryCustomizer<?>> customizers) {
		LambdaSafe.callbacks(WebServerFactoryCustomizer.class, customizers, factory)
				.invoke((customizer) -> customizer.customize(factory));
	}

	protected void customize(T factory, ManagementServerProperties managementServerProperties,
			ServerProperties serverProperties) {
		factory.setPort(managementServerProperties.getPort());
		Ssl ssl = managementServerProperties.getSsl();
		if (ssl != null) {
			factory.setSsl(ssl);
		}
		factory.setServerHeader(serverProperties.getServerHeader());
		factory.setAddress(managementServerProperties.getAddress());
	}

}
