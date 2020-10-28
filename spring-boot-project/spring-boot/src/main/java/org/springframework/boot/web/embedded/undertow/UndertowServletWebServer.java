package org.springframework.boot.web.embedded.undertow;

import io.undertow.Handlers;
import io.undertow.Undertow.Builder;
import io.undertow.server.HttpHandler;
import io.undertow.servlet.api.DeploymentManager;

import org.springframework.boot.web.server.Compression;
import org.springframework.boot.web.server.WebServer;
import org.springframework.util.StringUtils;

/**
 * {@link WebServer} that can be used to control an embedded Undertow server. Typically
 * this class should be created using {@link UndertowServletWebServerFactory} and not
 * directly.
 *





 * @since 2.0.0
 * @see UndertowServletWebServerFactory
 */
public class UndertowServletWebServer extends UndertowWebServer {

	private final String contextPath;

	private final DeploymentManager manager;

	/**
	 * Create a new {@link UndertowServletWebServer} instance.
	 * @param builder the builder
	 * @param manager the deployment manager
	 * @param contextPath the root context path
	 * @param autoStart if the server should be started
	 * @param compression compression configuration
	 * @deprecated since 2.3.0 in favor of
	 * {@link #UndertowServletWebServer(io.undertow.Undertow.Builder, Iterable, String, boolean)}
	 */
	@Deprecated
	public UndertowServletWebServer(Builder builder, DeploymentManager manager, String contextPath, boolean autoStart,
			Compression compression) {
		this(builder, manager, contextPath, false, autoStart, compression);
	}

	/**
	 * Create a new {@link UndertowServletWebServer} instance.
	 * @param builder the builder
	 * @param manager the deployment manager
	 * @param contextPath the root context path
	 * @param useForwardHeaders if x-forward headers should be used
	 * @param autoStart if the server should be started
	 * @param compression compression configuration
	 * @deprecated since 2.3.0 in favor of
	 * {@link #UndertowServletWebServer(io.undertow.Undertow.Builder, Iterable, String, boolean)}
	 */
	@Deprecated
	public UndertowServletWebServer(Builder builder, DeploymentManager manager, String contextPath,
			boolean useForwardHeaders, boolean autoStart, Compression compression) {
		this(builder, manager, contextPath, useForwardHeaders, autoStart, compression, null);
	}

	/**
	 * Create a new {@link UndertowServletWebServer} instance.
	 * @param builder the builder
	 * @param manager the deployment manager
	 * @param contextPath the root context path
	 * @param useForwardHeaders if x-forward headers should be used
	 * @param autoStart if the server should be started
	 * @param compression compression configuration
	 * @param serverHeader string to be used in HTTP header
	 * @deprecated since 2.3.0 in favor of
	 * {@link #UndertowServletWebServer(io.undertow.Undertow.Builder, Iterable, String, boolean)}
	 */
	@Deprecated
	public UndertowServletWebServer(Builder builder, DeploymentManager manager, String contextPath,
			boolean useForwardHeaders, boolean autoStart, Compression compression, String serverHeader) {
		this(builder, UndertowWebServerFactoryDelegate.createHttpHandlerFactories(compression, useForwardHeaders,
				serverHeader, null, new DeploymentManagerHttpHandlerFactory(manager)), contextPath, autoStart);
	}

	/**
	 * Create a new {@link UndertowServletWebServer} instance.
	 * @param builder the builder
	 * @param httpHandlerFactories the handler factories
	 * @param contextPath the root context path
	 * @param autoStart if the server should be started
	 * @since 2.3.0
	 */
	public UndertowServletWebServer(Builder builder, Iterable<HttpHandlerFactory> httpHandlerFactories,
			String contextPath, boolean autoStart) {
		super(builder, httpHandlerFactories, autoStart);
		this.contextPath = contextPath;
		this.manager = findManager(httpHandlerFactories);
	}

	private DeploymentManager findManager(Iterable<HttpHandlerFactory> httpHandlerFactories) {
		for (HttpHandlerFactory httpHandlerFactory : httpHandlerFactories) {
			if (httpHandlerFactory instanceof DeploymentManagerHttpHandlerFactory) {
				return ((DeploymentManagerHttpHandlerFactory) httpHandlerFactory).getDeploymentManager();
			}
		}
		return null;
	}

	@Override
	protected HttpHandler createHttpHandler() {
		HttpHandler handler = super.createHttpHandler();
		if (StringUtils.hasLength(this.contextPath)) {
			handler = Handlers.path().addPrefixPath(this.contextPath, handler);
		}
		return handler;
	}

	@Override
	protected String getStartLogMessage() {
		String message = super.getStartLogMessage();
		if (StringUtils.hasText(this.contextPath)) {
			message += " with context path '" + this.contextPath + "'";
		}
		return message;
	}

	public DeploymentManager getDeploymentManager() {
		return this.manager;
	}

}
