package org.springframework.boot.web.embedded.undertow;

import java.io.File;
import java.util.Collection;

import io.undertow.Undertow.Builder;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;

/**
 * {@link ConfigurableWebServerFactory} for Undertow-specific features.
 *

 * @since 2.0.0
 * @see UndertowServletWebServerFactory
 * @see UndertowReactiveWebServerFactory
 */
public interface ConfigurableUndertowWebServerFactory extends ConfigurableWebServerFactory {

	/**
	 * Set {@link UndertowBuilderCustomizer}s that should be applied to the Undertow
	 * {@link Builder}. Calling this method will replace any existing customizers.
	 * @param customizers the customizers to set
	 * @since 2.3.0
	 */
	void setBuilderCustomizers(Collection<? extends UndertowBuilderCustomizer> customizers);

	/**
	 * Add {@link UndertowBuilderCustomizer}s that should be used to customize the
	 * Undertow {@link Builder}.
	 * @param customizers the customizers to add
	 */
	void addBuilderCustomizers(UndertowBuilderCustomizer... customizers);

	/**
	 * Set the buffer size.
	 * @param bufferSize buffer size
	 */
	void setBufferSize(Integer bufferSize);

	/**
	 * Set the number of IO Threads.
	 * @param ioThreads number of IO Threads
	 */
	void setIoThreads(Integer ioThreads);

	/**
	 * Set the number of Worker Threads.
	 * @param workerThreads number of Worker Threads
	 */
	void setWorkerThreads(Integer workerThreads);

	/**
	 * Set whether direct buffers should be used.
	 * @param useDirectBuffers whether direct buffers should be used
	 */
	void setUseDirectBuffers(Boolean useDirectBuffers);

	/**
	 * Set the access log directory.
	 * @param accessLogDirectory access log directory
	 */
	void setAccessLogDirectory(File accessLogDirectory);

	/**
	 * Set the access log pattern.
	 * @param accessLogPattern access log pattern
	 */
	void setAccessLogPattern(String accessLogPattern);

	/**
	 * Set the access log prefix.
	 * @param accessLogPrefix log prefix
	 */
	void setAccessLogPrefix(String accessLogPrefix);

	/**
	 * Set the access log suffix.
	 * @param accessLogSuffix access log suffix
	 */
	void setAccessLogSuffix(String accessLogSuffix);

	/**
	 * Set whether access logs are enabled.
	 * @param accessLogEnabled whether access logs are enabled
	 */
	void setAccessLogEnabled(boolean accessLogEnabled);

	/**
	 * Set whether access logs rotation is enabled.
	 * @param accessLogRotate whether access logs rotation is enabled
	 */
	void setAccessLogRotate(boolean accessLogRotate);

	/**
	 * Set if x-forward-* headers should be processed.
	 * @param useForwardHeaders if x-forward headers should be used
	 */
	void setUseForwardHeaders(boolean useForwardHeaders);

}
